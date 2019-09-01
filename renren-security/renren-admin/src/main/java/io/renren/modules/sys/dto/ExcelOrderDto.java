package io.renren.modules.sys.dto;

import io.renren.common.utils.poi.model.ExcelBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Clarence
 * @Description:
 * @Date: 2019/9/2 0:49.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcelOrderDto extends ExcelBean {
    private String createTime; // 预约时间
    private String sendTime; // 送达时间
    private String productName; // 产品名称
    private String orderDes; // 订单备注
    private String detailTaste;//口味
    private String orderRemark;//生日牌内容
    private String updateTime;//配送时间
    private String sendType;//配送方式  0：送货上门 1：门店自取
    private String addrReceiver;//收货人姓名
    private String addrPhone;//联系方式
    private String addr;//配送地址 配送城市+详细地址
    private String sendPrice;//派送费用
    private String userName;//客人微信
    private String deskClerk;//接待负责人
    private String source;//渠道来源
    private String orderPrice;//价格
    private String orderState;//是否已收款

    public String getDeskClerk(){
        if(deskClerk == null){
            return "";
        }
        return "";
    }
}
