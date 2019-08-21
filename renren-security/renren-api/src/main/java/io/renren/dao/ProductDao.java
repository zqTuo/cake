package io.renren.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.dto.HotColumnDto;
import io.renren.dto.ProductDto;
import io.renren.entity.ProductEntity;
import io.renren.form.PageForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-19 16:35:41
 */
@Mapper
public interface ProductDao extends BaseMapper<ProductEntity> {

    List<HotColumnDto> findAllHotColumn();

    List<ProductDto> findHotProduct(@Param("form") PageForm form);
}
