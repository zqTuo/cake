package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.ShopOrderEntity;

import java.util.Map;

/**
 * 订单表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 16:22:06
 */
public interface ShopOrderService extends IService<ShopOrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

