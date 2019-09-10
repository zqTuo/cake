package io.renren.modules.sys.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.renren.modules.sys.dto.CourseDto;
import io.renren.modules.sys.entity.CourseEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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

    IPage<CourseDto> list(IPage<CourseDto> page);
}
