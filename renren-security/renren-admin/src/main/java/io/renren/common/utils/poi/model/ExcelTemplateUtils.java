package io.renren.common.utils.poi.model;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author: Clarence
 * @Description:
 * @Date: 2018/3/16 17:02.
 */
public class ExcelTemplateUtils {
    private static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    private static final String TEMPLATE_LOCATIONS = "excelTemplate";

    public static List<PropertyModel> getTemplateConfig(String templateName){
        List<PropertyModel> propertyModels = new ArrayList<PropertyModel>();

        if(!StringUtils.isEmpty(templateName)){
            String filePath = TEMPLATE_LOCATIONS + File.separator + templateName + ".xml";
            logger.info("模板路径为：" + filePath);

            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
            BufferedReader bufferedReader = null;
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"GBK"));
                StringBuffer stringBuffer = new StringBuffer();
                String line = null;
                while ((line = bufferedReader.readLine()) != null){
                    stringBuffer.append(line);
                }
//                excelModel = XMLParser.xml2Object(stringBuffer.toString(),ExcelModel.class);

                //采用dom4j 解析xml
                propertyModels = getObject(stringBuffer.toString());
            } catch (IOException e) {
                logger.warn("文件" + filePath + "读写异常：" + e.getMessage());
            }finally {
                if(bufferedReader != null || inputStream != null){
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return propertyModels;
    }

    /**
     *  dom4j 解析excel xml转对象
     * @param xmlString
     * @return
     */
    public static List<PropertyModel> getObject(String xmlString) {
        org.dom4j.Document document=null;
        try {
            document = DocumentHelper.parseText(xmlString);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        //获取根节点
        org.dom4j.Element root = document.getRootElement();
        //迭代出所有的子节点
        Iterator its=root.elementIterator();

        List<PropertyModel> properties = new ArrayList<PropertyModel>();

        while(its.hasNext()){
            org.dom4j.Element et=(org.dom4j.Element) its.next();
            if("property".equals(et.getName())){
                //迭代properties底下元素
                PropertyModel propertyModel = new PropertyModel();

                for (Iterator iterator = et.attributeIterator();iterator.hasNext();){
                    Attribute attribute = (Attribute) iterator.next();
                    if("name".equals(attribute.getName())){
                        propertyModel.setName(attribute.getStringValue());
                    }else if("title".equals(attribute.getName())){
                        propertyModel.setTitle(attribute.getStringValue());
                    }else if("position".equals(attribute.getName())){
                        propertyModel.setPosition(Integer.parseInt(attribute.getStringValue()));
                    }else if("type".equals(attribute.getName())){
                        propertyModel.setType(attribute.getStringValue());
                    }else if("dateFormat".equals(attribute.getName())){
                        propertyModel.setDateFormat(attribute.getStringValue());
                    }else if("uniqueness".equals(attribute.getName())){
                        propertyModel.setUniqueness(Boolean.parseBoolean(attribute.getStringValue()));
                    }else if("required".equals(attribute.getName())){
                        propertyModel.setRequired(Boolean.parseBoolean(attribute.getStringValue()));
                    }else if("limitLenth".equals(attribute.getName())){
                        propertyModel.setLimitLenth(Integer.parseInt(attribute.getStringValue()));
                    }else if("defaultValue".equals(attribute.getName())){
                        propertyModel.setDefaultValue(attribute.getStringValue());
                    }else if("numberFormat".equals(attribute.getName())){
                        propertyModel.setNumberFormat(Integer.valueOf(attribute.getStringValue()));
                    }
                }
                properties.add(propertyModel);
            }
        }
        return properties;
    }

}
