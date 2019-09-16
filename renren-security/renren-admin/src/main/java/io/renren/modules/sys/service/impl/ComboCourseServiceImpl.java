package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.ComboCourseDao;
import io.renren.modules.sys.entity.ComboCourseEntity;
import io.renren.modules.sys.service.ComboCourseService;


@Service("setCourseService")
public class ComboCourseServiceImpl extends ServiceImpl<ComboCourseDao, ComboCourseEntity> implements ComboCourseService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ComboCourseEntity> page = this.page(
                new Query<ComboCourseEntity>().getPage(params),
                new QueryWrapper<ComboCourseEntity>()
        );

        return new PageUtils(page);
    }

}
