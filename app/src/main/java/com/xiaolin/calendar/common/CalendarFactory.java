package com.xiaolin.calendar.common;

import com.xiaolin.bean.AttendDaysOFMonthStateBean;
import com.xiaolin.utils.LogUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sjy on 2017/8/3.
 */

public class CalendarFactory {
    private static HashMap<String, List<CalendarItemBean>> cache = new HashMap<>();
    private static final String TAG = "calendar";

    /**
     * 获取一个月中的day集合,同时绑定考勤数据
     */
    public static List<CalendarItemBean> getMonthOfDayList(int year, int month, ArrayList<AttendDaysOFMonthStateBean> listSource) {


        String key = year + " " + month;//以 年 月 为key
        if (cache.containsKey(key)) {
            List<CalendarItemBean> listBean = cache.get(key);
            if (listBean == null) {
                cache.remove(key);
            } else {
                return listBean;
            }
        }
        //存储一个月实例
        List<CalendarItemBean> list = new ArrayList<>();

        cache.put(key, list);

        //计算出一个月第一天星期几
        int fweek = CalendarUtil.getDayOfWeek(year, month, 1);
        int totalDaysOfMonth = CalendarUtil.getDaysOfMonth(year, month);

        //据星期推出前面还有几个显示
        for (int i = fweek - 1; i > 0; i--) {
            CalendarItemBean bean = getCalendarItemBean(year, month, 1 - i);
            bean.mothFlag = -1;
            list.add(bean);
        }

        //获取当月的天数
        for (int i = 0; i < totalDaysOfMonth; i++) {
            CalendarItemBean bean = getCalendarItemBean(year, month, i + 1);
            LogUtil.d("calendar", "month=" + month);
            //将该月对应的state参数值赋值
            if (listSource != null && listSource.size() > 0) {
                LogUtil.d("calendar", "month=" + month + "---listSource.get(0).getDMonth()=" + listSource.get(0).getDMonth());
                //月份对应才可赋值
                if (listSource.get(0).getDMonth().equals(month)) {
                    bean.setState(listSource.get(i).getDayState());
                    LogUtil.d("calendar", listSource.get(i).toString());
                }
            }

            //获取存储的日历考勤状态
            list.add(bean);
        }


        //为了塞满42个格子，显示多出当月的天数
        for (int i = 0; i < 42 - (fweek - 1) - totalDaysOfMonth; i++) {
            CalendarItemBean bean = getCalendarItemBean(year, month, totalDaysOfMonth + i + 1);
            bean.mothFlag = 1;
            list.add(bean);
        }
        //        LogUtil.d(TAG, "CalendarFactory--getMonthOfDayList:" + list.size() + "--" + list.toArray().toString());
        return list;
    }

    /**
     * 获取item的bean实例(包括农历信息，可以修改)
     */
    public static CalendarItemBean getCalendarItemBean(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);

        //item对应的年 月 日
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DATE);

        CalendarItemBean bean = new CalendarItemBean(year, month, day);

        bean.week = CalendarUtil.getDayOfWeek(year, month, day);
        //农历设置
//        String[] chinaDate = ChinaDateUtil.getChinaDate(year, month, day);
//        bean.chinaMonth = chinaDate[0];
//        bean.chinaDay = chinaDate[1];

        return bean;
    }

}
