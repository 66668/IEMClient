package com.xiaolin.bean;

import java.io.Serializable;

/**
 * 访客实体类
 * <p>
 * Created by sjy on 2017/7/27.
 */

public class VisitorBean implements Serializable {

    private String RecordID;//记录ID
    private String VisitorID;//
    private String VisitorName;//
    private String PhoneNumber;//
    private String RespondentID;//
    private String RespondentName;//
    private String Aim;//到访目的
    private String Affilication;//访客单位
    private String ArrivalTimePlan;//预约到来时间
    private String LeaveTimePlan;//预约离开时间
    private String WelcomeWord;//
    private String Remark;//
    private String isVip;//
    private String isReceived;//是否已接待
    private String ActualArrivalTime;//实际到访时间
    private String ImagePath;//访客预约图像
    private String ActualImagePath;//访客实际到达图像
    private String StoreID;//到访公司ID
    private String CapArrivalTime ;//
    private String iLastUpdateTime;//

    public String getCapArrivalTime() {
        return CapArrivalTime;
    }

    public void setCapArrivalTime(String capArrivalTime) {
        CapArrivalTime = capArrivalTime;
    }

    public String getRecordID() {
        return RecordID;
    }

    public void setRecordID(String recordID) {
        RecordID = recordID;
    }

    public String getVisitorID() {
        return VisitorID;
    }

    public void setVisitorID(String visitorID) {
        VisitorID = visitorID;
    }

    public String getVisitorName() {
        return VisitorName;
    }

    public void setVisitorName(String visitorName) {
        VisitorName = visitorName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getRespondentID() {
        return RespondentID;
    }

    public void setRespondentID(String respondentID) {
        RespondentID = respondentID;
    }

    public String getRespondentName() {
        return RespondentName;
    }

    public void setRespondentName(String respondentName) {
        RespondentName = respondentName;
    }

    public String getAim() {
        return Aim;
    }

    public void setAim(String aim) {
        Aim = aim;
    }

    public String getAffilication() {
        return Affilication;
    }

    public void setAffilication(String affilication) {
        Affilication = affilication;
    }

    public String getArrivalTimePlan() {
        return ArrivalTimePlan;
    }

    public void setArrivalTimePlan(String arrivalTimePlan) {
        ArrivalTimePlan = arrivalTimePlan;
    }

    public String getLeaveTimePlan() {
        return LeaveTimePlan;
    }

    public void setLeaveTimePlan(String leaveTimePlan) {
        LeaveTimePlan = leaveTimePlan;
    }

    public String getWelcomeWord() {
        return WelcomeWord;
    }

    public void setWelcomeWord(String welcomeWord) {
        WelcomeWord = welcomeWord;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getIsVip() {
        return isVip;
    }

    public void setIsVip(String isVip) {
        this.isVip = isVip;
    }

    public String getIsReceived() {
        return isReceived;
    }

    public void setIsReceived(String isReceived) {
        this.isReceived = isReceived;
    }

    public String getActualArrivalTime() {
        return ActualArrivalTime;
    }

    public void setActualArrivalTime(String actualArrivalTime) {
        ActualArrivalTime = actualArrivalTime;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String getActualImagePath() {
        return ActualImagePath;
    }

    public void setActualImagePath(String actualImagePath) {
        ActualImagePath = actualImagePath;
    }

    public String getStoreID() {
        return StoreID;
    }

    public void setStoreID(String storeID) {
        StoreID = storeID;
    }

    public String getiLastUpdateTime() {
        return iLastUpdateTime;
    }

    public void setiLastUpdateTime(String iLastUpdateTime) {
        this.iLastUpdateTime = iLastUpdateTime;
    }

    @Override
    public String toString() {
        return "VisitorBean{" +
                "RecordID='" + RecordID + '\'' +
                ", VisitorID='" + VisitorID + '\'' +
                ", VisitorName='" + VisitorName + '\'' +
                ", PhoneNumber='" + PhoneNumber + '\'' +
                ", RespondentID='" + RespondentID + '\'' +
                ", RespondentName='" + RespondentName + '\'' +
                ", Aim='" + Aim + '\'' +
                ", Affilication='" + Affilication + '\'' +
                ", ArrivalTimePlan='" + ArrivalTimePlan + '\'' +
                ", LeaveTimePlan='" + LeaveTimePlan + '\'' +
                ", WelcomeWord='" + WelcomeWord + '\'' +
                ", Remark='" + Remark + '\'' +
                ", isVip='" + isVip + '\'' +
                ", isReceived='" + isReceived + '\'' +
                ", ActualArrivalTime='" + ActualArrivalTime + '\'' +
                ", ImagePath='" + ImagePath + '\'' +
                ", ActualImagePath='" + ActualImagePath + '\'' +
                ", StoreID='" + StoreID + '\'' +
                ", iLastUpdateTime='" + iLastUpdateTime + '\'' +
                '}';
    }
}
