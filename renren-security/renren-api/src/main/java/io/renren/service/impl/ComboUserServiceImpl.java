package io.renren.service.impl;

import io.renren.dto.RemandCourseDto;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.dao.ComboUserDao;
import io.renren.entity.ComboUserEntity;
import io.renren.service.ComboUserService;

import java.util.List;


@Service("comboUserService")
public class ComboUserServiceImpl extends ServiceImpl<ComboUserDao, ComboUserEntity> implements ComboUserService {


    @Override
    public List<RemandCourseDto> findByUserId(long userId) {
        return baseMapper.findByUserId(userId);
    }

    @Override
    public int findMyRemaind(long userId, long comboTypeId) {
        return baseMapper.findMyRemaind(userId,comboTypeId);
    }
}
