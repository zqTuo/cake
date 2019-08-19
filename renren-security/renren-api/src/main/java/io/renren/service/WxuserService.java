package io.renren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.dto.UserDto;
import io.renren.entity.WxuserEntity;

/**
 * 用户表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-19 16:35:41
 */
public interface WxuserService extends IService<WxuserEntity> {
    WxuserEntity selectUserByOpenId(String openid);

    UserDto getUserInfo(long id);
}

