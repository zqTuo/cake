package io.renren.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.LocationUtils;
import io.renren.dao.SendFreightDao;
import io.renren.entity.SendFreightEntity;
import io.renren.entity.ShopEntity;
import io.renren.service.SendFreightService;
import io.renren.service.ShopService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service("sendFreightService")
public class SendFreightServiceImpl extends ServiceImpl<SendFreightDao, SendFreightEntity> implements SendFreightService {
    @Resource
    private ShopService shopService;
    @Resource
    private SendFreightDao sendFreightDao;
    @Resource
    private LocationUtils locationUtils;

    @Override
    public float getDistance(long shopId,String city,String addr) {
        ShopEntity shopEntity = shopService.getById(shopId);
        String addrName = "广东省" + city + addr;
        String origin = shopEntity.getShopLatitude() + "," + shopEntity.getShopLongitude();
        return locationUtils.getDistanceByBaiduMap(addrName,origin);
    }

    @Override
    public BigDecimal getFreight(long shopId, String city, String address) {
        BigDecimal freight = new BigDecimal("0");

        //计算距离
        float distance = getDistance(shopId,city,address);

        //按最大距离倒叙查询
        List<SendFreightEntity> freightEntities = baseMapper.selectList(
                new QueryWrapper<SendFreightEntity>().orderByDesc("max_distance"));

        //从大到小比较
        for (int i = 0; i < freightEntities.size(); i++) {
            SendFreightEntity sendFreightEntity = freightEntities.get(i);

            if(distance >= sendFreightEntity.getMaxDistance()){
                freight = sendFreightEntity.getFreight();
                break;
            }

            if((i + 1) == freightEntities.size() && freight.compareTo(new BigDecimal("0")) == 0){//没有 判断到运费配比
                // 说明距离比最小的运费距离还小
                freight = sendFreightEntity.getFreight();
                break;
            }
        }
        return freight;
    }
}
