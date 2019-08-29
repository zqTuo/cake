package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.OrderSalesDao;
import io.renren.modules.sys.entity.OrderSalesEntity;
import io.renren.modules.sys.service.OrderSalesService;


@Service("orderSalesService")
public class OrderSalesServiceImpl extends ServiceImpl<OrderSalesDao, OrderSalesEntity> implements OrderSalesService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderSalesEntity> page = this.page(
                new Query<OrderSalesEntity>().getPage(params),
                new QueryWrapper<OrderSalesEntity>()
        );

        return new PageUtils(page);
    }

}
