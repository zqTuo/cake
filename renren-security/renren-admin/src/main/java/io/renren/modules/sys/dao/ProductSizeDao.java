package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.ProductSizeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品尺寸表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-29 05:38:16
 */
@Mapper
public interface ProductSizeDao extends BaseMapper<ProductSizeEntity> {

    List<String> findDetail(@Param("id") long id);

    void del(@Param("ids")Long[] ids);
}
