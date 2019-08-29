package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.ProductSizeDao;
import io.renren.modules.sys.entity.ProductSizeEntity;
import io.renren.modules.sys.service.ProductSizeService;


@Service("productSizeService")
public class ProductSizeServiceImpl extends ServiceImpl<ProductSizeDao, ProductSizeEntity> implements ProductSizeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductSizeEntity> page = this.page(
                new Query<ProductSizeEntity>().getPage(params),
                new QueryWrapper<ProductSizeEntity>()
        );

        return new PageUtils(page);
    }

}
