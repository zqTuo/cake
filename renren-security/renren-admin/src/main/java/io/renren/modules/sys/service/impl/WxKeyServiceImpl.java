package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.WxKeyDao;
import io.renren.modules.sys.entity.WxKeyEntity;
import io.renren.modules.sys.service.WxKeyService;


@Service("wxKeyService")
public class WxKeyServiceImpl extends ServiceImpl<WxKeyDao, WxKeyEntity> implements WxKeyService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WxKeyEntity> page = this.page(
                new Query<WxKeyEntity>().getPage(params),
                new QueryWrapper<WxKeyEntity>()
        );

        return new PageUtils(page);
    }

}
