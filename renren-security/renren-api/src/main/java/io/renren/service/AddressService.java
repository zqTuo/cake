package io.renren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.entity.AddressEntity;


/**
 * 收货地址表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 12:08:37
 */
public interface AddressService extends IService<AddressEntity> {
    void cleanDefault(long userId);
}

