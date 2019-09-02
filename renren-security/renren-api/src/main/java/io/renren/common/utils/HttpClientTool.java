package io.renren.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.*;
import java.util.Map.Entry;

/**
 *
 * HttpClient工具类
 *
 * @author wqhui
 *
 */
public class HttpClientTool {

    private static final int BUFFER_SIZE = 4096;

    private static final int DEBUG_CONTENT_THRESHOLD = 1024 * 50; // 网络返回内容大于50K的暂不打印，防止日志内容过大

    private static MultiThreadedHttpConnectionManager httpConnectionManager = new MultiThreadedHttpConnectionManager();

    private static HttpClient httpClient = new HttpClient(httpConnectionManager);

    static {

        //每主机最大连接数和总共最大连接数，通过hosfConfiguration设置host来区分每个主机
        httpClient.getHttpConnectionManager().getParams().setDefaultMaxConnectionsPerHost(8);

        httpClient.getHttpConnectionManager().getParams().setMaxTotalConnections(300);

        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(10000);

        httpClient.getHttpConnectionManager().getParams().setSoTimeout(10000);

        httpClient.getHttpConnectionManager().getParams().setTcpNoDelay(true);

        httpClient.getHttpConnectionManager().getParams().setLinger(1);

        //失败的情况下会进行3次尝试,成功之后不会再尝试

        httpClient.getHttpConnectionManager().getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());

    }

    public static String postData(String url, Map data) {
        return postData(url, data, "UTF-8", "POST");
    }
    public static String getData(String url, Map data) {
        return postData(url, data, "UTF-8", "GET");
    }

    public static String postData(String url, Map data, String defaultEncoding) {
        return postData(url, data, defaultEncoding, "POST");
    }


    /**
     * 发生HTTP请求。返回http请求内容文档。 参数的Map的值只能是String或者String的数组，Method暂时只支持POST和GET
     *
     * @param url
     * @param data
     * @param defaultEncoding
     * @param method
     * @return
     */
    public static String postData(String url, Map data, String defaultEncoding,
                                  String method) {
        return postData(url, data, defaultEncoding, method, null);
    }

    public static String postData(String url, Map data, String defaultEncoding,
                                  String method, String postCharset) {
//        HttpMethod httpMethod = createMethod(Utils.trimNull(url), method, data, postCharset);
        HttpMethod httpMethod = createMethod(url, method, data, postCharset);
        if (httpMethod == null) {
//            SysLogUtils.info("请求参数不正确，无法创建http请求");
            throw new RuntimeException("请求参数不正确，无法创建http请求");
        }

        String resp = sendHttpMethod(httpMethod, defaultEncoding);
        if (resp != null && resp.length() <= DEBUG_CONTENT_THRESHOLD) {
//            SysLogUtils.debug("HTTP协议返回内容[" + resp + "]");
        }
        return resp;

    }

    public static int postDataReturnStatus(String url, Map data, String method, String postCharset) {
        int status = 999;
//        HttpMethod httpMethod = createMethod(Utils.trimNull(url), method, data, postCharset);
        HttpMethod httpMethod = createMethod(url, method, data, postCharset);
        if (httpMethod == null) {
//            SysLogUtils.info("请求参数不正确，无法创建http请求");
            return status;
        }

        HttpClient httpClient = new HttpClient();
        try {
            status = httpClient.executeMethod(httpMethod);
//            SysLogUtils.info("访问请求返回状态码[" + status + "]");
        } catch (Throwable e) {
//            SysLogUtils.info("访问网络发生异常", e);
        } finally {
            if (httpMethod != null) {
                httpMethod.releaseConnection();
            }
        }
        return status;
    }

    private static HttpMethod createMethod(String url, String method,
                                           Map paramMap, String postCharset) {
        if ((paramMap == null)) {
            paramMap = new HashMap();
        }

//        SysLogUtils.debug("使用HttpClient工具访问网络，请求地址[" + url + "]，请求参数:"
//                + paramMap);

        List pairList = new ArrayList();
        Iterator it = paramMap.entrySet().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            String key = (String) entry.getKey();
            Object value = entry.getValue();

            if (value == null) {
                continue;
            }

            if (value instanceof String) {
                NameValuePair tempPart = new NameValuePair(key, (String) value);
                pairList.add(tempPart);
            } else {
                String[] values = (String[]) value;
                for (int i = 0; i < values.length; i++) {
                    NameValuePair tempPart = new NameValuePair(key, values[i]);
                    pairList.add(tempPart);
                }
            }
        }

        NameValuePair[] pairs = new NameValuePair[pairList.size()];
        for (int i = 0; i < pairList.size(); ++i) {
            pairs[i] = ((NameValuePair) pairList.get(i));
        }

        if ("GET".equalsIgnoreCase(method)) {
            GetMethod getMethod = new GetMethod(url);
            getMethod.setQueryString(pairs);
            return getMethod;
        } else if ("POST".equalsIgnoreCase(method)) {
            PostMethod postMethod = new PostMethod(url);
            if(postCharset != null){
                postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,postCharset);
            }
            postMethod.setRequestBody(pairs);
            return postMethod;
        } else {
            return null;
        }
    }

    private static String sendHttpMethod(HttpMethod httpMethod,
                                         String defaultEncoding) {
        try {
            int status = httpClient.executeMethod(httpMethod);
            if (status != 200) {
//                SysLogUtils.info("访问请求返回状态码[" + status + "]，非200。");
            }
            String charset = defaultEncoding;
            try {
                Header header = httpMethod.getResponseHeader("Content-Type");
                HeaderElement[] elements = header.getElements();

                for (int i = 0; i < elements.length; i++) {
                    if ("charset".equalsIgnoreCase(elements[i].getName())) {
                        charset = elements[i].getValue();
                    }
                }
            } catch (Exception e) {
//                SysLogUtils.debug("无法解析返回HTTP协议头中的编码格式，使用默认编码方式进行解析", e);
            }

//            SysLogUtils.debug("HTTP返回编码格式：" + charset);

            return streamToString(httpMethod.getResponseBodyAsStream(), charset);

        } catch (Throwable e) {
//            SysLogUtils.info("访问网络发生异常", e);
        } finally {
            if (httpMethod != null) {
                httpMethod.releaseConnection();
            }
        }

        return null;
    }

    private static String streamToString(InputStream is, String encoding) {
        InputStreamReader isr = null;
        try {
            StringBuffer strBuff = new StringBuffer();
            isr = new InputStreamReader(is, encoding);
            char[] buff = new char[BUFFER_SIZE];
            int len = 0;
            while ((len = isr.read(buff)) != -1) {
                char[] temp = new char[len];
                System.arraycopy(buff, 0, temp, 0, len);
                strBuff.append(temp);
            }
            return strBuff.toString();
        } catch (Exception e) {
//            SysLogUtils.info("读取HTTP返回内容失败", e);
            return "";
        } finally {
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {

                }
            }

            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // ignore Exception
                }
            }
        }
    }

    public static JSONObject postForForm(String url, Map<String, String> parms) {
        HttpPost httpPost = new HttpPost(url);
        ArrayList<BasicNameValuePair> list = new ArrayList<>();
        parms.forEach((key, value) -> list.add(new BasicNameValuePair(key, value)));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            if (Objects.nonNull(parms) && parms.size() >0) {
                httpPost.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
            }
            InputStream content = httpPost.getEntity().getContent();
            InputStreamReader inputStreamReader = new InputStreamReader(content, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String readLine = bufferedReader.readLine();
            String s = URLDecoder.decode(readLine, "UTF-8");
            System.out.println("readLine===================================" + readLine);
            System.out.println("s==========================================" + s);
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            JSONObject jsonObject = JSON.parseObject(EntityUtils.toString(entity, "UTF-8"));
            return jsonObject;
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (Objects.nonNull(httpClient)){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }



}
