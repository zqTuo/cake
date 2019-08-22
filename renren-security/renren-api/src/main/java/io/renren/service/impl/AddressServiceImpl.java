package io.renren.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.dao.AddressDao;
import io.renren.entity.AddressEntity;
import io.renren.service.AddressService;


@Service("addressService")
public class AddressServiceImpl extends ServiceImpl<AddressDao, AddressEntity> implements AddressService {

    @Override
    public void cleanDefault(long userId) {
        baseMapper.cleanDefault(userId);
    }
}
