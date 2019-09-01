package io.renren.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.dao.MeituanCouponDao;
import io.renren.entity.MeituanCouponEntity;
import io.renren.service.MeituanCouponService;
import org.springframework.stereotype.Service;


@Service("meituanCouponService")
public class MeituanCouponServiceImpl extends ServiceImpl<MeituanCouponDao, MeituanCouponEntity> implements MeituanCouponService {


}
