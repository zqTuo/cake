package io.renren.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.dao.CouponDao;
import io.renren.entity.CouponEntity;
import io.renren.service.CouponService;
import org.springframework.stereotype.Service;


@Service("couponService")
public class CouponServiceImpl extends ServiceImpl<CouponDao, CouponEntity> implements CouponService {


}
