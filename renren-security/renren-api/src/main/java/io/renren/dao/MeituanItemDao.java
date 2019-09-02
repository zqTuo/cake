package io.renren.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.entity.MeituanItemEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * 美团券包含商品信息表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-01 14:26:01
 */
@Mapper
public interface MeituanItemDao extends BaseMapper<MeituanItemEntity> {

    BigDecimal countPrice(@Param("meituanId") long meituanId);
}
