package io.renren.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.dao.BannerDao;
import io.renren.entity.BannerEntity;
import io.renren.service.BannerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("bannerService")
public class BannerServiceImpl extends ServiceImpl<BannerDao, BannerEntity> implements BannerService {

    @Override
    public List<BannerEntity> getIndexBanner() {
        return baseMapper.selectList(new QueryWrapper<BannerEntity>().eq("bannerType",0));
    }
}
