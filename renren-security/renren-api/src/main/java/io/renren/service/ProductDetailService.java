package io.renren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.entity.ProductDetailEntity;

import java.util.Map;

/**
 * 商品详情表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-19 16:35:41
 */
public interface ProductDetailService extends IService<ProductDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

