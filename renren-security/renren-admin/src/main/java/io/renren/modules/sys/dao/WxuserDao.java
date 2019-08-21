package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.WxuserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 16:22:06
 */
@Mapper
public interface WxuserDao extends BaseMapper<WxuserEntity> {
	
}
