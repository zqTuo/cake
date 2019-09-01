package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.ProductTasteEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品口味表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-29 05:38:16
 */
@Mapper
public interface ProductTasteDao extends BaseMapper<ProductTasteEntity> {

    List<String> findDetail(@Param("id") long id);

    void del(@Param("ids") Long[] ids);
}
