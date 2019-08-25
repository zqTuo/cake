package io.renren.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.entity.CouponEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 16:22:06
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
