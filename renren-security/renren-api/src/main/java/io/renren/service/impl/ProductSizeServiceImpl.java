package io.renren.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.dao.ProductSizeDao;
import io.renren.entity.ProductSizeEntity;
import io.renren.service.ProductSizeService;


@Service("productSizeService")
public class ProductSizeServiceImpl extends ServiceImpl<ProductSizeDao, ProductSizeEntity> implements ProductSizeService {


}
