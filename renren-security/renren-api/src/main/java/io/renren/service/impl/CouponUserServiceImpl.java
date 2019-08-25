package io.renren.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.dao.CouponUserDao;
import io.renren.dto.CouponDto;
import io.renren.entity.CouponUserEntity;
import io.renren.service.CouponUserService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("couponUserService")
public class CouponUserServiceImpl extends ServiceImpl<CouponUserDao, CouponUserEntity> implements CouponUserService {


    @Override
    public List<CouponDto> getMyCoupon(long userId, int sourceType) {
        return baseMapper.getMyCoupon(userId,sourceType);
    }
}
