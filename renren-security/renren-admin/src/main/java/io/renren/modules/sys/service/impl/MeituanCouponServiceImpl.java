package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.MeituanCouponDao;
import io.renren.modules.sys.entity.MeituanCouponEntity;
import io.renren.modules.sys.service.MeituanCouponService;


@Service("meituanCouponService")
public class MeituanCouponServiceImpl extends ServiceImpl<MeituanCouponDao, MeituanCouponEntity> implements MeituanCouponService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MeituanCouponEntity> page = this.page(
                new Query<MeituanCouponEntity>().getPage(params),
                new QueryWrapper<MeituanCouponEntity>()
        );

        return new PageUtils(page);
    }

}
