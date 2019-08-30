package io.renren.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.annotation.Login;
import io.renren.common.result.Result;
import io.renren.common.utils.Constant;
import io.renren.common.utils.LocationUtils;
import io.renren.common.utils.R;
import io.renren.common.utils.UniqueOrderGenerate;
import io.renren.common.validator.ValidatorUtils;
import io.renren.dto.*;
import io.renren.entity.*;
import io.renren.form.*;
import io.renren.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/8/22 15:24
 */
@RestController
@RequestMapping("/api/order")
@Api(tags="订单接口控制器")
public class OrderController {
    private static Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Resource
    private SendTimeService sendTimeService;
    @Resource
    private ShopOrderItemService orderItemService;
    @Resource
    private ShopOrderService orderService;
    @Resource
    private ProductDetailService productDetailService;
    @Resource
    private AddressService addressService;
    @Resource
    private SendFreightService freightService;
    @Resource
    private CouponUserService couponUserService;
    @Resource
    private ShopService shopService;
    @Resource
    private WxuserService wxuserService;
    @Resource
    private ShoppingCartService shoppingCartService;

    @Value("${project.pic_pre}")
    private String pic_pre;


    @PostMapping("getSendTime")
    @ApiOperation(value = "获取配送时间接口")
    public Result<SendTimeDto> getSendTime(@RequestBody SendTimeForm form) throws ParseException {
        ValidatorUtils.validateEntity(form);
        log.info("获取配送时间参数：" + form.toString());
        List<SendTimeDto> sendTimeEntityList = sendTimeService.getData();

        // 根据派送地址系统时间 计算可选时段
        float distance = freightService.getDistance(form.getShopId(),form.getCity(),form.getAddr());

        if(distance == -1){
            return new Result<>().error("尚未获取派送距离，请核实派送地址是否有误");
        }

        sendTimeService.resolveTimeList(sendTimeEntityList,distance,form.getSelectedDate());
        return new Result<>().ok(sendTimeEntityList);
    }

    @Login
    @PostMapping("getPayPros")
    @ApiOperation(value = "获取结算商品数据接口")
    public Result<PayProDto> getPayPros(@RequestBody PayProForm form, @ApiIgnore @RequestAttribute("userId")long userId){
        ValidatorUtils.validateEntity(form);

        if(form.getSourceFrom() == 0 && form.getProductDetailId() == 0){
            return new Result<>().error("请传入商品详情ID");
        }
        List<PayProDto> payProDtoList = new ArrayList<>();
        if(form.getSourceFrom() == 0){ // 直接购买
            ProductDetailDto productDetail = productDetailService.findPayInfo(form.getProductDetailId());
            PayProDto payProDto = PayProDto.builder().buyNum(form.getBuyNum()).detailCover(pic_pre + productDetail.getDetailCover())
                    .detailName(productDetail.getProductName()).detailPrice(productDetail.getDetailPrice())
                    .productDetailId(productDetail.getId())
                    .detailSize(JSONObject.parseObject(productDetail.getDetailSize()).getString("size"))
                    .detailTaste(JSONObject.parseObject(productDetail.getDetailTaste()).getString("taste"))
                    .build();
            payProDtoList.add(payProDto);
        }else{ // 购物车购买
            payProDtoList = shoppingCartService.getBuyData(userId);
        }

        return new Result<>().ok(payProDtoList);
    }



