package io.renren.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.annotation.Login;
import io.renren.common.result.Result;
import io.renren.common.utils.Constant;
import io.renren.common.utils.HttpUtil;
import io.renren.common.utils.RandomStringGenerator;
import io.renren.common.utils.Signature;
import io.renren.common.utils.wepay.WXPayUtil;
import io.renren.common.validator.ValidatorUtils;
import io.renren.entity.ProductEntity;
import io.renren.entity.ShopOrderEntity;
import io.renren.entity.ShopOrderItemEntity;
import io.renren.entity.WxuserEntity;
import io.renren.form.PayForm;
import io.renren.form.WechatPay;
import io.renren.service.ProductService;
import io.renren.service.ShopOrderItemService;
import io.renren.service.ShopOrderService;
import io.renren.service.WxuserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/8/27 22:11
 */
@RestController
@RequestMapping("/api/pay")
@Api(tags = "微信支付接口控制器")
public class PayController {
    private static Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Resource
    private ShopOrderService orderService;
    @Resource
    private ShopOrderItemService orderItemService;
    @Resource
    private ProductService productService;
    @Resource
    private WxuserService wxuserService;
    @Value("${project.url_pre}")
    private String url_pre;
    @Value("${weCat.appId}")
    private String weCatAppId;
    @Value("${weCat.mchId}")
    private String mchId;
    @Value("${weCat.payApi}")
    private String pay_api;
    @Value("${weCat.key}")
    private String key;
    @Resource
    private Signature signature;
    @Resource
    private WXPayUtil wxPayUtil;


    @Login
    @ApiOperation(value = "微信预支付接口")
    @PostMapping("userPay")
    public Result<WechatPay> userPay(@RequestBody PayForm form, @ApiIgnore @RequestAttribute("userId")long userId, HttpServletRequest request){
        ValidatorUtils.validateEntity(form);

        ShopOrderEntity orderEntity = orderService.getOne(new QueryWrapper<ShopOrderEntity>().eq("order_no",form.getOrderNo()));
        if(orderEntity == null){
            return new Result().error("订单不存在");
        }

        if(orderEntity.getOrderState() == Constant.ORDER_PAY_SUCCESS){
            return new Result().error("订单已支付成功，请勿重复支付");
        }

        List<ShopOrderItemEntity> orderItemEntityList = orderItemService.list(
                new QueryWrapper<ShopOrderItemEntity>().eq("order_no",form.getOrderNo()));

        for (ShopOrderItemEntity orderItem:orderItemEntityList){
            ProductEntity productEntity = productService.getById(orderItem.getProductId());
            if(productEntity.getProductFlag() == 0){
                return new Result().error(productEntity.getProductName() + "已下架！");
            }
        }

        log.info("预支付订单：" + form.getOrderNo());

        WxuserEntity user = wxuserService.getById(userId);

        //32位随机字符串
        String noncestr = RandomStringGenerator.getRandomStringByLength(32);
        //时间戳
        String time = String.valueOf(new Date().getTime()).substring(0, 10);
        //发起请求的ip地址
        String ipAddr = HttpUtil.getIpAddr(request);
        //用户OPENID
        String openid = user.getUserOpenid();
        //微信支付成功回调地址
        String notify_url = url_pre + "/api/pay/paySuccess";

        //支付金额 单位 分
        int price =  orderEntity.getOrderPrice().multiply(new BigDecimal("100")).intValue();

        String desc = "蛋糕预订";
        if(orderEntity.getOrderSourceType() == 1){
            desc = "预约烘焙课程";
        }else if(orderEntity.getOrderSourceType() == 2){
            desc = "购买会员";
        }

        //支付类型
        String type = Constant.WX_PAYTYPE_FRO_WX;

        String xml = orderService.generatePayOrderXml(weCatAppId, mchId, noncestr, desc, form.getOrderNo(),price, ipAddr, notify_url, openid, type);

        log.info(form.getOrderNo() + ",支付参数：" + xml);

        JSONObject resultJSON = HttpUtil.wxPayByHttps(pay_api, "POST", xml);

        if(resultJSON.getString("prepay_id") == null){
            return new Result<>().error("请求微信支付错误，请重试或联系客服");
        }

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("appId", weCatAppId);
        map.put("timeStamp", time);
        map.put("nonceStr", noncestr);
        map.put("package", "prepay_id=" + resultJSON.getString("prepay_id"));
        map.put("signType", "MD5");
        String sign = signature.getSign(map);

        WechatPay wechatPay = WechatPay.builder().appid(weCatAppId).noncestr(noncestr)
                .time(time).signature(sign).prepay_id(resultJSON.getString("prepay_id")).build();

        return new Result().ok(wechatPay);
    }

    @RequestMapping("paySuccess")
    @ResponseBody
    public String paySuccess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //************** 获取微信支付返回值 *******************
        log.info("===================== 微信支付回调 ==================");
        Map<String, String> map = null;
        try {
            InputStream in = request.getInputStream();
            BufferedReader br = new BufferedReader( new InputStreamReader( in, "UTF-8" ) );
            StringBuilder result = new StringBuilder();
            String line = "";
            while ( ( line = br.readLine() ) != null ) {
                result.append(line);
            }

            map = wxPayUtil.xmlToMap( result.toString());
            log.info("================= 微信支付回调map参数：" + map);

            if(map == null){
                return "FAIL";
            }

            boolean isSafe = WXPayUtil.isSignatureValid(map,key);
            if(!isSafe){
                log.info("=========== 回调不安全！ =================");
                return "FAIL";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "FAIL";
        }

        /***************** END ****************/

        String return_code = map.get("return_code");
        String errMsg = map.get("err_code_des");
        String out_trade_no = map.get("out_trade_no");

        if (!return_code.trim().equals("SUCCESS")) {
            log.error(out_trade_no + "支付失败:" + errMsg);
            return "FAIL";
        }

        ShopOrderEntity order = orderService.getOne(new QueryWrapper<ShopOrderEntity>()
                .eq("order_no",out_trade_no));

        if (null == order) {
            log.error("找不到该订单信息：" + out_trade_no);
            return "FAIL";
        }
        if(order.getOrderState() == Constant.ORDER_PAY_SUCCESS){
            log.error("该订单信息：已支付成功！ 微信重复回调，暂不处理：" + out_trade_no);
            return "SUCCESS";
        }

        orderService.payFinished(order);

        log.info("=============订单：" + out_trade_no + " 微信支付完处理成功！======================");

        return "SUCCESS";
    }

}
