package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.ShopOrderDao;
import io.renren.modules.sys.entity.ShopOrderEntity;
import io.renren.modules.sys.service.ShopOrderService;


@Service("shopOrderService")
public class ShopOrderServiceImpl extends ServiceImpl<ShopOrderDao, ShopOrderEntity> implements ShopOrderService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ShopOrderEntity> page = this.page(
                new Query<ShopOrderEntity>().getPage(params),
                new QueryWrapper<ShopOrderEntity>()
        );

        return new PageUtils(page);
    }

}
