package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.WxuserDao;
import io.renren.modules.sys.entity.WxuserEntity;
import io.renren.modules.sys.service.WxuserService;


@Service("wxuserService")
public class WxuserServiceImpl extends ServiceImpl<WxuserDao, WxuserEntity> implements WxuserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WxuserEntity> page = this.page(
                new Query<WxuserEntity>().getPage(params),
                new QueryWrapper<WxuserEntity>()
        );

        return new PageUtils(page);
    }

}
