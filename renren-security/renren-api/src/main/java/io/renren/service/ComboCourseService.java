package io.renren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.dto.ComboCourseDto;
import io.renren.entity.ComboCourseEntity;


/**
 * 课程套餐表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-12 15:49:25
 */
public interface ComboCourseService extends IService<ComboCourseEntity> {

    ComboCourseDto findById(long setCourseId);
}

