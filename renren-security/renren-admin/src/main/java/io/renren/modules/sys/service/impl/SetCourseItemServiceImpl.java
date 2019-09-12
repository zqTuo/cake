package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.SetCourseItemDao;
import io.renren.modules.sys.entity.SetCourseItemEntity;
import io.renren.modules.sys.service.SetCourseItemService;


@Service("setCourseItemService")
public class SetCourseItemServiceImpl extends ServiceImpl<SetCourseItemDao, SetCourseItemEntity> implements SetCourseItemService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SetCourseItemEntity> page = this.page(
                new Query<SetCourseItemEntity>().getPage(params),
                new QueryWrapper<SetCourseItemEntity>()
        );

        return new PageUtils(page);
    }

}
