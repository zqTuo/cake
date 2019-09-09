package io.renren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.dto.CategoryDto;
import io.renren.entity.ProductCategoryEntity;

import java.util.List;


/**
 * 商品种类表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 16:22:06
 */
public interface ProductCategoryService extends IService<ProductCategoryEntity> {

    List<CategoryDto> getProCateData(Integer sourceType);
}

