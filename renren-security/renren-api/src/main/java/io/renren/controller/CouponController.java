package io.renren.controller;

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

    @GetMapping("getMyCoupon")
    @ApiOperation(value = "获取我的优惠券接口")
    public R getMyCoupon(@RequestBody CouponForm form,@ApiIgnore@RequestAttribute("userId")long userId){
        ValidatorUtils.validateEntity(form);

        // todo 查询可用状态的优惠券
        List<CouponDto> couponDtoList = couponUserService.getMyCoupon(userId,form.getSourceType());
        for (CouponDto couponDto:couponDtoList){
            couponDto.setCouponName("满" + couponDto.getCouponPrice() + "减" + couponDto.getPrice());
            if(couponDto.getEndTime() != null){ // 说明限时

            }
        }

        return R.ok().put("arrayData",couponDtoList);
    }
}
