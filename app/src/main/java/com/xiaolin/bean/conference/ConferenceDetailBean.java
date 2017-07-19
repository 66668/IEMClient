package com.xiaolin.bean.conference;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.linzhi.isis.BR;
import com.linzhi.isis.http.ParamNames;

import java.io.Serializable;

/**
 * 会议选择 详情modle
 */

public class ConferenceDetailBean extends BaseObservable implements Serializable {

    //会议编号
    @ParamNames(value ="conferenceId")
    private String ConferenceID;

    //文件路径
    @ParamNames("fileurl")
    public String FileUrl;

    //会议名称
    @ParamNames("conferenceName")
    private String ConferenceName;

    //会议主题
    @ParamNames("conferenceTitle")
    private String ConferenceTitle;


    //会议详情
    @ParamNames("conabstracts")
    private String ConAbstracts;

    //开始时间
    @ParamNames("beginTime")
    private String BeginTime;

    //结束时间
    @ParamNames("finishTime")
    private String FinishTime;

    //会议人数
    @ParamNames("numParticipant")
    private String NumParticipant;

    //会议地址
    @ParamNames("location")
    private String Location;

    //场所名称
    @ParamNames("site")
    private String Site;

    //负责人
    @ParamNames("director")
    private String Director;

    //负责人电话
    @ParamNames("telephone")
    private String Telephone;

    //创建时间
    @ParamNames("createTime")
    private String CreateTime;

    //会场面积
    @ParamNames("area")
    private String Area;

    //备注
    @ParamNames("remark")
    private String Remark;

    //公司ID
    @ParamNames("companyID")
    private String CompanyID;

    //会议图标
    @ParamNames("photourl")
    private String Photourl;

    //报名费用
    @ParamNames("money")
    private String Money;

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
    public String getConferenceTitle() {
        return ConferenceTitle;
    }

    public void setConferenceTitle(String conferenceTitle) {
        this.ConferenceTitle = conferenceTitle;
        notifyPropertyChanged(BR.conferenceTitle);
    }

    @Bindable
    public String getConAbstracts() {
        return ConAbstracts;
    }

    public void setConAbstracts(String conAbstracts) {
        ConAbstracts = conAbstracts;
        notifyPropertyChanged(BR.conAbstracts);
    }

    @Bindable
    public String getBeginTime() {
        return BeginTime;
    }

    public void setBeginTime(String beginTime) {
        this.BeginTime = beginTime;
        notifyPropertyChanged(BR.beginTime);
    }

    @Bindable
    public String getFinishTime() {
        return FinishTime;
    }

    public void setFinishTime(String finishTime) {
        this.FinishTime = finishTime;
        notifyPropertyChanged(BR.finishTime);
    }

    @Bindable
    public String getNumParticipant() {
        return NumParticipant;
    }

    public void setNumParticipant(String numParticipant) {
        this.NumParticipant = numParticipant;
        notifyPropertyChanged(BR.numParticipant);
    }

    @Bindable
    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        this.Location = location;
        notifyPropertyChanged(BR.location);
    }

    @Bindable
    public String getSite() {
        return Site;
    }

    public void setSite(String site) {
        this.Site = site;
        notifyPropertyChanged(BR.site);
    }

    @Bindable
    public String getDirector() {
        return Director;
    }

    public void setDirector(String director) {
        this.Director = director;
        notifyPropertyChanged(BR.director);
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
    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        this.CreateTime = createTime;
        notifyPropertyChanged(BR.createTime);
    }

    @Bindable
    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        this.Area = area;
        notifyPropertyChanged(BR.area);
    }

    @Bindable
    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        this.Remark = remark;
        notifyPropertyChanged(BR.remark);
    }

    @Bindable
    public String getCompanyID() {
        return CompanyID;
    }

    public void setCompanyID(String companyID) {
        this.CompanyID = companyID;
        notifyPropertyChanged(BR.companyID);
    }

    @Bindable
    public String getPhotourl() {
        return Photourl;
    }

    public void setPhotourl(String photourl) {
        this.Photourl = photourl;
        notifyPropertyChanged(BR.photourl);
    }

    @Bindable
    public String getMoney() {
        return Money;
    }

    public void setMoney(String money) {
        this.Money = money;
        notifyPropertyChanged(BR.money);
    }

    @Bindable
    public String getFileUrl() {
        return FileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.FileUrl = fileUrl;
        notifyPropertyChanged(BR.fileUrl);
    }

    @Override
    public String toString() {
        return "ConferenceDetailBean{" +
                "ConferenceID='" + ConferenceID + '\'' +
                ", FileUrl='" + FileUrl + '\'' +
                ", ConferenceName='" + ConferenceName + '\'' +
                ", ConferenceTitle='" + ConferenceTitle + '\'' +
                ", ConAbstracts='" + ConAbstracts + '\'' +
                ", BeginTime='" + BeginTime + '\'' +
                ", FinishTime='" + FinishTime + '\'' +
                ", NumParticipant='" + NumParticipant + '\'' +
                ", Location='" + Location + '\'' +
                ", Site='" + Site + '\'' +
                ", Director='" + Director + '\'' +
                ", Telephone='" + Telephone + '\'' +
                ", CreateTime='" + CreateTime + '\'' +
                ", Area='" + Area + '\'' +
                ", Remark='" + Remark + '\'' +
                ", CompanyID='" + CompanyID + '\'' +
                ", Photourl='" + Photourl + '\'' +
                ", Money='" + Money + '\'' +
                '}';
    }
}
