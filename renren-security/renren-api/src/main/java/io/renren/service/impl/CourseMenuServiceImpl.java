package io.renren.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.dao.CourseMenuDao;
import io.renren.dto.CourseMenuDto;
import io.renren.entity.CourseMenuEntity;
import io.renren.service.CourseMenuService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("courseMenuService")
public class CourseMenuServiceImpl extends ServiceImpl<CourseMenuDao, CourseMenuEntity> implements CourseMenuService {


    @Override
    public List<CourseMenuDto> getMenu(String selectedDate) {
        return baseMapper.getMenu(selectedDate);
    }
}
