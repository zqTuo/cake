package io.renren.controller;

import io.renren.annotation.Login;
import io.renren.common.result.Result;
import io.renren.common.utils.JodaTimeUtil;
import io.renren.common.validator.ValidatorUtils;
import io.renren.dto.CouponDto;
import io.renren.entity.ComboCourseEntity;
import io.renren.entity.CourseEntity;
import io.renren.form.CouponForm;
import io.renren.service.ComboCourseService;
import io.renren.service.CouponUserService;
import io.renren.service.CourseService;
import io.renren.service.ProductDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: Clarence
 * @Description:
 * @Date: 2019/8/25 23:16.
 */
@RestController
@RequestMapping("/api/coupon")
@Api(tags = "优惠券接口控制器")
public class CouponController {
    private static Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
    @Resource
    private ProductDetailService productDetailService;

    @Resource
    private CouponUserService couponUserService;
    @Resource
    private CourseService courseService;
    @Resource
    private ComboCourseService comboCourseService;

    @Login
    @PostMapping("getMyCoupon")
    @ApiOperation(value = "获取我的优惠券接口")
    public Result<CouponDto> getMyCoupon(@RequestBody CouponForm form, @ApiIgnore @RequestAttribute("userId")long userId){
        ValidatorUtils.validateEntity(form);

        if(form.getSourceType() == 0 && form.getProds() == null){
            return new Result<>().error("请传入商品参数");
        }else if(form.getSourceType() == 2 && form.getCourseId() == 0){
            return new Result<>().error("请传入课程ID");
        }else if(form.getSourceType() == 3 && form.getComboCourseId() == 0){
            return new Result<>().error("请传入课程套餐ID");
        }


        // 查询可用状态的优惠券

        BigDecimal totalPrice = new BigDecimal("0");

        if(form.getSourceType() == 0){
            totalPrice = productDetailService.countTotalPrice(form.getProds());
            log.info("商品购买总金额：" + totalPrice);
        }else if(form.getSourceType() == 2){
            CourseEntity courseEntity = courseService.getById(form.getCourseId());
            totalPrice = courseEntity.getPrice();
            log.info("课程预订总金额：" + totalPrice);
        }else if(form.getSourceType() == 3){
            ComboCourseEntity comboCourseEntity = comboCourseService.getById(form.getComboCourseId());
            totalPrice = comboCourseEntity.getPrice();
            log.info("课程套餐预订总金额：" + totalPrice);
        }

        List<CouponDto> couponList = new ArrayList<>();

        List<CouponDto> couponDtoList = couponUserService.getMyCoupon(userId,form.getSourceType());
        for (CouponDto couponDto:couponDtoList){
            if(couponDto.getEndTime() != null){ // 说明限时
                if(JodaTimeUtil.isExpired(new Date(),couponDto.getEndTime())){ // 已经过期了
                    couponDto.setDateFlag(2);
                    log.info("==》优惠券：" + couponDto.toString() + "，已过期！");
                    continue;
                }else{
                    couponDto.setDateFlag(1);
                }
                couponDto.setExpiredTime(JodaTimeUtil.dateToStr(couponDto.getEndTime(),"yyyy年MM月dd日") + "前");
            }else{
                couponDto.setExpiredTime("无期限限制");
            }

            if(form.getSourceType() != 1){ // 预定蛋糕结算页
                //判断订单价格是否达到触发价格
                if(totalPrice.compareTo(couponDto.getPrice()) < 0){
                    log.info("==》优惠券：" + couponDto.toString() + ",订单未达到触发价格，不可用");
                    continue;
                }
            }

            if(couponDto.getCouponType() == 0){
                couponDto.setDesc("用于购买蛋糕、下午茶等商品使用");
            }else if(couponDto.getCouponType() == 1){
                couponDto.setDesc("仅限于订购单次体验课程使用使用");
            }else{
                couponDto.setDesc("仅限于订购会员套餐课程使用");
            }

            couponList.add(couponDto);
        }

        return new Result<>().ok(couponList);
    }
}
