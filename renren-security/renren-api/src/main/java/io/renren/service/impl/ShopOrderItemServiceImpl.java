package io.renren.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.dao.ShopOrderItemDao;
import io.renren.entity.ShopOrderItemEntity;
import io.renren.service.ShopOrderItemService;
import org.springframework.stereotype.Service;


@Service("shopOrderItemService")
public class ShopOrderItemServiceImpl extends ServiceImpl<ShopOrderItemDao, ShopOrderItemEntity> implements ShopOrderItemService {

}
