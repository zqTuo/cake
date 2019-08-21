package io.renren.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 功能描述: <br>
 * 首页热销栏目
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/8/21 17:18
 */
@ApiModel(value = "热销栏目实体")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotColumnDto {
    private long id;
    private String hotTitle;
    private List<IndexProductDto> productList;
}
