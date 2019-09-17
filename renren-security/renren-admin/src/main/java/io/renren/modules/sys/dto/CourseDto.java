package io.renren.modules.sys.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/9/9 18:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {
    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 课程名称
     */
    private String title;
    /**
     * 主图图片
     */
    private String courseImg;
    /**
     * 售价
     */
    private BigDecimal price;
    /**
     * 课程简介
     */
    private String courseDes;
    /**
     * 分类名称
     */
    @NotBlank(message = "请填写分类名称")
    private String categoryName;

    private String comboType;//课程套餐类别
}
