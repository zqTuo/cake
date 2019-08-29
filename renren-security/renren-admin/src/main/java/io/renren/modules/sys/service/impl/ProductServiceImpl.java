package io.renren.modules.sys.service.impl;

import io.renren.modules.sys.dto.ProductDto;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.ProductDao;
import io.renren.modules.sys.entity.ProductEntity;
import io.renren.modules.sys.service.ProductService;


@Service("productService")
public class ProductServiceImpl extends ServiceImpl<ProductDao, ProductEntity> implements ProductService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductEntity> page = this.page(
                new Query<ProductEntity>().getPage(params),
                new QueryWrapper<ProductEntity>()
        );
        return new PageUtils(page);
    }

    @Override
    public PageUtils findByPage(Map<String, Object> params) {
        IPage<ProductDto> page = baseMapper.findByPage(new Query<ProductDto>().getPage(params));
        return new PageUtils(page);
    }

}
