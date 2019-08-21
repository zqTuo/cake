package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.HotRelationDao;
import io.renren.modules.sys.entity.HotRelationEntity;
import io.renren.modules.sys.service.HotRelationService;


@Service("hotRelationService")
public class HotRelationServiceImpl extends ServiceImpl<HotRelationDao, HotRelationEntity> implements HotRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HotRelationEntity> page = this.page(
                new Query<HotRelationEntity>().getPage(params),
                new QueryWrapper<HotRelationEntity>()
        );

        return new PageUtils(page);
    }

}
