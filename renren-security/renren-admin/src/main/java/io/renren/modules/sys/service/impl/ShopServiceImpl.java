package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.ShopDao;
import io.renren.modules.sys.entity.ShopEntity;
import io.renren.modules.sys.service.ShopService;


@Service("shopService")
public class ShopServiceImpl extends ServiceImpl<ShopDao, ShopEntity> implements ShopService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ShopEntity> page = this.page(
                new Query<ShopEntity>().getPage(params),
                new QueryWrapper<ShopEntity>()
        );

        return new PageUtils(page);
    }

}
