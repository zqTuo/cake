package io.renren.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.dao.ComboTypeDao;
import io.renren.entity.ComboTypeEntity;
import io.renren.service.ComboTypeService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("setTypeService")
public class ComboTypeServiceImpl extends ServiceImpl<ComboTypeDao, ComboTypeEntity> implements ComboTypeService {


}
