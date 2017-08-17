package com.xiaolin.bean;

import java.io.Serializable;

/**
 * Created by sjy on 2017/7/24.
 */

public class LoginBean implements Serializable {
    private String StoreId;
    private String StoreUserId;
    private String EmployeeId;
    private String TodayDate;
    private String DeviceId;
    private String EmployeeName;
    private String StoreName;
    private String FirstAttend;
    private String LastAttend;
    private String YesterdayState;

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String StoreName) {
        StoreName = StoreName;
    }

    public String getStoreId() {
        return StoreId;
    }

    public void setStoreId(String storeId) {
        StoreId = storeId;
    }

    public String getStoreUserId() {
        return StoreUserId;
    }

    public void setStoreUserId(String storeUserId) {
        StoreUserId = storeUserId;
    }

    public String getEmployeeId() {
        return EmployeeId;
    }

    public void setEmployeeId(String employeeId) {
        EmployeeId = employeeId;
    }

    public String getTodayDate() {
        return TodayDate;
    }

    public void setTodayDate(String todayDate) {
        TodayDate = todayDate;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String employeeName) {
        EmployeeName = employeeName;
    }

    public String getFirstAttend() {
        return FirstAttend;
    }

    public void setFirstAttend(String firstAttend) {
        FirstAttend = firstAttend;
    }

    public String getLastAttend() {
        return LastAttend;
    }

    public void setLastAttend(String lastAttend) {
        LastAttend = lastAttend;
    }

    public String getYesterdayState() {
        return YesterdayState;
    }

    public void setYesterdayState(String yesterdayState) {
        YesterdayState = yesterdayState;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "StoreId='" + StoreId + '\'' +
                ", StoreUserId='" + StoreUserId + '\'' +
                ", EmployeeId='" + EmployeeId + '\'' +
                ", TodayDate='" + TodayDate + '\'' +
                ", DeviceId='" + DeviceId + '\'' +
                ", EmployeeName='" + EmployeeName + '\'' +
                ", FirstAttend='" + FirstAttend + '\'' +
                ", LastAttend='" + LastAttend + '\'' +
                ", YesterdayState='" + YesterdayState + '\'' +
                '}';
    }
}
