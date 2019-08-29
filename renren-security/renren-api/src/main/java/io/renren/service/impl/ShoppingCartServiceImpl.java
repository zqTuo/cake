package io.renren.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.dao.ShoppingCartDao;
import io.renren.dto.PayProDto;
import io.renren.dto.ShoppingCartDto;
import io.renren.entity.ShoppingCartEntity;
import io.renren.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("shoppingCartService")
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartDao, ShoppingCartEntity> implements ShoppingCartService {


    @Override
    public List<ShoppingCartEntity> getMyData(long userId) {
        return baseMapper.getMyData(userId);
    }

    @Override
    public ShoppingCartDto countMyData(long userId) {
        return baseMapper.countMyData(userId);
    }

    @Override
    public List<PayProDto> getBuyData(long userId) {
        return baseMapper.getBuyData(userId);
    }
}
