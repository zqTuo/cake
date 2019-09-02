package io.renren.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.entity.MeituanCouponEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 美团券验券记录表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-01 14:26:01
 */
@Mapper
public interface MeituanCouponDao extends BaseMapper<MeituanCouponEntity> {
	
}
