package io.renren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.dto.RemandCourseDto;
import io.renren.entity.ComboUserEntity;

import java.util.List;


/**
 * 用户剩余套餐次数表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-17 15:50:04
 */
public interface ComboUserService extends IService<ComboUserEntity> {

    List<RemandCourseDto> findByUserId(long userId);

    int findMyRemaind(long userId, long comboTypeId);
}

