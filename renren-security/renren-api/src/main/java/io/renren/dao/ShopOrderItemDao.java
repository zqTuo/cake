package io.renren.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.entity.ShopOrderItemEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单详情表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 16:22:06
 */
@Mapper
public interface ShopOrderItemDao extends BaseMapper<ShopOrderItemEntity> {
	
}
