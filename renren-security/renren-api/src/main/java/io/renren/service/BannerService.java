package io.renren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.entity.BannerEntity;

import java.util.List;

/**
 * banner表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 16:22:06
 */
public interface BannerService extends IService<BannerEntity> {

    List<BannerEntity> getIndexBanner();
}

