package io.renren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.dto.CouponDto;
import io.renren.entity.CouponUserEntity;

import java.util.List;

/**
 * 优惠券用户表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 16:22:06
 */
public interface CouponUserService extends IService<CouponUserEntity> {

    List<CouponDto> getMyCoupon(long userId, int sourceType);
}

