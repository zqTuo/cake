package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.SetCourseDao;
import io.renren.modules.sys.entity.SetCourseEntity;
import io.renren.modules.sys.service.SetCourseService;


@Service("setCourseService")
public class SetCourseServiceImpl extends ServiceImpl<SetCourseDao, SetCourseEntity> implements SetCourseService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SetCourseEntity> page = this.page(
                new Query<SetCourseEntity>().getPage(params),
                new QueryWrapper<SetCourseEntity>()
        );

        return new PageUtils(page);
    }

}
