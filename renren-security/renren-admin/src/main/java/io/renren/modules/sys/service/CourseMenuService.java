package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.CourseMenuEntity;

import java.util.Map;

/**
 * 课程菜单表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-17 09:35:40
 */
public interface CourseMenuService extends IService<CourseMenuEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

