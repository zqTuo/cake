package io.renren.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.dto.CourseDto;
import io.renren.entity.CourseEntity;
import io.renren.form.PageForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 蛋糕课程表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-09 18:28:51
 */
@Mapper
public interface CourseDao extends BaseMapper<CourseEntity> {

    List<CourseEntity> getDataByPage(PageForm form);

}
