package io.renren.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.entity.ComboTypeEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 套餐课程类别表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-12 15:49:25
 */
@Mapper
public interface ComboTypeDao extends BaseMapper<ComboTypeEntity> {
	
}
