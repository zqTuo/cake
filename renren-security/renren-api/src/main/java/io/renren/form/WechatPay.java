package io.renren.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/8/28 20:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "微信支付参数实体")
@Builder
public class WechatPay {
    @ApiModelProperty(value = "随机字符串",required = true)
    private String nonceStr;
    @ApiModelProperty(value = "时间戳",required = true)
    private String timestamp;
    @ApiModelProperty(value = "签名",required = true)
    private String paySign;
    @ApiModelProperty(value = "预支付ID",required = true)
    private String prepay_id;
    @ApiModelProperty(value = "加密方式",required = true)
    private String signType;
}
