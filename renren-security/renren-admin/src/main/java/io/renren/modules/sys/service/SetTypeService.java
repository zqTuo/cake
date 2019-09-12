package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SetTypeEntity;

import java.util.Map;

/**
 * 套餐课程类别表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-12 15:49:25
 */
public interface SetTypeService extends IService<SetTypeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

