package io.renren.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.dao.MeituanItemDao;
import io.renren.entity.MeituanItemEntity;
import io.renren.service.MeituanItemService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service("meituanItemService")
public class MeituanItemServiceImpl extends ServiceImpl<MeituanItemDao, MeituanItemEntity> implements MeituanItemService {


    @Override
    public BigDecimal countPrice(long meituanId) {
        return baseMapper.countPrice(meituanId);
    }
}
