package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.SendFreightDao;
import io.renren.modules.sys.entity.SendFreightEntity;
import io.renren.modules.sys.service.SendFreightService;


@Service("sendFreightService")
public class SendFreightServiceImpl extends ServiceImpl<SendFreightDao, SendFreightEntity> implements SendFreightService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SendFreightEntity> page = this.page(
                new Query<SendFreightEntity>().getPage(params),
                new QueryWrapper<SendFreightEntity>()
        );

        return new PageUtils(page);
    }

}
