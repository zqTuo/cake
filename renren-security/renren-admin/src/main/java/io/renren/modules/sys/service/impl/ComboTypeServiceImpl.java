package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.ComboTypeDao;
import io.renren.modules.sys.entity.ComboTypeEntity;
import io.renren.modules.sys.service.ComboTypeService;


@Service("setTypeService")
public class ComboTypeServiceImpl extends ServiceImpl<ComboTypeDao, ComboTypeEntity> implements ComboTypeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ComboTypeEntity> page = this.page(
                new Query<ComboTypeEntity>().getPage(params),
                new QueryWrapper<ComboTypeEntity>()
        );

        return new PageUtils(page);
    }

}
