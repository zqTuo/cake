package io.renren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.entity.BaseDataEntity;


/**
 * 系统配置表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 12:26:29
 */
public interface BaseDataService extends IService<BaseDataEntity> {
    BaseDataEntity getBySourceType(int sourceType);
}

