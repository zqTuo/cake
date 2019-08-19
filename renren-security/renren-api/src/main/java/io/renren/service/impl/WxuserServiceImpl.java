package io.renren.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.dao.WxuserDao;
import io.renren.entity.WxuserEntity;
import io.renren.service.WxuserService;
import org.springframework.stereotype.Service;



@Service("wxuserService")
public class WxuserServiceImpl extends ServiceImpl<WxuserDao, WxuserEntity> implements WxuserService {

}
