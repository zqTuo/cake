package io.renren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.entity.MeituanItemEntity;

import java.math.BigDecimal;

/**
 * 美团券包含商品信息表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-01 14:26:01
 */
public interface MeituanItemService extends IService<MeituanItemEntity> {

    BigDecimal countPrice(long meituanId);
}

