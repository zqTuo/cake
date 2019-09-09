package io.renren.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.dto.CategoryDto;
import io.renren.entity.ProductCategoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品种类表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 16:22:06
 */
@Mapper
public interface ProductCategoryDao extends BaseMapper<ProductCategoryEntity> {

    List<CategoryDto> getProCateData(@Param("sourceType") Integer sourceType);
}
