package io.renren.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.dao.OrderSalesDao;
import io.renren.entity.OrderSalesEntity;
import io.renren.service.OrderSalesService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("orderSalesService")
public class OrderSalesServiceImpl extends ServiceImpl<OrderSalesDao, OrderSalesEntity> implements OrderSalesService {


}
