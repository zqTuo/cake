package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.ShopOrderItemDao;
import io.renren.modules.sys.entity.ShopOrderItemEntity;
import io.renren.modules.sys.service.ShopOrderItemService;


@Service("shopOrderItemService")
public class ShopOrderItemServiceImpl extends ServiceImpl<ShopOrderItemDao, ShopOrderItemEntity> implements ShopOrderItemService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ShopOrderItemEntity> page = this.page(
                new Query<ShopOrderItemEntity>().getPage(params),
                new QueryWrapper<ShopOrderItemEntity>()
        );

        return new PageUtils(page);
    }

}
