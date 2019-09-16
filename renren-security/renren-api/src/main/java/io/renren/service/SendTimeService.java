package io.renren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.dto.SendTimeDto;
import io.renren.entity.SendTimeEntity;

import java.text.ParseException;
import java.util.List;

/**
 * 配送时间表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 21:10:39
 */
public interface SendTimeService extends IService<SendTimeEntity> {

    void resolveTimeList(List<SendTimeDto> sendTimeList, float distance, String selectedDate,int sendType) throws ParseException;

    List<SendTimeDto> getData(int type);
}

