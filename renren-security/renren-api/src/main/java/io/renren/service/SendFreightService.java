package io.renren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.entity.SendFreightEntity;
import io.renren.form.SendTimeForm;

import java.math.BigDecimal;


/**
 * 运费表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-21 16:22:06
 */
public interface SendFreightService extends IService<SendFreightEntity> {

    float getDistance(long shopId,String city,String addr);

    BigDecimal getFreight(long shopId, String city, String address);
}

