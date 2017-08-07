package com.xiaolin.bean;

import java.io.Serializable;

/**
 * 月考勤之日详细数据记录
 * <p>
 * Created by sjy on 2017/8/7.
 */

public class AttendDaysOFMonthBean implements Serializable {
    private String DayState;// 当天考勤状态
    private String FirstAttendDateTime;    // 第一次打卡时间
    private String FirstState;// 第一次打卡状态
    private String FirstAttendType;  // 第一次打卡类型
    private String FirstAttendLocation;  // 地图考勤位置
    private String LastAttendDateTime;  // 最后一次打卡时间
    private String LastState;  // 最后一次打卡状态
    private String LastAttendType;  // 最后一次打卡类型
    private String LastAttendLocation;  // 地图考勤位置
    private String DYear;  //
    private String DMonth;  //
    private String DDay;  //


    public String getDayState() {
        return DayState;
    }

    public void setDayState(String dayState) {
        DayState = dayState;
    }

    public String getFirstAttendDateTime() {
        return FirstAttendDateTime;
    }

    public void setFirstAttendDateTime(String firstAttendDateTime) {
        FirstAttendDateTime = firstAttendDateTime;
    }

    public String getFirstState() {
        return FirstState;
    }

    public void setFirstState(String firstState) {
        FirstState = firstState;
    }

    public String getFirstAttendType() {
        return FirstAttendType;
    }

    public void setFirstAttendType(String firstAttendType) {
        FirstAttendType = firstAttendType;
    }

    public String getFirstAttendLocation() {
        return FirstAttendLocation;
    }

    public void setFirstAttendLocation(String firstAttendLocation) {
        FirstAttendLocation = firstAttendLocation;
    }

    public String getLastAttendDateTime() {
        return LastAttendDateTime;
    }

    public void setLastAttendDateTime(String lastAttendDateTime) {
        LastAttendDateTime = lastAttendDateTime;
    }

    public String getLastState() {
        return LastState;
    }

    public void setLastState(String lastState) {
        LastState = lastState;
    }

    public String getLastAttendType() {
        return LastAttendType;
    }

    public void setLastAttendType(String lastAttendType) {
        LastAttendType = lastAttendType;
    }

    public String getLastAttendLocation() {
        return LastAttendLocation;
    }

    public void setLastAttendLocation(String lastAttendLocation) {
        LastAttendLocation = lastAttendLocation;
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
        return "AttendDaysOFMonthBean{" +
                "DayState='" + DayState + '\'' +
                ", FirstAttendDateTime='" + FirstAttendDateTime + '\'' +
                ", FirstState='" + FirstState + '\'' +
                ", FirstAttendType='" + FirstAttendType + '\'' +
                ", FirstAttendLocation='" + FirstAttendLocation + '\'' +
                ", LastAttendDateTime='" + LastAttendDateTime + '\'' +
                ", LastState='" + LastState + '\'' +
                ", LastAttendType='" + LastAttendType + '\'' +
                ", LastAttendLocation='" + LastAttendLocation + '\'' +
                ", DYear='" + DYear + '\'' +
                ", DMonth='" + DMonth + '\'' +
                ", DDay='" + DDay + '\'' +
                '}';
    }
}
