package io.renren.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.dao.WxuserDao;
import io.renren.dto.UserDto;
import io.renren.entity.WxuserEntity;
import io.renren.service.WxuserService;
import org.springframework.stereotype.Service;



@Service("wxuserService")
public class WxuserServiceImpl extends ServiceImpl<WxuserDao, WxuserEntity> implements WxuserService {

    @Override
    public WxuserEntity selectUserByOpenId(String openid) {
        return baseMapper.selectOne(new QueryWrapper<WxuserEntity>().eq("userOpenid",openid));
    }

    @Override
    public UserDto getUserInfo(long id) {
        return baseMapper.getUserInfo(id);
    }
}
