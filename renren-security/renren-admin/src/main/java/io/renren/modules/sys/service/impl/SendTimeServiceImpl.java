package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.SendTimeDao;
import io.renren.modules.sys.entity.SendTimeEntity;
import io.renren.modules.sys.service.SendTimeService;


@Service("sendTimeService")
public class SendTimeServiceImpl extends ServiceImpl<SendTimeDao, SendTimeEntity> implements SendTimeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SendTimeEntity> page = this.page(
                new Query<SendTimeEntity>().getPage(params),
                new QueryWrapper<SendTimeEntity>()
        );

        return new PageUtils(page);
    }

}
