package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.ShopEntity;

import java.util.Map;

/**
 * 门店表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 16:22:06
 */
public interface ShopService extends IService<ShopEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

