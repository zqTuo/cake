package io.renren.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.dao.ProductCategoryDao;
import io.renren.dto.CategoryDto;
import io.renren.entity.ProductCategoryEntity;
import io.renren.service.ProductCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("productCategoryService")
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryDao, ProductCategoryEntity> implements ProductCategoryService {

    @Override
    public List<CategoryDto> getProCateData(Integer sourceType) {
        return baseMapper.getProCateData(sourceType);
    }
}
