package io.renren.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.entity.ProductDetailEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品详情表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-19 16:35:41
 */
@Mapper
public interface ProductDetailDao extends BaseMapper<ProductDetailEntity> {
	
}
