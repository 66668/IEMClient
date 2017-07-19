package com.xiaolin.bean.signin;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.linzhi.isis.BR;
import com.linzhi.isis.http.ParamNames;

import java.io.Serializable;

/**
 * 会议选择 详情modle
 */

public class SigninDetailBean extends BaseObservable implements Serializable {

    //人员编号
    @ParamNames("employeeId")
    public String EmployeeID;
    //会议编号
    @ParamNames("conferenceId")
    private String ConferenceID;
    //会议名称
    @ParamNames("conferenceName")
    private String ConferenceName;
    //人员姓名
    @ParamNames("employeeName")
    private String EmployeeName;
    //电话
    @ParamNames("telephone")
    private String Telephone;

    //座机
    @ParamNames("landLine")
    private String Landline;

    //邮箱
    @ParamNames("email")
    private String Email;

    //所属公司
    @ParamNames("empStore")
    private String EmpStore;

    //职位
    @ParamNames("position")
    private String position;

    //性别
    @ParamNames("sex")
    private String Sex;

    //年龄
    @ParamNames("age")
    private String Age;

    //微信
    @ParamNames("wechat")
    private String WeChat;
    //护照
    @ParamNames("passport")
    private String Passport;
    //报名时间
    @ParamNames("enrollTime")
    private String EnrollTime;
    //生日
    @ParamNames("birth")
    private String Birth;
    //注册时间
    @ParamNames("createTime")
    private String CreateTime;
    //是否付费
    @ParamNames("isPay")
    private String IsPay;
    // 是否签到
    @ParamNames("isSign")
    private String IsSign;
    //更新时间
    @ParamNames("lastUpdateTime")
    private String LastUpdateTime;
    //职位
    @ParamNames("companyID")
    private String CompanyID;
    //会员等级
    @ParamNames("levels")
    private String Levels;


    @Bindable
    public String getConferenceID() {
        return ConferenceID;
    }

    public void setConferenceID(String conferenceID) {
        this.ConferenceID = conferenceID;
        notifyPropertyChanged(BR.conferenceID);
    }

    @Bindable
    public String getConferenceName() {
        return ConferenceName;
    }

    public void setConferenceName(String conferenceName) {
        this.ConferenceName = conferenceName;
        notifyPropertyChanged(BR.conferenceName);

    }


    @Bindable
    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        this.Telephone = telephone;
        notifyPropertyChanged(BR.telephone);
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
    public String getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(String employeeID) {
        EmployeeID = employeeID;
        notifyPropertyChanged(BR.employeeID);
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

    @Bindable
    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
        notifyPropertyChanged(BR.createTime);
    }

    @Bindable
    public String getIsPay() {
        return IsPay;
    }

    public void setIsPay(String isPay) {
        IsPay = isPay;
        notifyPropertyChanged(BR.isPay);
    }

    @Bindable
    public String getIsSign() {
        return IsSign;
    }

    public void setIsSign(String isSign) {
        IsSign = isSign;
        notifyPropertyChanged(BR.isSign);
    }

    @Bindable
    public String getLastUpdateTime() {
        return LastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        LastUpdateTime = lastUpdateTime;
        notifyPropertyChanged(BR.lastUpdateTime);
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
    public String getLevels() {
        return Levels;
    }

    public void setLevels(String levels) {
        Levels = levels;
        notifyPropertyChanged(BR.levels);
    }
}
