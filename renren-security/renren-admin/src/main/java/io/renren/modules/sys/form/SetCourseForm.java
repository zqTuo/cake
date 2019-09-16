package io.renren.modules.sys.form;

import io.renren.modules.sys.entity.ComboCourseEntity;
import io.renren.modules.sys.entity.ComboCourseItemEntity;
import lombok.Data;

import java.util.List;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/9/16 9:45
 */
@Data
public class SetCourseForm {
    private ComboCourseEntity setCourse;
    private List<ComboCourseItemEntity> detailList;
}
