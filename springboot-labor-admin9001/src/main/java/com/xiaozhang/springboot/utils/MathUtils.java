package com.xiaozhang.springboot.utils;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

/**
 * @author: xiaozhangtx
 * @ClassName: MathUtils
 * @Description: TODO 数学工具包
 * @date: 2023/4/4 21:11
 * @Version: 1.0
 */
@Component
public class MathUtils {

    public Date[] getDatesBetween(Date startDate, Date endDate) {

        long days = DateUtil.between(startDate, endDate, DateUnit.DAY);
        Date[] dates = new Date[(int) days + 1];

        int index = 0;
        while (startDate.before(endDate)) {
            dates[index++] = startDate;
            startDate = DateUtil.offsetDay(startDate, 1);
        }
        dates[index] = endDate;
        return dates;
    }

    /**
     * 计算两个坐标点之间的距离
     *
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @return
     */
    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;
        return distance;
    }

    /**
     * 计算工作时长
     *
     * @param signInTime
     * @param signOutTime
     * @return
     */
    public Double getDuration(Date signInTime, Date signOutTime) {
        long between = DateUtil.between(signInTime, signOutTime, DateUnit.MINUTE);
        Double duration = Double.valueOf(NumberUtil.roundStr(NumberUtil.div(between, 60), 1));
        return duration;
    }

}
