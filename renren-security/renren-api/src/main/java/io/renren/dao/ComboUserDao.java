package io.renren.dao;

import io.renren.dto.RemandCourseDto;
import io.renren.entity.ComboUserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户剩余套餐次数表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-17 15:50:04
 */
@Mapper
public interface ComboUserDao extends BaseMapper<ComboUserEntity> {

    List<RemandCourseDto> findByUserId(@Param("userid") long userId);

    int findMyRemaind(@Param("userid") long userId, @Param("typeid") long comboTypeId);

    void reduceNum(@Param("id")long comboUserId);
}