    @Login
    @PostMapping("getPayMoney")
    @ApiOperation(value = "计算支付金额接口")
    public Result<PayMoneyDto> getPayMoney(@RequestBody PayMoneyForm form, @ApiIgnore @RequestAttribute("userId")long userId){
        ValidatorUtils.validateEntity(form);
        log.info("======= 用户：" + userId + ",结算参数：" + form.toString());

        // 计算购买商品
        BigDecimal totalPrice = new BigDecimal("0");
        List<ProductDetailEntity> detailList = productDetailService.getByIds(form.getProductDetailId());
        for (ProductDetailEntity detail:detailList){
            totalPrice = totalPrice.add(detail.getDetailPrice());
        }

        log.info("购买商品金额：" + totalPrice);
        PayMoneyDto payMoneyDto = new PayMoneyDto();

        payMoneyDto.setPrice(totalPrice);

        if(form.getExtraIds() != null && form.getExtraIds().length > 0){
            //计算加购商品
            BigDecimal extraPrice = productDetailService.countExtraPrice(form.getExtraIds());
            log.info( "加购商品金额：" + extraPrice);
            totalPrice = totalPrice.add(extraPrice);
        }

        if(form.getCouponUserId() > 0){
            //计算优惠券
            BigDecimal discount = couponUserService.calculate(form.getCouponUserId(),totalPrice);
            log.info( "使用优惠券优惠金额：" + discount);
            totalPrice = totalPrice.subtract(discount);
            payMoneyDto.setDiscount(discount);
        }

        if(form.getSendType() == 0){
            //计算配送费
            AddressEntity addressEntity = addressService.getById(form.getAddressId());
            BigDecimal freight = freightService.getFreight(form.getShopId(),addressEntity.getCity(),addressEntity.getAddress());
            log.info("送货上门，运费：" + freight);
            totalPrice = totalPrice.add(freight).setScale(2,BigDecimal.ROUND_HALF_UP);
            payMoneyDto.setTotalPrice(totalPrice);
            payMoneyDto.setFreight(freight);
        }

        return new Result().ok(payMoneyDto);
    }

    @Login
    @ApiOperation(value = "统一下单接口")
    @PostMapping("generate")
    public Result<OrderFormDto> generate(@RequestBody OrderForm form, @ApiIgnore @RequestAttribute("userId")long userId){
        ValidatorUtils.validateEntity(form);

        log.info("====== 用户:" + userId + "统一下单参数：" + form.toString());

        UniqueOrderGenerate idWorker = new UniqueOrderGenerate(0, 0);
        String orderNo = String.valueOf(idWorker.nextId());//雪花算法 生成订单号

        List<ShopOrderItemEntity> orderItemList = new ArrayList<>();

        BigDecimal totalPrice = new BigDecimal("0.00"); // 订单总价
        BigDecimal discountFee = new BigDecimal("0.00"); // 优惠金额

        int orderState = Constant.ORDER_UNPAY; // 是否已经抵扣完的订单状态

        if(form.getSourceType() == 0){//蛋糕预订
            JSONArray proArr = JSONArray.parseArray(form.getProds());
            for (int i = 0; i < proArr.size(); i++) {
                long detailId = proArr.getJSONObject(i).getLong("detailId");
                int buyNum = proArr.getJSONObject(i).getInteger("buyNum");
                log.info("******* 订单条目[" + i + "] 购买蛋糕商品详情id：" + detailId + ",购买数量：" + buyNum);

                ProductDetailDto productDetail = productDetailService.findPayInfo(detailId);
                if(productDetail == null || productDetail.getProductFlag() == 0){
                    String errMsg = productDetail == null ? "" : productDetail.getProductName();
                    log.info("xxxxxxxx 商品：" + errMsg + "已下架,id:" + detailId);
                    return new Result<>().error(errMsg + "已下架！");
                }

                ShopOrderItemEntity orderItemEntity = ShopOrderItemEntity.builder().orderNo(orderNo).productName(productDetail.getProductName())
                        .productId(productDetail.getProductId()).detailCover(productDetail.getDetailCover())
                        .detailPrice(productDetail.getDetailPrice())
                        .detailSize(JSONObject.parseObject(productDetail.getDetailSize()).getString("size"))
                        .detailTaste(JSONObject.parseObject(productDetail.getDetailTaste()).getString("taste"))
                        .buyNum(buyNum).userMember(0).courseId(0L).build();

                orderItemList.add(orderItemEntity);

                log.info("条目价格："+ orderItemEntity.getDetailPrice() +",订单详情生成:" + orderItemEntity.toString());
                totalPrice = totalPrice.add(orderItemEntity.getDetailPrice());

            }

            orderItemService.saveBatch(orderItemList);
            log.info("********** 订单条目：" + orderItemList.size() + "已录入成功！******** ");

            log.info("订单原始支付金额：" + totalPrice);

            if(form.getCouponUserId() > 0){
                //计算优惠券
                BigDecimal discount = couponUserService.calculate(form.getCouponUserId(),totalPrice);
                log.info( "使用优惠券优惠金额：" + discount);
                totalPrice = totalPrice.subtract(discount);
                discountFee = discountFee.add(discount);
            }

            String sendAddr = "";
            String addrReceiver = "";
            String addrPhone = "";
            if(form.getSendType() == 0){
                //计算配送费
                AddressEntity addressEntity = addressService.getById(form.getAddressId());

                sendAddr = "广东省|" + addressEntity.getCity() + "|" + addressEntity.getAddress();
                addrReceiver = addressEntity.getContract();
                addrPhone = addressEntity.getPhone();

                BigDecimal freight = freightService.getFreight(form.getShopId(),addressEntity.getCity(),addressEntity.getAddress());
                log.info("送货上门，运费：" + freight);
                totalPrice = totalPrice.add(freight).setScale(2,BigDecimal.ROUND_HALF_UP);
            }else{
                ShopEntity shopEntity = shopService.getById(form.getShopId());
                sendAddr = shopEntity.getShopName() + " 自提";
                WxuserEntity user = wxuserService.getById(userId);
                addrPhone = user.getUserPhone();
                addrReceiver = user.getUserName();
            }

            if(totalPrice.compareTo(new BigDecimal("0")) <= 0){
                totalPrice = new BigDecimal("0");
                orderState = Constant.ORDER_PAY_SUCCESS;
            }
            ShopOrderEntity orderEntity = ShopOrderEntity.builder().orderNo(orderNo).userId(userId)
                    .orderPrice(totalPrice).orderDiscount(discountFee).orderDiscountType(form.getDiscountType())
                    .couponUserId(form.getCouponUserId()).orderState(orderState).orderSourceType(form.getSourceType())
                    .orderRemark(form.getOrderRemark()).addrDetail(sendAddr).sendType(form.getSendType())
                    .addrPhone(addrPhone).addrReceiver(addrReceiver).sendTime(form.getSendTime())
                    .createTime(new Date()).payType(0).shopId(form.getShopId())
                    .build();

            orderService.save(orderEntity);
            log.info("******** 订单创建成功：" + orderEntity.toString());

            if (form.getSourceFrom() == 1) {
                shoppingCartService.remove(new QueryWrapper<ShoppingCartEntity>().eq("user_id",userId));
                log.info("已删除购物车中的购买商品");
            }

            if(orderState == Constant.ORDER_PAY_SUCCESS){
                orderService.payFinished(orderEntity);
            }


        }

        OrderFormDto res = OrderFormDto.builder().orderNo(orderNo).orderState(orderState).build();

        return new Result<>().ok(res);
    }

