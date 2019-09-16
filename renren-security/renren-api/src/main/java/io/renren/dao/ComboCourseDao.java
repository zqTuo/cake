package io.renren.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.dto.ComboCourseDto;
import io.renren.entity.ComboCourseEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 课程套餐表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-12 15:49:25
 */
@Mapper
public interface ComboCourseDao extends BaseMapper<ComboCourseEntity> {

    ComboCourseDto findById(@Param("id") long setCourseId);
}
