package io.renren.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.dao.CustomDao;
import io.renren.entity.CustomEntity;
import io.renren.service.CustomService;
import org.springframework.stereotype.Service;



@Service("customService")
public class CustomServiceImpl extends ServiceImpl<CustomDao, CustomEntity> implements CustomService {

}
