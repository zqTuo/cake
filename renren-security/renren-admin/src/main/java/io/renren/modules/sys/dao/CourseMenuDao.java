package io.renren.modules.sys.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.renren.modules.sys.dto.CourseMenuDto;
import io.renren.modules.sys.entity.CourseMenuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 课程菜单表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-17 09:35:40
 */
@Mapper
public interface CourseMenuDao extends BaseMapper<CourseMenuEntity> {

    IPage<CourseMenuDto> findByPage(IPage<CourseMenuDto> page, @Param("map") Map<String, Object> params);
}
