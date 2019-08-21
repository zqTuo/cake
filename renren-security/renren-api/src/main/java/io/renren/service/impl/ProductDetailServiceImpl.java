package io.renren.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.dao.ProductDetailDao;
import io.renren.entity.ProductDetailEntity;
import io.renren.service.ProductDetailService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("productDetailService")
public class ProductDetailServiceImpl extends ServiceImpl<ProductDetailDao, ProductDetailEntity> implements ProductDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductDetailEntity> page = this.page(
                new Query<ProductDetailEntity>().getPage(params),
                new QueryWrapper<ProductDetailEntity>()
        );

        return new PageUtils(page);
    }

}
