package io.renren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.dto.ProductDetailDto;
import io.renren.dto.ProductInfoDetailDto;
import io.renren.entity.ProductDetailEntity;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品详情表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-19 16:35:41
 */
public interface ProductDetailService extends IService<ProductDetailEntity> {

    BigDecimal countExtraPrice(long[] extraIds);

    ProductDetailDto findPayInfo(long detailId);

    List<ProductDetailEntity> getByIds(long[] productDetailId);

    List<ProductInfoDetailDto> getByProductId(long id);

    BigDecimal countTotalPrice(String prods);
}

