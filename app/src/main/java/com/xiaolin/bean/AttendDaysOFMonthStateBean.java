package com.xiaolin.bean;

import java.io.Serializable;

/**
 * 月记录状态，供月日历详细展示使用
 * <p>
 * Created by sjy on 2017/8/7.
 */

public class AttendDaysOFMonthStateBean implements Serializable {
    private String DayState;// 当天考勤状态
    private String DYear;//
    private String DMonth;//
    private String DDay;//
    //


    public String getDayState() {
        return DayState;
    }

    public void setDayState(String dayState) {
        DayState = dayState;
    }

    public String getDYear() {
        return DYear;
    }

    public void setDYear(String DYear) {
        this.DYear = DYear;
    }

    public String getDMonth() {
        return DMonth;
    }

    public void setDMonth(String DMonth) {
        this.DMonth = DMonth;
    }

    public String getDDay() {
        return DDay;
    }

    public void setDDay(String DDay) {
        this.DDay = DDay;
    }

    @Override
    public String toString() {
        return "AttendDaysOFMonthStateBean{" +
                "DayState='" + DayState + '\'' +
                ", DYear='" + DYear + '\'' +
                ", DMonth='" + DMonth + '\'' +
                ", DDay='" + DDay + '\'' +
                '}';
    }
}
