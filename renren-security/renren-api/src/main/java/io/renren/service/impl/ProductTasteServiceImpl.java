package io.renren.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.dao.ProductTasteDao;
import io.renren.entity.ProductTasteEntity;
import io.renren.service.ProductTasteService;


@Service("productTasteService")
public class ProductTasteServiceImpl extends ServiceImpl<ProductTasteDao, ProductTasteEntity> implements ProductTasteService {


}
