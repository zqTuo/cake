package io.renren.modules.sys.service.impl;

import io.renren.modules.sys.dto.CourseMenuDto;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.CourseMenuDao;
import io.renren.modules.sys.entity.CourseMenuEntity;
import io.renren.modules.sys.service.CourseMenuService;


@Service("courseMenuService")
public class CourseMenuServiceImpl extends ServiceImpl<CourseMenuDao, CourseMenuEntity> implements CourseMenuService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CourseMenuDto> page = baseMapper.findByPage(new Query<CourseMenuDto>().getPage(params),params);

        return new PageUtils(page);
    }

}
