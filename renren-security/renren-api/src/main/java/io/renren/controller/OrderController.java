package io.renren.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.annotation.Login;
import io.renren.common.result.Result;
import io.renren.common.utils.*;
import io.renren.common.validator.ValidatorUtils;
import io.renren.dto.*;
import io.renren.entity.*;
import io.renren.form.*;
import io.renren.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
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
    @Resource
    private MeituanItemService meituanItemService;
    @Resource
    private CourseService courseService;
    @Resource
    private ComboCourseService comboCourseService;
    @Resource
    private ComboUserService comboUserService;

    @Value("${project.pic_pre}")
    private String pic_pre;


    @PostMapping("getSendTime")
    @ApiOperation(value = "获取配送时间接口")
    public Result<SendTimeDto> getSendTime(@RequestBody SendTimeForm form) throws ParseException {
        ValidatorUtils.validateEntity(form);
        log.info("获取配送时间参数：" + form.toString());
        List<SendTimeDto> sendTimeEntityList = sendTimeService.getData(0);

        // 根据派送地址系统时间 计算可选时段
        float distance = 0;
        if(form.getSendType() == 0){
            distance = freightService.getDistance(form.getShopId(),form.getCity(),form.getAddr());

            if(distance == -1){
                return new Result<>().error("尚未获取派送距离，请核实派送地址是否有误");
            }
        }

        sendTimeService.resolveTimeList(sendTimeEntityList,distance,form.getSelectedDate(),form.getSendType());
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
                    .detailSize(productDetail.getDetailSize())
                    .detailTaste(productDetail.getDetailTaste())
                    .build();
            payProDtoList.add(payProDto);
        }else{ // 购物车购买
            payProDtoList = shoppingCartService.getBuyData(userId);
            for (PayProDto payProDto:payProDtoList){
                payProDto.setDetailCover(pic_pre + payProDto.getDetailCover());
            }
        }

        return new Result<>().ok(payProDtoList);
    }



    @Login
    @PostMapping("getPayMoney")
    @ApiOperation(value = "计算支付金额接口")
    public Result<PayMoneyDto> getPayMoney(@RequestBody PayMoneyForm form, @ApiIgnore @RequestAttribute("userId")long userId){
        ValidatorUtils.validateEntity(form);
        log.info("======= 用户：" + userId + ",结算参数：" + form.toString());

        if(StringUtils.isEmpty(form.getProds())){
            return new Result<>().error("请传入商品参数");
        }

        // 计算购买商品
        BigDecimal totalPrice = productDetailService.countTotalPrice(form.getProds());

        log.info("购买商品金额：" + totalPrice);
        PayMoneyDto payMoneyDto = new PayMoneyDto();

        payMoneyDto.setPrice(totalPrice);
        payMoneyDto.setDiscount(new BigDecimal(0));
        payMoneyDto.setFreight(new BigDecimal(0));

        if(form.getCouponUserId() > 0){
            //计算优惠券
            BigDecimal discount = couponUserService.calculate(form.getCouponUserId(),totalPrice);
            log.info( "使用优惠券优惠金额：" + discount);
            totalPrice = totalPrice.subtract(discount);
            payMoneyDto.setDiscount(discount);
        }

        if(form.getMeituanId() > 0){
            // 计算美团券包含商品金额
            BigDecimal discount = meituanItemService.countPrice(form.getMeituanId());
            log.info( "使用美团商品抵扣券优惠金额：" + discount);
            totalPrice = totalPrice.subtract(discount);
            payMoneyDto.setDiscount(payMoneyDto.getDiscount().add(discount));
        }

        if(form.getSendType() == 0){
            //计算配送费
            AddressEntity addressEntity = addressService.getById(form.getAddressId());
            BigDecimal freight = freightService.getFreight(form.getShopId(),addressEntity.getCity(),addressEntity.getAddress());
            log.info("送货上门，运费：" + freight);
            totalPrice = totalPrice.add(freight).setScale(2,BigDecimal.ROUND_HALF_UP);

            payMoneyDto.setFreight(freight);
        }

        payMoneyDto.setTotalPrice(totalPrice);

        if(totalPrice.compareTo(new BigDecimal("0")) < 0){
            payMoneyDto.setTotalPrice(new BigDecimal("0"));
        }

        return new Result().ok(payMoneyDto);
    }


    @Login
    @PostMapping("getCourseMoney")
    @ApiOperation(value = "计算课程预约支付金额接口")
    public Result<CoursePayDto> getCourseMoney(@RequestBody CourseForm form,@ApiIgnore @RequestAttribute("userId")long userId){
        ValidatorUtils.validateEntity(form);

        CourseEntity courseEntity = courseService.getById(form.getCourseId());
        if(courseEntity == null){
            return new Result<>().error("课程不存在或已下架");
        }

        CoursePayDto coursePayDto = new CoursePayDto();

        if(StringUtils.isNotBlank(form.getSendTime())){
            String time = DateUtil.dateToWeek(form.getSendTime()); // 获取周几
            time = form.getSendTime().split(" ")[0] + "(" + time + ")" + form.getSendTime().split(" ")[1];
            coursePayDto.setSendTime(time);
        }

        ShopEntity shopEntity = shopService.getById(form.getShopId());

        BigDecimal coursePrice = courseEntity.getPrice();


        //查看是否有同行人数
        if(form.getKidNum() == 2 || form.getAdultNum() == 2
                || (form.getAdultNum() == 2 && form.getKidNum() == 1)){ // 2小  2大 2大1小 加收50
            BigDecimal extraPrice = new BigDecimal("50");
            log.info("同行人数额外加收：" + extraPrice);
            coursePrice = coursePrice.add(extraPrice);
        }else if((form.getKidNum() == 2 && form.getAdultNum() == 1) || form.getAdultNum() == 3 || form.getKidNum() == 3){
            BigDecimal extraPrice = new BigDecimal("100");
            log.info("同行人数额外加收：" + extraPrice);
            coursePrice = coursePrice.add(extraPrice);
        }
        coursePayDto.setCoursePrice(coursePrice);

        BigDecimal orderPrice = coursePrice;

        //判断优惠券
        if(form.getCouponUserId() != null && form.getCouponUserId() > 0){
            //计算优惠券
            BigDecimal discount = couponUserService.calculate(form.getCouponUserId(),coursePrice);
            log.info( "使用优惠券优惠金额：" + discount);
            orderPrice = orderPrice.subtract(discount);
            coursePayDto.setDiscountPrice(discount);
        }

        coursePayDto.setOrderPrice(orderPrice);

        coursePayDto.setShopName(shopEntity.getShopName());
        coursePayDto.setUserPhone(form.getUserPhone());
        coursePayDto.setCourseName(courseEntity.getTitle());

        return new Result<>().ok(coursePayDto);
    }


    @Login
    @ApiOperation(value = "统一下单接口")
    @PostMapping("generate")
    public Result<OrderFormDto> generate(@RequestBody OrderForm form, @ApiIgnore @RequestAttribute("userId")long userId){
        ValidatorUtils.validateEntity(form);

        log.info("====== 用户:" + userId + "统一下单参数：" + form.toString());

        String sendTimeHHss = ""; // 配送时间段/上课时间点
        Date sendDate = null; // 配送时间 日 / 上课时间点

        if(form.getSourceType() < Constant.ORDER_TYPE_SETCOURSE){// 蛋糕预订 烘焙课程
            try {
                sendDate = JodaTimeUtil.strToDate(form.getSendTime(),"yyyy-MM-dd HH:mm");
                sendTimeHHss = JodaTimeUtil.getTimeStrForHHss(form.getSendTime());
                SendTimeEntity sendTimeEntity = sendTimeService.getOne(new QueryWrapper<SendTimeEntity>()
                        .eq("start_time",sendTimeHHss).eq("type",0));
                if(sendTimeEntity == null){
                    return new Result<>().error("无此时间派送");
                }

                sendTimeHHss = sendTimeEntity.getStartTime() + "-" + sendTimeEntity.getEndTime();
                log.info("配送时间/上课时间段：" + sendTimeHHss);
            } catch (ParseException e) {
                e.printStackTrace();
                return new Result<>().error("配送时间格式错误，请遵照yyyy-MM-dd HH:ss格式");
            }
        }

        UniqueOrderGenerate idWorker = new UniqueOrderGenerate(0, 0);
        String orderNo = String.valueOf(idWorker.nextId());//雪花算法 生成订单号

        List<ShopOrderItemEntity> orderItemList = new ArrayList<>();

        BigDecimal totalPrice = new BigDecimal("0.00"); // 订单总价
        BigDecimal discountFee = new BigDecimal("0.00"); // 优惠金额
        BigDecimal sendPrice = new BigDecimal("0.00"); // 配送费用

        long comboUserId = 0;//用户课程套餐ID

        int orderState = Constant.ORDER_UNPAY; // 是否已经抵扣完的订单状态

        if(form.getSourceType() == Constant.ORDER_TYPE_CAKE){//蛋糕预订
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

                ShopOrderItemEntity orderItemEntity = ShopOrderItemEntity.builder().orderNo(orderNo)
                        .productName(productDetail.getProductName())
                        .productId(productDetail.getProductId()).detailCover(productDetail.getDetailCover())
                        .detailPrice(productDetail.getDetailPrice())
                        .detailSize(productDetail.getDetailSize())
                        .detailTaste(productDetail.getDetailTaste())
                        .buyNum(buyNum).userMember(0).courseId(0L).build();

                orderItemList.add(orderItemEntity);

                log.info("条目价格："+ orderItemEntity.getDetailPrice() +",订单详情生成:" + orderItemEntity.toString());
                totalPrice = totalPrice.add(orderItemEntity.getDetailPrice().multiply(new BigDecimal(buyNum)));

            }

            orderItemService.saveBatch(orderItemList);
            log.info("********** 订单条目：" + orderItemList.size() + "已录入成功！******** ");

        }else if(form.getSourceType() == Constant.ORDER_TYPE_COURSE){ // 预约课程
            if(form.getCourseId() == null || form.getCourseId() == 0){
                return new Result<>().error("缺少课程参数");
            }
            if(StringUtils.isEmpty(form.getRealName()) || StringUtils.isEmpty(form.getUserPhone())){
                return new Result<>().error("请准确填写预约姓名或手机号");
            }


            CourseEntity courseEntity = courseService.getById(form.getCourseId());
            if(courseEntity == null){
                return new Result<>().error("课程已下架");
            }
            if(form.getAdultNum() + form.getKidNum() > 3){
                return new Result<>().error("同行人数不能超过3人哦");
            }

            ShopOrderItemEntity orderItemEntity = ShopOrderItemEntity.builder().courseId(form.getCourseId())
                    .orderNo(orderNo).productId(0L).productName(courseEntity.getTitle())
                    .productDesc(courseEntity.getCourseDes()).detailCover(courseEntity.getCourseImg())
                    .detailPrice(courseEntity.getPrice()).buyNum(1).userMember(0).build();

            orderItemService.save(orderItemEntity);

            log.info("单次课程订单栏目生成成功：" + orderItemEntity.toString());
            totalPrice = totalPrice.add(courseEntity.getPrice());
            //查看是否有同行人数
            if(form.getKidNum() == 2 || form.getAdultNum() == 2
                    || (form.getAdultNum() == 2 && form.getKidNum() == 1)){ // 2小  2大 2大1小 加收50
                BigDecimal extraPrice = new BigDecimal("50");
                log.info("同行人数额外加收：" + extraPrice);
                totalPrice = totalPrice.add(extraPrice);
            }else if((form.getKidNum() == 2 && form.getAdultNum() == 1) || form.getAdultNum() == 3 || form.getKidNum() == 3){
                BigDecimal extraPrice = new BigDecimal("100");
                log.info("同行人数额外加收：" + extraPrice);
                totalPrice = totalPrice.add(extraPrice);
            }


        }else if(form.getSourceType() == Constant.ORDER_TYPE_SETCOURSE){ // 购买课程套餐
            if(form.getComboCourseId() == null || form.getComboCourseId() == 0){
                return new Result<>().error("请传入课程套餐ID");
            }

            ComboCourseEntity comboCourseEntity = comboCourseService.getById(form.getComboCourseId());
            if(comboCourseEntity == null){
                return new Result<>().error("该课程套餐已下架");
            }

            ShopOrderItemEntity orderItemEntity = ShopOrderItemEntity.builder().comboCourseId(form.getComboCourseId())
                    .orderNo(orderNo).productId(0L).productName(comboCourseEntity.getTitle())
                    .productDesc(comboCourseEntity.getRemark()).detailCover(comboCourseEntity.getPicUrl())
                    .detailPrice(comboCourseEntity.getPrice()).expiredDate(DateUtil.getDayAfterMonth(comboCourseEntity.getValidPeriod(),new Date()))
                    .buyNum(1).userMember(0).courseId(0L).build();

            orderItemService.save(orderItemEntity);

            log.info("课程套餐订单栏目生成成功：" + orderItemEntity.toString());
            totalPrice = totalPrice.add(comboCourseEntity.getPrice());

        }else if(form.getSourceType() == Constant.ORDER_TYPE_USECOMBO){//使用套餐
            if(form.getCourseId() == null || form.getCourseId() == 0){
                return new Result<>().error("请传入课程ID");
            }

            //查看课程
            CourseEntity courseEntity = courseService.getById(form.getCourseId());
            if(courseEntity == null){
                return new Result<>().error("该课程已结束");
            }

            //检测用户这类套餐次数是否使用完
            ComboUserEntity comboUserEntity = comboUserService.getOne(
                    new QueryWrapper<ComboUserEntity>().eq("user_id",userId)
                            .eq("type_id",courseEntity.getComboTypeId())
            );
            if(comboUserEntity == null){
                return new Result().error("您的套餐剩余次数不足！");
            }

            if(comboUserEntity.getNum() <= 0){
                log.info("用户：" + userId + ",套餐内该类课程剩余次数不足！");
                return new Result<>().error("您的套餐内该类课程剩余次数不足！");
            }

            if(comboUserEntity.getExpiredTime() != null && JodaTimeUtil.isExpired(comboUserEntity.getExpiredTime(),new Date())){
                log.info("套餐已过期");
                return new Result().error("您的套餐已过期！");
            }

            ShopOrderItemEntity orderItemEntity = ShopOrderItemEntity.builder().courseId(form.getCourseId())
                    .orderNo(orderNo).productId(0L).productName(courseEntity.getTitle())
                    .productDesc(courseEntity.getCourseDes()).detailCover(courseEntity.getCourseImg())
                    .detailPrice(courseEntity.getPrice())
                    .buyNum(1).userMember(0).courseId(0L).build();

            orderItemService.save(orderItemEntity);

            log.info("用户使用套餐预约课程订单栏目生成成功：" + orderItemEntity.toString());
            orderState = Constant.ORDER_PAY_SUCCESS;
            comboUserId = comboUserEntity.getId();
        }

        log.info("订单原始支付金额：" + totalPrice);

        if(form.getCouponUserId() > 0){
            //计算优惠券
            BigDecimal discount = couponUserService.calculate(form.getCouponUserId(),totalPrice);
            log.info( "使用优惠券优惠金额：" + discount);
            totalPrice = totalPrice.subtract(discount);
            discountFee = discountFee.add(discount);
        }

        if(form.getMeituanId() > 0){
            // 计算美团券包含商品金额
            BigDecimal discount = meituanItemService.countPrice(form.getMeituanId());
            log.info( "使用美团商品抵扣券优惠金额：" + discount);
            totalPrice = totalPrice.subtract(discount);
            discountFee = discountFee.add(discount);
            form.setDiscountType(2);
        }

        if(form.getCouponUserId() > 0 && form.getMeituanId() > 0){
            form.setDiscountType(3);
        }

        String sendAddr = "";
        String addrReceiver = "";
        String addrPhone = "";

        if(form.getSourceType() == 0){
            if(form.getSendType() == 0){
                //计算配送费
                AddressEntity addressEntity = addressService.getById(form.getAddressId());

                sendAddr = "广东省|" + addressEntity.getCity() + "|" + addressEntity.getAddress();
                addrReceiver = addressEntity.getContract();
                addrPhone = addressEntity.getPhone();

                BigDecimal freight = freightService.getFreight(form.getShopId(),addressEntity.getCity(),addressEntity.getAddress());
                log.info("送货上门，运费：" + freight);
                sendPrice = sendPrice.add(freight);
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
        }

        ShopOrderEntity orderEntity = ShopOrderEntity.builder().orderNo(orderNo).userId(userId)
                .orderPrice(totalPrice).orderDiscount(discountFee).orderDiscountType(form.getDiscountType())
                .couponUserId(form.getCouponUserId()).orderState(orderState).orderSourceType(form.getSourceType())
                .orderRemark(form.getOrderRemark()).addrDetail(sendAddr).sendType(form.getSendType())
                .sendPrice(sendPrice).sendTime(sendTimeHHss)
                .addrPhone(addrPhone).addrReceiver(addrReceiver).sendDate(sendDate)
                .createTime(new Date()).payType(0).shopId(form.getShopId()).adultNum(form.getAdultNum())
                .kidNum(form.getKidNum()).comboUserId(comboUserId)
                .build();

        orderService.save(orderEntity);
        log.info("******** 订单创建成功：" + orderEntity.toString());

        if(form.getCouponUserId() > 0){
            //扣除优惠券
            couponUserService.userCoupon(form.getCouponUserId());
            log.info("优惠券：" + form.getCouponUserId());
        }

        if (form.getSourceFrom() == 1) {
            shoppingCartService.remove(new QueryWrapper<ShoppingCartEntity>().eq("user_id",userId));
            log.info("已删除购物车中的购买商品");
        }

        if(orderState == Constant.ORDER_PAY_SUCCESS){
            orderService.payFinished(orderEntity);
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

    @GetMapping("getQrcode")
    @ApiOperation(value = "获取订单二维码接口"
            ,notes = "直接响应图片流，使用示例：<img src=\"/api/order/getQrcode?orderNo=54654654\"/>")
    public void getQrcode(@RequestParam("orderNo") @ApiParam(value = "订单编号",required = true) String orderNo, HttpServletResponse response){
        try {
            QrCodeUtils.writeImage(orderNo,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
