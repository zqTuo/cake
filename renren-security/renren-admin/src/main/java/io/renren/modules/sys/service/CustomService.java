package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.CustomEntity;

import java.util.Map;

/**
 * 微信客服表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-03 11:34:56
 */
public interface CustomService extends IService<CustomEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

