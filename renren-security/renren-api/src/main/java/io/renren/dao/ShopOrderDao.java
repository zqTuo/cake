package io.renren.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.dto.OrderDto;
import io.renren.entity.ShopOrderEntity;
import io.renren.form.MyOrderForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 16:22:06
 */
@Mapper
public interface ShopOrderDao extends BaseMapper<ShopOrderEntity> {

    int countToday(@Param("startTime") String startTime, @Param("endTime") String endTime);

    List<OrderDto> findMyOrder(@Param("form") MyOrderForm form, @Param("userid") long userId);

    OrderDto findByOrderNo(@Param("orderNo") String orderNo);
}
