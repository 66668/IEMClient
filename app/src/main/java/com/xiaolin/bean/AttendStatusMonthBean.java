package com.xiaolin.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 月考勤状态
 *
 * Created by sjy on 2017/8/4.
 */

public class AttendStatusMonthBean implements Serializable {
    private String NormalNumber;
    private String LateNumber;
    private String EarlyNumber;
    private String NotSignNumber;

    private List<String> LateData;
    private List<String> EarlyData;
    private List<String> NotSignData;

    public String getNormalNumber() {
        return NormalNumber;
    }

    public void setNormalNumber(String normalNumber) {
        NormalNumber = normalNumber;
    }

    public String getLateNumber() {
        return LateNumber;
    }

    public void setLateNumber(String lateNumber) {
        LateNumber = lateNumber;
    }

    public String getEarlyNumber() {
        return EarlyNumber;
    }

    public void setEarlyNumber(String earlyNumber) {
        EarlyNumber = earlyNumber;
    }

    public String getNotSignNumber() {
        return NotSignNumber;
    }

    public void setNotSignNumber(String notSignNumber) {
        NotSignNumber = notSignNumber;
    }

    public List<String> getLateData() {
        return LateData;
    }

    public void setLateData(List<String> lateData) {
        LateData = lateData;
    }

    public List<String> getEarlyData() {
        return EarlyData;
    }

    public void setEarlyData(List<String> earlyData) {
        EarlyData = earlyData;
    }

    public List<String> getNotSignData() {
        return NotSignData;
    }

    public void setNotSignData(List<String> notSignData) {
        NotSignData = notSignData;
    }

    @Override
    public String toString() {
        return "AttendStatusMonthBean{" +
                "NormalNumber='" + NormalNumber + '\'' +
                ", LateNumber='" + LateNumber + '\'' +
                ", EarlyNumber='" + EarlyNumber + '\'' +
                ", NotSignNumber='" + NotSignNumber + '\'' +
                ", LateData=" + LateData +
                ", EarlyData=" + EarlyData +
                ", NotSignData=" + NotSignData +
                '}';
    }
}
