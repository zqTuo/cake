package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.BaseDataDao;
import io.renren.modules.sys.entity.BaseDataEntity;
import io.renren.modules.sys.service.BaseDataService;


@Service("baseDataService")
public class BaseDataServiceImpl extends ServiceImpl<BaseDataDao, BaseDataEntity> implements BaseDataService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BaseDataEntity> page = this.page(
                new Query<BaseDataEntity>().getPage(params),
                new QueryWrapper<BaseDataEntity>()
        );

        return new PageUtils(page);
    }

}
