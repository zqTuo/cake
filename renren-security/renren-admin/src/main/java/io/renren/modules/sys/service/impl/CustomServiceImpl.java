package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.CustomDao;
import io.renren.modules.sys.entity.CustomEntity;
import io.renren.modules.sys.service.CustomService;


@Service("customService")
public class CustomServiceImpl extends ServiceImpl<CustomDao, CustomEntity> implements CustomService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CustomEntity> page = this.page(
                new Query<CustomEntity>().getPage(params),
                new QueryWrapper<CustomEntity>()
        );

        return new PageUtils(page);
    }

}
