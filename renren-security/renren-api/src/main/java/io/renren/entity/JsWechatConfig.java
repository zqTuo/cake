package io.renren.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/8/21 13:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JsWechatConfig implements Serializable {
    private static final long serialVersionUID = -6192449238781863423L;

    private String appId;

    private String timestamp;

    private String noncestr;

    private String signature;

    private String url;
}
