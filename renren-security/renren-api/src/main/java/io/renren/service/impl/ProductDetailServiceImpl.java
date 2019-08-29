package io.renren.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.dao.ProductDetailDao;
import io.renren.dto.ProductDetailDto;
import io.renren.dto.ProductInfoDetailDto;
import io.renren.entity.ProductDetailEntity;
import io.renren.service.ProductDetailService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service("productDetailService")
public class ProductDetailServiceImpl extends ServiceImpl<ProductDetailDao, ProductDetailEntity> implements ProductDetailService {

    @Override
    public BigDecimal countExtraPrice(long[] extraIds) {
        return baseMapper.countExtraPrice(extraIds);
    }

    @Override
    public ProductDetailDto findPayInfo(long detailId) {
        return baseMapper.findPayInfo(detailId);
    }

    @Override
    public List<ProductDetailEntity> getByIds(long[] ids) {
        return baseMapper.getByIds(ids);
    }

    @Override
    public List<ProductInfoDetailDto> getByProductId(long id) {
        return baseMapper.getByProductId(id);
    }

}
