package io.renren.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.Constant;
import io.renren.common.utils.DateUtil;
import io.renren.common.utils.Signature;
import io.renren.common.utils.notice.CreateJsonUtil;
import io.renren.dao.*;
import io.renren.dto.OrderDto;
import io.renren.entity.*;
import io.renren.form.MyOrderForm;
import io.renren.service.ShopOrderService;
import io.renren.service.WechatAuthService;
import org.apache.commons.lang.StringUtils;
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
    @Resource
    private ShopOrderItemDao shopOrderItemDao;
    @Resource
    private ComboCourseItemDao comboCourseItemDao;
    @Resource
    private CreateJsonUtil createJsonUtil;
    @Resource
    private WechatAuthService wechatAuthService;
    @Resource
    private ShopDao shopDao;
    @Resource
    private ComboUserDao comboUserDao;
    @Resource
    private ComboCourseDao comboCourseDao;

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
        ShopEntity shopEntity = shopDao.selectById(order.getShopId());

        if(order.getOrderSourceType() == Constant.ORDER_TYPE_CAKE){ // 购买蛋糕
            StringBuilder productName = new StringBuilder();
            List<ShopOrderItemEntity> itemList = shopOrderItemDao.selectList(new QueryWrapper<ShopOrderItemEntity>()
                    .eq("order_no",order.getOrderNo()));

            for (ShopOrderItemEntity shopOrderItemEntity:itemList){
                productName.append(shopOrderItemEntity.getProductName());
                if(StringUtils.isNotBlank(shopOrderItemEntity.getDetailSize())){
                    productName.append("，").append(shopOrderItemEntity.getDetailSize());
                }
            }

            //通知用户预订成功
            try {
                //创建交易提醒json包;
                log.info("蛋糕预订通知中：" + user.getId() +" --> " + user.getUserOpenid() );
                JSONObject jsonObject = createJsonUtil.cake_self_Json(order.getOrderNo(),productName.toString(),order.getSendTime(),shopEntity.getShopName(),order.getAddrReceiver(),order.getAddrPhone(),user.getUserOpenid());
                log.info("json包创建成功");
                //发送交易提醒模板消息;
                String accessToken = wechatAuthService.getLastAccessToken();
                String result = createJsonUtil.send_Json(jsonObject.toString(), accessToken);
                log.info("通知结果：" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else if(order.getOrderSourceType() == Constant.ORDER_TYPE_COURSE){ // 预约烘焙课程



        }else if(order.getOrderSourceType() == Constant.ORDER_TYPE_SETCOURSE) { // 购买课程套餐
            log.info("===================正在处理课程套餐==================");
            List<ShopOrderItemEntity> itemList = shopOrderItemDao.selectList(new QueryWrapper<ShopOrderItemEntity>()
                    .eq("order_no",order.getOrderNo()));

            for (ShopOrderItemEntity shopOrderItemEntity:itemList){

                //查询课程套餐
                ComboCourseEntity comboCourseEntity = comboCourseDao.selectById(shopOrderItemEntity.getComboCourseId());
                Date expiredTime = null;
                if(comboCourseEntity.getValidPeriod() > 0){
                    expiredTime = DateUtil.getDayAfterMonth(comboCourseEntity.getValidPeriod(),new Date());
                }

                //查询课程套餐内的课程类别
                List<ComboCourseItemEntity> detailList = comboCourseItemDao.selectList(
                        new QueryWrapper<ComboCourseItemEntity>().eq("combo_course_id",shopOrderItemEntity.getComboCourseId()));

                for (ComboCourseItemEntity comboCourseItemEntity:detailList){ // 课程类别 如：初级课程
                    //给用户加套餐
                    log.info("正在给用户：" + order.getUserId() + ",增加套餐");
                    ComboUserEntity comboUserEntity = comboUserDao.selectOne(new QueryWrapper<ComboUserEntity>()
                            .eq("user_id",order.getUserId()).eq("type_id",comboCourseItemEntity.getTypeId()));

                    if(comboUserEntity == null){
                        log.info("正在新增套餐");
                        comboUserEntity = ComboUserEntity.builder().userId(order.getUserId())
                                .typeId(shopOrderItemEntity.getComboCourseId()).num(comboCourseItemEntity.getNum())
                                .createTime(new Date()).expiredTime(expiredTime)
                                .build();

                        comboUserDao.insert(comboUserEntity);
                        log.info("========= 用户：" + order.getUserId() + "，套餐新增成功：" + comboUserEntity.toString());
                    }else{
                        log.info("正在更新套餐");
                        comboUserEntity.setUpdateTime(new Date());

                        if(comboUserEntity.getExpiredTime() == null || comboUserEntity.getNum() == 0){
                            comboUserEntity.setExpiredTime(DateUtil.getDayAfterMonth(comboCourseEntity.getValidPeriod(),new Date()));
                            comboUserEntity.setNum(comboCourseItemEntity.getNum());
                        }else {
                            comboUserEntity.setExpiredTime(DateUtil.getDayAfterMonth(comboCourseEntity.getValidPeriod(),comboUserEntity.getExpiredTime()));
                            comboUserEntity.setNum(comboUserEntity.getNum() + comboCourseItemEntity.getNum());
                        }

                        comboUserDao.updateById(comboUserEntity);
                        log.info("========= 用户：" + order.getUserId() + "，套餐更新成功：" + comboUserEntity.toString());
                    }
                }
            }

            log.info("================= 购买套餐业务处理完成 ===============");

        }else if(order.getOrderSourceType() == Constant.ORDER_TYPE_USECOMBO) { // 使用套餐课程
            //扣除套餐内次数
            log.info("===================正在处理使用套餐课程==================");
            comboUserDao.reduceNum(order.getComboUserId());
            log.info("===================使用套餐课程处理完成==================");
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

    @Override
    public int countToday(String startDate, String endDate, int sourceType) {
        return baseMapper.countToday(startDate,endDate,sourceType);
    }

}
