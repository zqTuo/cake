package io.renren.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.dao.CourseDao;
import io.renren.entity.CourseEntity;
import io.renren.service.CourseService;
import org.springframework.stereotype.Service;



@Service("courseService")
public class CourseServiceImpl extends ServiceImpl<CourseDao, CourseEntity> implements CourseService {

    
}
