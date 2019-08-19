package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.ProductEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-19 16:35:41
 */
@Mapper
public interface ProductDao extends BaseMapper<ProductEntity> {
	
}