    @Login
    @ApiOperation(value = "订单详情接口")
    @PostMapping("detail")
    public Result<OrderDto> detail(@RequestBody OrderNoForm form){
        ValidatorUtils.validateEntity(form);
        OrderDto orderDto = orderService.findByOrderNo(form.getOrderNo());

        return new Result<>().ok(orderDto);
    }

    @Login
    @ApiOperation(value = "取消订单接口")
    @PostMapping("cancel")
    public R cancel(@RequestBody OrderNoForm form){
        ValidatorUtils.validateEntity(form);

        ShopOrderEntity shopOrderEntity = orderService.getOne(new QueryWrapper<ShopOrderEntity>()
                .eq("order_no",form.getOrderNo()));

        if(shopOrderEntity.getOrderSourceType() == Constant.ORDER_TYPE_COURSE){
            // todo 处理课程取消距离上课时间24小时内不可以取消 48小时内取消需要扣除课程费用30%

        }

        shopOrderEntity.setOrderState(Constant.ORDER_CANCELED);
        shopOrderEntity.setCancelTime(new Date());

        orderService.updateById(shopOrderEntity);
        return R.ok();
    }


    @Login
    @PostMapping("getOrders")
    @ApiOperation(value = "获取订单列表接口")
    public Result<OrderDto> getOrders(@RequestBody MyOrderForm form, @ApiIgnore @RequestAttribute("userId")long userId){
        ValidatorUtils.validateEntity(form);

        form.setPageNo(form.getPageNo() > 0 ? (form.getPageNo()-1) * form.getPageSize() : 0);

        List<OrderDto> orderDtos = orderService.findMyOrder(form,userId);
        for (OrderDto orderDto:orderDtos){
            for (OrderItemDto orderItemDto:orderDto.getOrderItemList()){
                orderItemDto.setDetailCover(pic_pre + orderItemDto.getDetailCover());
            }
        }

        return new Result<>().ok(orderDtos);
    }
}
