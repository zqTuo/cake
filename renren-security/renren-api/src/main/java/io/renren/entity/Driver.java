package io.renren.entity;

import lombok.Data;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/8/12 14:08
 */
@Data
public class Driver {
    private String tempPic; // 驾照图片
    private String forgeryPic;//防伪图片
    private String country;//国家
    private String remark;//国家缩写
    private String prov;//省

}
