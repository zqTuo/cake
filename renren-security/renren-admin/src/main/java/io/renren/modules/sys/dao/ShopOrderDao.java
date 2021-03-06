package io.renren.modules.sys.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.renren.modules.sys.dto.ExcelOrderDto;
import io.renren.modules.sys.dto.ExcelSmallOrderDto;
import io.renren.modules.sys.dto.OrderDto;
import io.renren.modules.sys.entity.ShopOrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 订单表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 16:22:06
 */
@Mapper
public interface ShopOrderDao extends BaseMapper<ShopOrderEntity> {

    List<ExcelOrderDto> getData(@Param("map") Map<String, Object> map);

    IPage<OrderDto> findByPage(IPage<OrderDto> page, @Param("map") Map<String, Object> params);

    List<ExcelSmallOrderDto> getSmallData(@Param("map") Map<String, Object> map);
}
