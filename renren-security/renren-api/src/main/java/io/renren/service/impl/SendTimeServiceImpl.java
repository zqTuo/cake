package io.renren.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.dao.SendTimeDao;
import io.renren.entity.SendTimeEntity;
import io.renren.service.SendTimeService;

import java.util.List;


@Service("sendTimeService")
public class SendTimeServiceImpl extends ServiceImpl<SendTimeDao, SendTimeEntity> implements SendTimeService {

    @Override
    public void resolveTimeList(List<SendTimeEntity> sendTimeEntityList) {

    }
}
