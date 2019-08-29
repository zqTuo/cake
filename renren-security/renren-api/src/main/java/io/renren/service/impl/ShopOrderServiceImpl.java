package io.renren.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.Constant;
import io.renren.common.utils.Signature;
import io.renren.dao.OrderSalesDao;
import io.renren.dao.ShopOrderDao;
import io.renren.dao.WxuserDao;
import io.renren.dto.OrderDto;
import io.renren.entity.OrderSalesEntity;
import io.renren.entity.ShopOrderEntity;
import io.renren.entity.WxuserEntity;
import io.renren.form.MyOrderForm;
import io.renren.service.ShopOrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Service("shopOrderService")
public class ShopOrderServiceImpl extends ServiceImpl<ShopOrderDao, ShopOrderEntity> implements ShopOrderService {
    private static Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Resource
    private Signature signature;
    @Resource
    private OrderSalesDao orderSalesDao;
    @Resource
    private WxuserDao wxuserDao;

    @Override
    public String generatePayOrderXml(String weCatAppId, String mchId, String noncestr, String desc, String orderNo, int price, String ipAddr, String notify_url, String openid, String type) {
        String openidXml = "";
        String scene_infoXml = "";

        Map<String,Object> map = new LinkedHashMap<>();
        map.put("appid",weCatAppId);
        map.put("mch_id",mchId);
        map.put("nonce_str",noncestr);
        map.put("body",desc);
        map.put("out_trade_no",orderNo);
        map.put("total_fee",price);
        map.put("spbill_create_ip",ipAddr);
        map.put("notify_url",notify_url);
        map.put("trade_type",type);
        if (type.equals(Constant.WX_PAYTYPE_FRO_WX)) {
            map.put("openid",openid);
            openidXml = "<openid>"+openid+"</openid>";
        }
//        if (type.equals(Constant.WX_PAYTYPE_FRO_H5)) {
//            map.put("scene_info",scene_info);
//            scene_infoXml = "<scene_info>"+scene_info+"</scene_info>";
//        }
        String sign = signature.getSign(map);

        return "<xml>"+
                "<appid>" + weCatAppId + "</appid>"+
                "<mch_id>" + mchId + "</mch_id>"+
                "<nonce_str>" + noncestr + "</nonce_str>"+
                "<sign>" + sign + "</sign>"+
                "<body><![CDATA[" + desc + "]]></body>"+
                "<out_trade_no>" + orderNo + "</out_trade_no>"+
                //金额
                "<total_fee>" + price + "</total_fee>"+
                "<spbill_create_ip>" + ipAddr + "</spbill_create_ip>"+
                "<notify_url>" + notify_url + "</notify_url>"+
                "<trade_type>" + type + "</trade_type>"+
                openidXml+
                scene_infoXml+
                "</xml>";
    }

    @Override
    public void payFinished(ShopOrderEntity order) {
        log.info("========== 订单号：" + order.getOrderNo() + ",正在结算中===========");
        //更新订单状态
        order.setPayTime(new Date());
        order.setOrderState(Constant.ORDER_PAY_SUCCESS);
        baseMapper.updateById(order);

        if(order.getOrderPrice().compareTo(new BigDecimal("0")) > 0){
            //生成流水
            OrderSalesEntity salesEntity = OrderSalesEntity.builder().userId(order.getUserId())
                    .orderNo(order.getOrderNo()).orderPrice(order.getOrderPrice())
                    .orderDiscount(order.getOrderDiscount()).sourceType(order.getOrderSourceType())
                    .createTime(new Date()).build();

            orderSalesDao.insert(salesEntity);
            log.info("订单流水生成成功");
        }

        WxuserEntity user = wxuserDao.selectById(order.getUserId());

        if(order.getOrderSourceType() == Constant.ORDER_TYPE_CAKE){ // 购买蛋糕
            //通知用户预订成功

        }else if(order.getOrderSourceType() == Constant.ORDER_TYPE_COURSE){ // 预约烘焙课程

        }else { // 购买会员

        }
    }

    @Override
    public List<OrderDto> findMyOrder(MyOrderForm form, long userId) {
        return baseMapper.findMyOrder(form,userId);
    }

    @Override
    public OrderDto findByOrderNo(String orderNo) {
        return baseMapper.findByOrderNo(orderNo);
    }
}
