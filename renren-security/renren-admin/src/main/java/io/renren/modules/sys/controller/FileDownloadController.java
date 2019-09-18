package io.renren.modules.sys.controller;

import io.renren.common.utils.Constant;
import io.renren.common.utils.DateUtil;
import io.renren.common.utils.NameTransUtil;
import io.renren.modules.sys.dto.ExcelOrderDto;
import io.renren.modules.sys.dto.ExcelSmallOrderDto;
import io.renren.modules.sys.dto.SmallOrderDto;
import io.renren.modules.sys.service.ExcelService;
import io.renren.modules.sys.service.ShopOrderService;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Clarence
 * @Description:
 * @Date: 2019/9/2 0:47.
 */
@Controller
@RequestMapping("sys/upload")
public class FileDownloadController {
    private static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Resource
    private ShopOrderService orderService;
    @Resource
    private ExcelService excelService;

    /**
     * 下载总订单表格
     * @param request
     * @param response
     */
    @RequestMapping("/downloadOrder")
    public void downloadOrder(HttpServletRequest request, HttpServletResponse response){
        response.setCharacterEncoding("utf-8");
        String date = ServletRequestUtils.getStringParameter(request,"date","");
        String dateEnd =ServletRequestUtils.getStringParameter(request,"dateEnd","");
        String ids = ServletRequestUtils.getStringParameter(request,"ids","");

        Map<String,Object> map = new HashMap<>();

        if(StringUtils.isNotBlank(ids)){ //说明要下载选中行的数据
            String[] idStr=ids.split(",");
            map.put("idArr",idStr);
        }
        if(StringUtils.isNotBlank(date)){
            map.put("date",date);
        }
        if(StringUtils.isNotBlank(dateEnd)){
            map.put("dateEnd",dateEnd);
        }

        List<ExcelSmallOrderDto> orderDtoList = orderService.getSmallData(map);

        if(orderDtoList.size()==0){
            logger.error("下载数据为空！");
            return;
        }

        for (ExcelSmallOrderDto orderDto:orderDtoList){
            if(StringUtils.isNotBlank(orderDto.getUserName())){
                orderDto.setUserName(NameTransUtil.transName(orderDto.getUserName()));
            }
            if(StringUtils.isNotBlank(orderDto.getAddrReceiver())){
                orderDto.setAddrReceiver(NameTransUtil.transName(orderDto.getAddrReceiver()));
            }
        }

        try {
            String fileName = "订单表_" + DateUtil.getNowLongTime();
            logger.info("正在下载订单表：" + orderDtoList.size() + " 条数据");
            excelService.createExport(request,response,orderDtoList, Constant.EXPORT_ORDER_EXCEL,fileName,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载总订单表格
     * @param request
     * @param response
     */
    @RequestMapping("/downloadSmallOrder")
    public void downloadSmallOrder(HttpServletRequest request, HttpServletResponse response){
        String date = ServletRequestUtils.getStringParameter(request,"date","");
        String dateEnd = ServletRequestUtils.getStringParameter(request,"dateEnd","");
        String ids = ServletRequestUtils.getStringParameter(request,"ids","");

        Map<String,Object> map = new HashMap<>();

        if(StringUtils.isNotBlank(ids)){ //说明要下载选中行的数据
            String[] idStr=ids.split(",");
            map.put("idArr",idStr);
        }
        if(StringUtils.isNotBlank(date)){
            map.put("date",date);
        }
        if(StringUtils.isNotBlank(dateEnd)){
            map.put("dateEnd",dateEnd);
        }

        List<SmallOrderDto> smallOrderDtos = orderService.getSmallData(map);

        if(smallOrderDtos.size()==0){
            logger.error("下载数据为空！");
            return;
        }

        try {
            String fileName = "蛋糕详情表_" + DateUtil.getNowLongTime();
            logger.info("正在下载订单表：" + smallOrderDtos.size() + " 条数据");
            excelService.createExport(request,response,smallOrderDtos, Constant.EXPORT_SMALLORDER_EXCEL,fileName,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
