package io.renren.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.entity.CourseEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 蛋糕课程表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-09 18:28:51
 */
@Mapper
public interface CourseDao extends BaseMapper<CourseEntity> {

}
