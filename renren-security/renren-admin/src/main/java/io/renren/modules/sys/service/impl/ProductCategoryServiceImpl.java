package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.ProductCategoryDao;
import io.renren.modules.sys.entity.ProductCategoryEntity;
import io.renren.modules.sys.service.ProductCategoryService;


@Service("productCategoryService")
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryDao, ProductCategoryEntity> implements ProductCategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductCategoryEntity> page = this.page(
                new Query<ProductCategoryEntity>().getPage(params),
                new QueryWrapper<ProductCategoryEntity>()
        );

        return new PageUtils(page);
    }

}
