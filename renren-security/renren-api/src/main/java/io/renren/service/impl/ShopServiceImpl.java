package io.renren.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.dao.ShopDao;
import io.renren.dto.ShopDto;
import io.renren.entity.ShopEntity;
import io.renren.service.ShopService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("shopService")
public class ShopServiceImpl extends ServiceImpl<ShopDao, ShopEntity> implements ShopService {

    @Override
    public List<ShopDto> findAll() {
        return baseMapper.findAll();
    }
}
