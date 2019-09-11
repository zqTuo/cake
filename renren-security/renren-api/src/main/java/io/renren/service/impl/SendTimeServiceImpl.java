package io.renren.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.JodaTimeUtil;
import io.renren.dao.SendTimeDao;
import io.renren.dao.ShopOrderDao;
import io.renren.dto.SendTimeDto;
import io.renren.entity.SendTimeEntity;
import io.renren.service.SendTimeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.List;


@Service("sendTimeService")
public class SendTimeServiceImpl extends ServiceImpl<SendTimeDao, SendTimeEntity> implements SendTimeService {
    private static Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Resource
    private ShopOrderDao orderDao;

    @Override
    public void resolveTimeList(List<SendTimeDto> sendTimeList, float distance, String selectedDate,int sendType) throws ParseException {
        //获取营业时段
        String start = sendTimeList.get(0).getStartTime();
        String end = sendTimeList.get(sendTimeList.size() - 1).getStartTime();

        // 1.自提需要提前3小时，
        // 2.配送的需要判断派送距离导致的推迟小时
        //      20km以内 只能选择4小时以后的时间点
        //      20km以外的 只能选择6小时以后的时间点
        int delay = 4;
        if(sendType == 0){ // 自提
            delay = 3;
        }else if(distance / 1000 > 20){// 20km以外
            delay = 6;
        }

        // 判断可选时段距离当前时间的有效时长（营业时间）
        //获取非营业时长
        long unWorkTime = JodaTimeUtil.duringHours(selectedDate + " " + start,selectedDate + " " + end);

        log.info("营业允许派送时间段：" + start + " 至 " + end + "，派送距离：" + distance + "，选择日期：" + selectedDate + ",非营业时长：" + unWorkTime);

        for (SendTimeDto sendTimeDto:sendTimeList){
            //判断当前时间是否已经过了这个点了 selectedDate -> yyyy-MM-dd  startTime -> HH:mm
            if(JodaTimeUtil.isExpiredCur(selectedDate + " " + sendTimeDto.getStartTime())){
                sendTimeDto.setState(1);
                continue;
            }

            // 判断是否已经约满
            int curOrderNum = orderDao.countToday(selectedDate + " " + sendTimeDto.getStartTime() ,selectedDate + " " + sendTimeDto.getEndTime(), 0);
            if(curOrderNum >= sendTimeDto.getMaxOrder()){
                // 已经约满 不能再预约了
                sendTimeDto.setState(0);
                continue;
            }

            //获取有效时长
            long durTime = JodaTimeUtil.duringHours(selectedDate + " " + sendTimeDto.getStartTime(),new Date()); // 选择时间段距离现在的时长 单位 hour
            if(durTime >= unWorkTime){
                durTime = durTime - unWorkTime; // 得到有效时长
            }

            if(durTime >= delay){
                sendTimeDto.setState(2);
            }else{
                sendTimeDto.setState(1);
            }
        }
    }

    @Override
    public List<SendTimeDto> getData() {
        return baseMapper.getData();
    }
}
