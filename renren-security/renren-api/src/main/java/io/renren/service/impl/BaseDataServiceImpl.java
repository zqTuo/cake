package io.renren.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.dao.BaseDataDao;
import io.renren.entity.BaseDataEntity;
import io.renren.service.BaseDataService;
import org.springframework.stereotype.Service;



@Service("baseDataService")
public class BaseDataServiceImpl extends ServiceImpl<BaseDataDao, BaseDataEntity> implements BaseDataService {

    @Override
    public BaseDataEntity getBySourceType(int sourceType) {
        return baseMapper.selectOne(new QueryWrapper<BaseDataEntity>().eq("sourceType",sourceType));
    }
}
