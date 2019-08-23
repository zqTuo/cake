package io.renren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.entity.SendTimeEntity;

import java.util.List;

/**
 * 配送时间表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 21:10:39
 */
public interface SendTimeService extends IService<SendTimeEntity> {

    void resolveTimeList(List<SendTimeEntity> sendTimeEntityList);
}

