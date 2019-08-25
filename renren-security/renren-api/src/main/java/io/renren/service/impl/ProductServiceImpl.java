package io.renren.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.dao.ProductDao;
import io.renren.dto.HotColumnDto;
import io.renren.dto.ProductDto;
import io.renren.entity.ProductEntity;
import io.renren.form.PageForm;
import io.renren.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("productService")
public class ProductServiceImpl extends ServiceImpl<ProductDao, ProductEntity> implements ProductService {

    @Override
    public List<HotColumnDto> findAllHotColumn() {
        return baseMapper.findAllHotColumn();
    }

    @Override
    public List<ProductDto> findProduct(PageForm form) {
        return baseMapper.findProduct(form);
    }

    @Override
    public List<ProductDto> getExtraInfo() {
        return baseMapper.getExtraInfo();
    }

}
