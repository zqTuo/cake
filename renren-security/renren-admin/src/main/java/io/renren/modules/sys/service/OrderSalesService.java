package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.OrderSalesEntity;

import java.util.Map;

/**
 * 财务流水表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-28 21:42:09
 */
public interface OrderSalesService extends IService<OrderSalesEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

