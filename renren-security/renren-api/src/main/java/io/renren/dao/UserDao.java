/**
 * Copyright (c) 2016-2019 炫酷游开源 All rights reserved.
 *
 * http://www.xkygame.com
 *
 * 版权所有，侵权必究！
 */

package io.renren.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {

}