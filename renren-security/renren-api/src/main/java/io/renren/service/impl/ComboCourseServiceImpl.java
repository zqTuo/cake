package io.renren.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.dao.ComboCourseDao;
import io.renren.dto.ComboCourseDto;
import io.renren.entity.ComboCourseEntity;
import io.renren.service.ComboCourseService;
import org.springframework.stereotype.Service;



@Service("comboCourseService")
public class ComboCourseServiceImpl extends ServiceImpl<ComboCourseDao, ComboCourseEntity> implements ComboCourseService {


    @Override
    public ComboCourseDto findById(long setCourseId) {
        return baseMapper.findById(setCourseId);
    }
}
