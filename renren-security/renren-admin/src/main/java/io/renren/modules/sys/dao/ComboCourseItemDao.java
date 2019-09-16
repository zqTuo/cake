package io.renren.modules.sys.dao;

import io.renren.modules.sys.dto.ComboCourseItemDto;
import io.renren.modules.sys.entity.ComboCourseItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 套餐课程表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-12 15:49:25
 */
@Mapper
public interface ComboCourseItemDao extends BaseMapper<ComboCourseItemEntity> {

    List<ComboCourseItemDto> getList(@Param("id") Long id);

    void removeByCourseIds(@Param("idArr")Long[] ids);
}
