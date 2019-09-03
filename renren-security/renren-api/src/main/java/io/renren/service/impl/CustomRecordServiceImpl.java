package io.renren.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.dao.CustomRecordDao;
import io.renren.entity.CustomRecordEntity;
import io.renren.service.CustomRecordService;
import org.springframework.stereotype.Service;



@Service("customRecordService")
public class CustomRecordServiceImpl extends ServiceImpl<CustomRecordDao, CustomRecordEntity> implements CustomRecordService {

}
