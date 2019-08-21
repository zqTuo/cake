package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.BannerEntity;

import java.util.Map;

/**
 * banner表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 18:11:13
 */
public interface BannerService extends IService<BannerEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

