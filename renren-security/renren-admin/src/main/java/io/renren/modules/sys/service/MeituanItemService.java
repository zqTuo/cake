package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.MeituanItemEntity;

import java.util.Map;

/**
 * 美团券包含商品信息表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-01 14:26:01
 */
public interface MeituanItemService extends IService<MeituanItemEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

