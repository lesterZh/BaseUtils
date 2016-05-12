package com.gaia.member.gaiatt.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhangHaiTao on 2016/5/6.
 */
public class FormatUtil {

    /**
     * 将long类型的ms数 转换成日期
     * @param time
     * @return
     */
    public static String getDate(long time) {
        String date;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        date = format.format(time);
        return date;
    }

    /**
     * 获取当前日期
     * @return
     */
    public static String getNowDate() {
        return getDate(new Date().getTime());
    }

    /**
     * 将long类型的ms数 转换成日期和时间
     * @param time
     * @return
     */
    public static String getDateTime(long time) {
        String date;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = format.format(time);
        return date;
    }

    /**
     * 获取当前日期和时间
     * @return
     */
    public static String getNowDateTime() {
        return getDateTime(new Date().getTime());
    }


}
