package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.ProductDao;
import io.renren.modules.sys.dao.ProductDetailDao;
import io.renren.modules.sys.dao.ProductSizeDao;
import io.renren.modules.sys.dao.ProductTasteDao;
import io.renren.modules.sys.dto.ProductDto;
import io.renren.modules.sys.entity.ProductEntity;
import io.renren.modules.sys.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;


@Service("productService")
@Transactional(rollbackFor = Exception.class)
public class ProductServiceImpl extends ServiceImpl<ProductDao, ProductEntity> implements ProductService {
    @Resource
    private ProductDetailDao productDetailDao;
    @Resource
    private ProductSizeDao productSizeDao;
    @Resource
    private ProductTasteDao productTasteDao;

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

    @Override
    public void delPros(Long[] ids) {
        baseMapper.deleteBatchIds(Arrays.asList(ids));
        productDetailDao.del(ids);
        productSizeDao.del(ids);
        productTasteDao.del(ids);
    }

}
