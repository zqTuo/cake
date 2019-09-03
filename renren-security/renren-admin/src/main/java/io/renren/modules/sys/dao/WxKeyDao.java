package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.WxKeyEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 微信关键字表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-03 17:29:29
 */
@Mapper
public interface WxKeyDao extends BaseMapper<WxKeyEntity> {
	
}
