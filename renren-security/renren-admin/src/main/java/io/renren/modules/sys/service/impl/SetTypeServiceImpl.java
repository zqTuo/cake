package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.SetTypeDao;
import io.renren.modules.sys.entity.SetTypeEntity;
import io.renren.modules.sys.service.SetTypeService;


@Service("setTypeService")
public class SetTypeServiceImpl extends ServiceImpl<SetTypeDao, SetTypeEntity> implements SetTypeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SetTypeEntity> page = this.page(
                new Query<SetTypeEntity>().getPage(params),
                new QueryWrapper<SetTypeEntity>()
        );

        return new PageUtils(page);
    }

}
