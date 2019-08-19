package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.ProductCategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品种类表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-19 16:35:41
 */
@Mapper
public interface ProductCategoryDao extends BaseMapper<ProductCategoryEntity> {
	
}
