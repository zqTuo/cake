package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.ShopOrderDao;
import io.renren.modules.sys.dto.ExcelOrderDto;
import io.renren.modules.sys.dto.OrderDto;
import io.renren.modules.sys.dto.SmallOrderDto;
import io.renren.modules.sys.entity.ShopOrderEntity;
import io.renren.modules.sys.service.ShopOrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("shopOrderService")
public class ShopOrderServiceImpl extends ServiceImpl<ShopOrderDao, ShopOrderEntity> implements ShopOrderService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderDto> page = baseMapper.findByPage(new Query<OrderDto>().getPage(params),params);

        return new PageUtils(page);
    }

    @Override
    public List<ExcelOrderDto> getData(Map<String, Object> map) {
        return baseMapper.getData(map);
    }

    @Override
    public List<SmallOrderDto> getSmallData(Map<String, Object> map) {
        return baseMapper.getSmallData(map);
    }

}
