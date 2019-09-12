package io.renren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.entity.CourseEntity;
import io.renren.form.PageForm;

import java.util.List;


/**
 * 蛋糕课程表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-09 18:28:51
 */
public interface CourseService extends IService<CourseEntity> {

    List<CourseEntity> getDataByPage(PageForm form);
}

