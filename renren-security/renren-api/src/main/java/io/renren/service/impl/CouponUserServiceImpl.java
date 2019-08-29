package io.renren.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.JodaTimeUtil;
import io.renren.dao.CouponUserDao;
import io.renren.dto.CouponDto;
import io.renren.entity.CouponUserEntity;
import io.renren.service.CouponUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Service("couponUserService")
public class CouponUserServiceImpl extends ServiceImpl<CouponUserDao, CouponUserEntity> implements CouponUserService {
    private static Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Override
    public List<CouponDto> getMyCoupon(long userId, int sourceType) {
        return baseMapper.getMyCoupon(userId,sourceType);
    }

    @Override
    public CouponDto findById(long id) {
        return baseMapper.findById(id);
    }

    @Override
    public BigDecimal calculate(long couponUserId, BigDecimal totalPrice) {
        BigDecimal discount = new BigDecimal("0");

        CouponDto couponDto = findById(couponUserId);
        if(couponDto == null){
            return discount;
        }

        if (couponDto.getEndTime() != null){
            // 判断优惠券是否过期
            if(JodaTimeUtil.isExpired(new Date(),couponDto.getEndTime())){
                log.info("优惠券已过期");
                return discount;
            }
        }

        if(totalPrice.compareTo(couponDto.getCouponPrice()) < 0){
            log.info("商品累计金额：" + totalPrice + ",未达到触发价格：" + couponDto.getCouponPrice());
            return discount;
        }

        return couponDto.getPrice();
    }
}
