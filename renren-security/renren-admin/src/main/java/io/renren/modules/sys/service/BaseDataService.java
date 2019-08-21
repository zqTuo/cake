package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.BaseDataEntity;

import java.util.Map;

/**
 * 系统配置表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 16:22:06
 */
public interface BaseDataService extends IService<BaseDataEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

