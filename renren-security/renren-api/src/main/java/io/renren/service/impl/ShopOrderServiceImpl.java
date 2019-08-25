package io.renren.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.dao.ShopOrderDao;
import io.renren.entity.ShopOrderEntity;
import io.renren.service.ShopOrderService;
import org.springframework.stereotype.Service;

@Service("shopOrderService")
public class ShopOrderServiceImpl extends ServiceImpl<ShopOrderDao, ShopOrderEntity> implements ShopOrderService {


}
