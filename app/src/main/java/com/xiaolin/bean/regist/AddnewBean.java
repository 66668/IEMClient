package com.xiaolin.bean.regist;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 添加新注册人员
 * Created by sjy on 2017/5/9.
 */

public class AddnewBean extends BaseObservable implements Serializable {
    @SerializedName("conferenceId")
    public String ConferenceID;

    @SerializedName("employeeName")
    public String EmployeeName;
    @SerializedName("telephone")
    public String Telephone;
    @SerializedName("landline")
    public String Landline;
    @SerializedName("email")
    public String Email;
    @SerializedName("empStore")
    public String EmpStore;
    @SerializedName("position")
    public String position;
    @SerializedName("sex")
    public String Sex;
    @SerializedName("age")
    public String Age;
    @SerializedName("weChat")
    public String WeChat;
    @SerializedName("passport")
    public String Passport;
    @SerializedName("enrollTime")
    public String EnrollTime;
    @SerializedName("birth")
    public String Birth;
    @SerializedName("conferenceName")
    public String ConferenceName;
    @SerializedName("companyID")
    public String CompanyID;

    @SerializedName("industry")
    public String Industry;

    @Bindable
    public String getIndustry() {
        return Industry;
    }

    public void setIndustry(String industry) {
        Industry = industry;
        notifyPropertyChanged(BR.industry);
    }

    @Bindable
    public String getConferenceID() {
        return ConferenceID;
    }

    public void setConferenceID(String conferenceID) {
        ConferenceID = conferenceID;
        notifyPropertyChanged(BR.conferenceID);
    }

    @Bindable
    public String getConferenceName() {
        return ConferenceName;
    }

    public void setConferenceName(String conferenceName) {
        ConferenceName = conferenceName;
        notifyPropertyChanged(BR.conferenceID);
    }

    @Bindable
    public String getCompanyID() {
        return CompanyID;
    }

    public void setCompanyID(String companyID) {
        CompanyID = companyID;
        notifyPropertyChanged(BR.companyID);
    }

    @Bindable
    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String employeeName) {
        EmployeeName = employeeName;
        notifyPropertyChanged(BR.employeeName);
    }

    @Bindable
    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
        notifyPropertyChanged(BR.telephone);
    }

    @Bindable
    public String getLandline() {
        return Landline;
    }

    public void setLandline(String landline) {
        Landline = landline;
        notifyPropertyChanged(BR.landline);

    }

    @Bindable
    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getEmpStore() {
        return EmpStore;
    }

    public void setEmpStore(String empStore) {
        EmpStore = empStore;
        notifyPropertyChanged(BR.empStore);
    }

    @Bindable
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
        notifyPropertyChanged(BR.position);
    }

    @Bindable
    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
        notifyPropertyChanged(BR.sex);
    }

    @Bindable
    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
        notifyPropertyChanged(BR.age);
    }

    @Bindable
    public String getWeChat() {
        return WeChat;
    }

    public void setWeChat(String weChat) {
        WeChat = weChat;
        notifyPropertyChanged(BR.weChat);
    }

    @Bindable
    public String getPassport() {
        return Passport;
    }

    public void setPassport(String passport) {
        Passport = passport;
        notifyPropertyChanged(BR.passport);
    }

    @Bindable
    public String getEnrollTime() {
        return EnrollTime;
    }

    public void setEnrollTime(String enrollTime) {
        EnrollTime = enrollTime;
        notifyPropertyChanged(BR.enrollTime);
    }

    @Bindable
    public String getBirth() {
        return Birth;
    }

    public void setBirth(String birth) {
        Birth = birth;
        notifyPropertyChanged(BR.birth);
    }

    @Override
    public String toString() {
        return "AddnewBean{" +
                "ConferenceID='" + ConferenceID + '\'' +
                ", EmployeeName='" + EmployeeName + '\'' +
                ", Telephone='" + Telephone + '\'' +
                ", Landline='" + Landline + '\'' +
                ", Email='" + Email + '\'' +
                ", EmpStore='" + EmpStore + '\'' +
                ", position='" + position + '\'' +
                ", Sex='" + Sex + '\'' +
                ", Age='" + Age + '\'' +
                ", WeChat='" + WeChat + '\'' +
                ", Passport='" + Passport + '\'' +
                ", EnrollTime='" + EnrollTime + '\'' +
                ", Birth='" + Birth + '\'' +
                ", ConferenceName='" + ConferenceName + '\'' +
                ", CompanyID='" + CompanyID + '\'' +
                ", Industry='" + Industry + '\'' +
                '}';
    }


}
