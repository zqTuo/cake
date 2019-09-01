package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.HotColumnEntity;

import java.util.Map;

/**
 * 首页热销栏目表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 17:12:34
 */
public interface HotColumnService extends IService<HotColumnEntity> {

    PageUtils queryPage(Map<String, Object> params);

}

