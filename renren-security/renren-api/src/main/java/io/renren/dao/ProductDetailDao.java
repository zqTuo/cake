package io.renren.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.dto.ProductDetailDto;
import io.renren.dto.ProductInfoDetailDto;
import io.renren.entity.ProductDetailEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品详情表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-19 16:35:41
 */
@Mapper
public interface ProductDetailDao extends BaseMapper<ProductDetailEntity> {

    BigDecimal countExtraPrice(@Param("idArr") long[] extraIds);

    ProductDetailDto findPayInfo(@Param("detailId")long detailId);

    List<ProductDetailEntity> getByIds(@Param("idArr")long[] ids);

    List<ProductInfoDetailDto> getByProductId(@Param("id")long id);
}
