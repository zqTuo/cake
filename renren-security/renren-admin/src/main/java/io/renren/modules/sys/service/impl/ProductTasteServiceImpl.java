package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.ProductTasteDao;
import io.renren.modules.sys.entity.ProductTasteEntity;
import io.renren.modules.sys.service.ProductTasteService;


@Service("productTasteService")
public class ProductTasteServiceImpl extends ServiceImpl<ProductTasteDao, ProductTasteEntity> implements ProductTasteService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductTasteEntity> page = this.page(
                new Query<ProductTasteEntity>().getPage(params),
                new QueryWrapper<ProductTasteEntity>()
        );

        return new PageUtils(page);
    }

}
