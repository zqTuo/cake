package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.HotColumnEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 首页热销栏目表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 17:12:34
 */
@Mapper
public interface HotColumnDao extends BaseMapper<HotColumnEntity> {
	
}
