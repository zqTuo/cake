package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SendFreightEntity;

import java.util.Map;

/**
 * 运费表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-19 16:35:41
 */
public interface SendFreightService extends IService<SendFreightEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

