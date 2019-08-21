package io.renren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.dto.HotColumnDto;
import io.renren.dto.ProductDto;
import io.renren.entity.ProductEntity;
import io.renren.form.PageForm;

import java.util.List;
import java.util.Map;

/**
 * 商品表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-19 16:35:41
 */
public interface ProductService extends IService<ProductEntity> {

    List<HotColumnDto> findAllHotColumn();

    List<ProductDto> findHotProduct(PageForm form);
}

