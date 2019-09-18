package io.renren.modules.sys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 功能描述: <br>
 * 订单小票实体
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/9/18 18:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelSmallOrderDto {
    private long id;
    /**
     * 派送时间段
     */
    private String sendTime;
    /**
     * 派送日期 yyyy-MM-dd
     */
    private String sendDate;
    /**
     * 收货人
     */
    private String addrReceiver;
    /**
     * 联系方式
     */
    private String addrPhone;
    /**
     * 配送时间 送出时间
     */
    private Date updateTime;
    /**
     * 物流方式
     */
    private String express;
    /**
     * 配送详细地址
     */
    private String addrDetail;
    /**
     * 特殊备注
     */
    private String orderDes;
    /**
     * 生日牌
     */
    private String orderRemark;
    /**
     * 接单客服
     */
    private String kfNick;

    /**
     * 美团券验券记录ID
     */
    private long meituanId;

    private List<OrderItemDto> orderItemDtoList;
}
