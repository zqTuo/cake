package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.CouponEntity;

import java.util.Map;

/**
 * 优惠券表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-25 23:30:01
 */
public interface CouponService extends IService<CouponEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

