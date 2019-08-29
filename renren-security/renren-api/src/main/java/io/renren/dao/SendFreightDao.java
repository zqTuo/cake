package io.renren.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.entity.SendFreightEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 运费表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 16:22:06
 */
@Mapper
public interface SendFreightDao extends BaseMapper<SendFreightEntity> {
	
}
