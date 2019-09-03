package io.renren.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.entity.CustomRecordEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 客服接待记录表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-03 11:34:56
 */
@Mapper
public interface CustomRecordDao extends BaseMapper<CustomRecordEntity> {
	
}
