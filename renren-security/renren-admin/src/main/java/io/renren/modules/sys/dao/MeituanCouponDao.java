package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.MeituanCouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
