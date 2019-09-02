package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.MeituanItemDao;
import io.renren.modules.sys.entity.MeituanItemEntity;
import io.renren.modules.sys.service.MeituanItemService;


@Service("meituanItemService")
public class MeituanItemServiceImpl extends ServiceImpl<MeituanItemDao, MeituanItemEntity> implements MeituanItemService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MeituanItemEntity> page = this.page(
                new Query<MeituanItemEntity>().getPage(params),
                new QueryWrapper<MeituanItemEntity>()
        );

        return new PageUtils(page);
    }

}
