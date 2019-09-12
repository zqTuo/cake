package io.renren.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.entity.SetCourseEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 课程套餐表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-12 15:49:25
 */
@Mapper
public interface SetCourseDao extends BaseMapper<SetCourseEntity> {
	
}
