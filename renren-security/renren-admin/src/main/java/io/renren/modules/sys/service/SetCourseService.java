package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SetCourseEntity;

import java.util.Map;

/**
 * 课程套餐表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-12 15:49:25
 */
public interface SetCourseService extends IService<SetCourseEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

