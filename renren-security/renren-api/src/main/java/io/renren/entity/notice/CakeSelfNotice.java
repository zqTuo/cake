package io.renren.entity.notice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/9/16 19:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CakeSelfNotice {
    private Map<String, Object> map;
    private Map<String, Object> data;

    public CakeSelfNotice(String orderNo, String productName, String sendTime, String shopName, String addrReceiver, String addrPhone, String template_id, String pagepath, String topcolor, String touser) {
        map = new LinkedHashMap<>();
        data = new LinkedHashMap<>();

        LinkedHashMap<String, String> first = new LinkedHashMap<>();
        first.put("value", "您好，您已成功预订ZUOSTUDIO蛋糕，请凭此条信息取货。");
        first.put("color", "#743A3A");
        data.put("first", first);

        //订单号
        LinkedHashMap<String, String> keynote1 = new LinkedHashMap<>();
        keynote1.put("value", orderNo);
        keynote1.put("color", "#FF0000");
        data.put("keynote1", keynote1);

        //产品
        LinkedHashMap<String, String> keynote2 = new LinkedHashMap<>();
        keynote2.put("value", productName);
        keynote2.put("color", "#FF0000");
        data.put("keynote2", keynote2);

        //取货时间
        LinkedHashMap<String, String> keynote3 = new LinkedHashMap<>();
        keynote3.put("value", sendTime);
        keynote3.put("color", "#FF0000");
        data.put("keynote3", keynote3);

        //取货门店
        LinkedHashMap<String, String> keynote4 = new LinkedHashMap<>();
        keynote4.put("value", shopName);
        keynote4.put("color", "#FF0000");
        data.put("keynote4", keynote4);

        //取货人
        LinkedHashMap<String, String> keynote5 = new LinkedHashMap<>();
        keynote5.put("value", addrReceiver);
        keynote5.put("color", "#FF0000");
        data.put("keynote5", keynote5);

        //联系电话
        LinkedHashMap<String, String> keynote6 = new LinkedHashMap<>();
        keynote6.put("value", addrPhone);
        keynote6.put("color", "#FF0000");
        data.put("keynote6", keynote6);

        LinkedHashMap<String, String> remark = new LinkedHashMap<>();
        remark.put("value", "\n有任何问题可以咨询0755-86159062");
        remark.put("color", "#000000");
        data.put("remark", remark);

        map.put("touser", touser);
        map.put("template_id", template_id);
        map.put("url", pagepath);
        map.put("topcolor", topcolor);
        map.put("data", data);
    }
}
