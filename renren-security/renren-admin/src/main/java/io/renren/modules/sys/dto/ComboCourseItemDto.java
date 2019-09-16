package io.renren.modules.sys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/9/16 11:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComboCourseItemDto {
    private Long id;
    /**
     * 课程套餐ID
     */
    private Long comboCourseId;
    /**
     * 套餐课程类别ID
     */
    private Long typeId;
    /**
     * 类别名称
     */
    private String title;
    /**
     * 包含课程次数
     */
    private Integer num;

}
