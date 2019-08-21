package io.renren.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.dao.ShoppingCartDao;
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
}
