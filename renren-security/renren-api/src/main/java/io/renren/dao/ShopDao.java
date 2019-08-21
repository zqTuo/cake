package io.renren.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.dto.ShopDto;
import io.renren.entity.ShopEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 门店表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-19 16:35:41
 */
@Mapper
public interface ShopDao extends BaseMapper<ShopEntity> {

    List<ShopDto> findAll();
}
