package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.WxKeyEntity;

import java.util.Map;

/**
 * 微信关键字表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-03 17:29:29
 */
public interface WxKeyService extends IService<WxKeyEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

