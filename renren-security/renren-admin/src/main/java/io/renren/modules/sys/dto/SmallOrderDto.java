package io.renren.modules.sys.dto;

import io.renren.common.utils.poi.model.ExcelBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/9/4 18:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmallOrderDto extends ExcelBean {
    private String createTime; // 预约时间
    private String addrReceiver;//收货人姓名
    private String addrPhone;//收货人电话
    private String updateTime;//送出时间
    private String sendType;//物流方式  0：uu跑腿 1：门店自取
    private String addr;//收货地址 配送城市+详细地址
    private String productName;//蛋糕名称
    private String detailSize;//规格
    private String detailTaste;//口味
    private String orderDes; // 特殊备注
    private String orderRemark;//生日牌内容
    private String kfNick;//接单客服
    private String source;//渠道来源
}
