package com.xiaolin.calendar.common;

import java.util.Calendar;
import java.util.Date;

/**
 * 日历工具类.
 */

public class CalendarUtil {
    private static final String TAG = "calendar";

    /**
     * 获取一月的某天是星期几
     *
     * @param y
     * @param m
     * @param day
     * @return
     */
    public static int getDayOfWeek(int y, int m, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(y, m - 1, day);
//        Log.d(TAG, "--CalendarUtil--getDayOfWeek: calendar.get(Calendar.DAY_OF_WEEK)=" + calendar.get(Calendar.DAY_OF_WEEK));
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取一月有几天
     */
    public static int getDaysOfMonth(int y, int m) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(y, m - 1, 1);
        int dayOfMonth = calendar.getActualMaximum(Calendar.DATE);
//        Log.d(TAG, "--CalendarUtil--getDayofMonth: dayOfMonth=" + dayOfMonth);
        return dayOfMonth;
    }

    /**
     * 获取一月的月份
     */
    public static int getMothOfMonth(int y, int m) {
        Calendar cal = Calendar.getInstance();
        cal.set(y, m - 1, 1);
        int dateOfMonth = cal.get(Calendar.MONTH);
        return dateOfMonth + 1;
    }

    /**
     * 获取日期
     * int[]保存
     */
    public static int[] getYearMonthDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return new int[]{cal.get(Calendar.YEAR)
                , cal.get(Calendar.MONTH) + 1
                , cal.get(Calendar.DATE)};
    }

}
