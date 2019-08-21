package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SendTimeEntity;

import java.util.Map;

/**
 * 配送时间表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 16:22:06
 */
public interface SendTimeService extends IService<SendTimeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

