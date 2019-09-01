package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.ProductTasteEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品口味表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-29 05:38:16
 */
public interface ProductTasteService extends IService<ProductTasteEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<String> findDetail(long id);
}

