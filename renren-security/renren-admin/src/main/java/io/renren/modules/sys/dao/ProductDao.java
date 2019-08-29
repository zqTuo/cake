package io.renren.modules.sys.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.modules.sys.dto.ProductDto;
import io.renren.modules.sys.entity.ProductEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 商品表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-25 22:57:11
 */
@Mapper
public interface ProductDao extends BaseMapper<ProductEntity> {

    IPage<ProductDto> findByPage(IPage<ProductDto> page);
}
