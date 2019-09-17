package io.renren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.dto.CourseMenuDto;
import io.renren.entity.CourseMenuEntity;

import java.util.List;


/**
 * 课程菜单表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-17 09:35:40
 */
public interface CourseMenuService extends IService<CourseMenuEntity> {

    List<CourseMenuDto> getMenu(String selectedDate);
}

