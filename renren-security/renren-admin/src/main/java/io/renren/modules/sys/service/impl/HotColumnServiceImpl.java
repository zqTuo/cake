package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.HotColumnDao;
import io.renren.modules.sys.entity.HotColumnEntity;
import io.renren.modules.sys.service.HotColumnService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("hotColumnService")
public class HotColumnServiceImpl extends ServiceImpl<HotColumnDao, HotColumnEntity> implements HotColumnService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HotColumnEntity> page = this.page(
                new Query<HotColumnEntity>().getPage(params),
                new QueryWrapper<HotColumnEntity>()
        );

        return new PageUtils(page);
    }


}
