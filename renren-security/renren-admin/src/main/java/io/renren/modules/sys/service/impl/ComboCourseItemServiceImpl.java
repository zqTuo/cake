package io.renren.modules.sys.service.impl;

import io.renren.modules.sys.dto.ComboCourseItemDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.ComboCourseItemDao;
import io.renren.modules.sys.entity.ComboCourseItemEntity;
import io.renren.modules.sys.service.ComboCourseItemService;


@Service("setCourseItemService")
public class ComboCourseItemServiceImpl extends ServiceImpl<ComboCourseItemDao, ComboCourseItemEntity> implements ComboCourseItemService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ComboCourseItemEntity> page = this.page(
                new Query<ComboCourseItemEntity>().getPage(params),
                new QueryWrapper<ComboCourseItemEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<ComboCourseItemDto> getList(Long id) {
        return baseMapper.getList(id);
    }

    @Override
    public void removeByCourseIds(Long[] ids) {
        baseMapper.removeByCourseIds(ids);
    }

}
