package io.renren.modules.sys.service.impl;

import io.renren.modules.sys.dto.CourseDto;
import io.renren.modules.sys.dto.ProductDto;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.CourseDao;
import io.renren.modules.sys.entity.CourseEntity;
import io.renren.modules.sys.service.CourseService;


@Service("courseService")
public class CourseServiceImpl extends ServiceImpl<CourseDao, CourseEntity> implements CourseService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CourseDto> page = baseMapper.list(new Query<CourseDto>().getPage(params));

        return new PageUtils(page);
    }

}
