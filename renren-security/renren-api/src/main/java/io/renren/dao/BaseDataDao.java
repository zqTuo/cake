package io.renren.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.entity.BaseDataEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统配置表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 12:26:29
 */
@Mapper
public interface BaseDataDao extends BaseMapper<BaseDataEntity> {
	
}
