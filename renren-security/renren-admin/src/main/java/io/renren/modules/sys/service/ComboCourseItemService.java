package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.dto.ComboCourseItemDto;
import io.renren.modules.sys.entity.ComboCourseItemEntity;

import java.util.List;
import java.util.Map;

/**
 * 套餐课程表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-12 15:49:25
 */
public interface ComboCourseItemService extends IService<ComboCourseItemEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<ComboCourseItemDto> getList(Long id);

    void removeByCourseIds(Long[] ids);
}

