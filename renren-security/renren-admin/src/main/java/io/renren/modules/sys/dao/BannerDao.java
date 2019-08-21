package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.BannerEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * banner表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 18:11:13
 */
@Mapper
public interface BannerDao extends BaseMapper<BannerEntity> {
	
}
