package com.xiaolin.calendar.common;

/**
 * item上具体的信息：日期
 * 格式yyyy-MM-dd
 */

public class CalendarItemBean {
    public int year;
    public int moth;
    public int day;
    public int week;
    public String DayState;

    //-1,0,1 CalendarFactory中设置,
    public int mothFlag = 0;//是否本月

    //显示
    public String chinaMonth;
    public String chinaDay;

    public CalendarItemBean(int year, int moth, int day) {
        this.year = year;
        this.moth = moth;
        this.day = day;
    }

    public String getState() {
        return DayState;
    }

    public void setState(String state) {
        this.DayState = state;
    }

    public String getDisplayWeek() {
        String s = "";
        switch (week) {
            case 1:
                s = "星期日";
                break;
            case 2:
                s = "星期一";
                break;
            case 3:
                s = "星期二";
                break;
            case 4:
                s = "星期三";
                break;
            case 5:
                s = "星期四";
                break;
            case 6:
                s = "星期五";
                break;
            case 7:
                s = "星期六";
                break;

        }
        return s;
    }


    @Override
    public String toString() {
        return "CalendarItemBean{" +
                "year=" + year +
                ", moth=" + moth +
                ", day=" + day +
                ", DayState='" + DayState + '\'' +
                '}';
    }

    public String getDate() {
        String s = year + "-" + moth + "-" + day;
        return s;
    }
}
