package io.renren.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.dao.CourseDao;
import io.renren.entity.CourseEntity;
import io.renren.form.PageForm;
import io.renren.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("courseService")
public class CourseServiceImpl extends ServiceImpl<CourseDao, CourseEntity> implements CourseService {


    @Override
    public List<CourseEntity> getDataByPage(PageForm form) {
        return baseMapper.getDataByPage(form);
    }
}
