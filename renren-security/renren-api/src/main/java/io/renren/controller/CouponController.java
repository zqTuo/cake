package io.renren.controller;

import io.renren.annotation.Login;
import io.renren.common.result.Result;
import io.renren.common.utils.JodaTimeUtil;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.dto.CouponDto;
import io.renren.form.CouponForm;
import io.renren.service.CouponService;
import io.renren.service.CouponUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
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
    @Resource
    private CouponUserService couponUserService;

    @Login
    @PostMapping("getMyCoupon")
    @ApiOperation(value = "获取我的优惠券接口")
    public Result<CouponDto> getMyCoupon(@RequestBody CouponForm form, @ApiIgnore @RequestAttribute("userId")long userId){
        ValidatorUtils.validateEntity(form);

        // 查询可用状态的优惠券
        List<CouponDto> couponDtoList = couponUserService.getMyCoupon(userId,form.getSourceType());
        for (CouponDto couponDto:couponDtoList){
            if(couponDto.getEndTime() != null){ // 说明限时
                if(JodaTimeUtil.isExpired(new Date(),couponDto.getEndTime())){ // 已经过期了
                    couponDto.setDateFlag(2);
                }else{
                    couponDto.setDateFlag(1);
                }
                couponDto.setExpiredTime(JodaTimeUtil.dateToStr(couponDto.getEndTime(),"yyyy年MM月dd日") + "前");
            }

            if(couponDto.getCouponType() == 0){
                couponDto.setDesc("用于购买蛋糕、下午茶等商品使用");
            }else if(couponDto.getCouponType() == 1){
                couponDto.setDesc("仅限于订购单次体验课程使用使用");
            }else{
                couponDto.setDesc("仅限于订购会员套餐课程使用");
            }
        }

        return new Result<>().ok(couponDtoList);
    }
}
