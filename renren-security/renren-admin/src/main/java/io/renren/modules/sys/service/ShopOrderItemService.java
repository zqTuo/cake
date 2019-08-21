package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.ShopOrderItemEntity;

import java.util.Map;

/**
 * 订单详情表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 16:22:06
 */
public interface ShopOrderItemService extends IService<ShopOrderItemEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

