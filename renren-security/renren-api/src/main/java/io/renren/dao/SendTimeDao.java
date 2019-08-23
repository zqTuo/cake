package io.renren.dao;

import io.renren.entity.SendTimeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 配送时间表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 21:10:39
 */
@Mapper
public interface SendTimeDao extends BaseMapper<SendTimeEntity> {
	
}
