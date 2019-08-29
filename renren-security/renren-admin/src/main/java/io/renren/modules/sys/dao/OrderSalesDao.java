package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.OrderSalesEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 财务流水表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-28 21:42:09
 */
@Mapper
public interface OrderSalesDao extends BaseMapper<OrderSalesEntity> {
	
}
