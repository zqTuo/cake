package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.CouponUserDao;
import io.renren.modules.sys.entity.CouponUserEntity;
import io.renren.modules.sys.service.CouponUserService;


@Service("couponUserService")
public class CouponUserServiceImpl extends ServiceImpl<CouponUserDao, CouponUserEntity> implements CouponUserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CouponUserEntity> page = this.page(
                new Query<CouponUserEntity>().getPage(params),
                new QueryWrapper<CouponUserEntity>()
        );

        return new PageUtils(page);
    }

}
