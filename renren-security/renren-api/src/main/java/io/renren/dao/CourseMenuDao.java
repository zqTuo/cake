package io.renren.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.dto.CourseMenuDto;
import io.renren.entity.CourseMenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 课程菜单表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-17 09:35:40
 */
@Mapper
public interface CourseMenuDao extends BaseMapper<CourseMenuEntity> {

    List<CourseMenuDto> getMenu(@Param("date") String selectedDate);
}
