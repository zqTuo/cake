package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.ProductDetailEntity;

import java.util.Map;

/**
 * 商品详情表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 21:51:22
 */
public interface ProductDetailService extends IService<ProductDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

