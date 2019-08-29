package io.renren.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.dto.CouponDto;
import io.renren.entity.CouponUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 优惠券用户表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 16:22:06
 */
@Mapper
public interface CouponUserDao extends BaseMapper<CouponUserEntity> {

    List<CouponDto> getMyCoupon(@Param("userId") long userId, @Param("sourceType") int sourceType);

    CouponDto findById(@Param("id") long id);
}
