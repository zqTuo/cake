package io.renren.dao;

import io.renren.entity.AddressEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 收货地址表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 12:08:37
 */
@Mapper
public interface AddressDao extends BaseMapper<AddressEntity> {

    void cleanDefault(@Param("userid") long userId);
}
