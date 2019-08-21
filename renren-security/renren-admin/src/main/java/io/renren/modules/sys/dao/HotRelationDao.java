package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.HotRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 热销栏目与商品关联表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 17:12:34
 */
@Mapper
public interface HotRelationDao extends BaseMapper<HotRelationEntity> {
	
}
