package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.ProductDetailEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 商品详情表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 21:51:22
 */
@Mapper
public interface ProductDetailDao extends BaseMapper<ProductDetailEntity> {

    void del(@Param("ids") Long[] ids);
}
