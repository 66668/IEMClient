package com.xiaolin.calendar.common;

import com.xiaolin.bean.AttendDaysOFMonthStateBean;
import com.xiaolin.utils.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sjy on 2017/8/9.
 */

public class CalendarCache {
    private static CalendarCache calendarCache;

    private static HashMap<String, ArrayList<AttendDaysOFMonthStateBean>> map = new HashMap<>();

    private CalendarCache() {

    }

    public static CalendarCache getInstance() {
        if (calendarCache == null) {
            calendarCache = new CalendarCache();
        }
        return calendarCache;
    }

    public static void setCacahe(String key, ArrayList<AttendDaysOFMonthStateBean> list) {
        if (map.get(key) != null) {
            map.remove(key);
        }
        LogUtil.d("SJY", "保存" + list.size());
        map.put(key, list);
    }

    public static ArrayList<AttendDaysOFMonthStateBean> getCache(String key) {
        if (map.get(key) == null) {
            return null;
        }
        return map.get(key);
    }

    public static void clearAll() {
        map.clear();
    }


}
