package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.HotRelationEntity;

import java.util.Map;

/**
 * 热销栏目与商品关联表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 17:12:34
 */
public interface HotRelationService extends IService<HotRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

