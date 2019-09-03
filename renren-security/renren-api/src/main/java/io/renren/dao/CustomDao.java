package io.renren.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.entity.CustomEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 微信客服表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-03 11:34:56
 */
@Mapper
public interface CustomDao extends BaseMapper<CustomEntity> {
	
}
