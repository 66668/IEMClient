package com.xiaolin.bean.login;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.linzhi.isis.BR;
import com.linzhi.isis.http.ParamNames;

import java.io.Serializable;

/**
 * 返回登录接口信息
 *
 * databinding数据源更新，界面自动更新操作
 * <p>
 * （1）@Bindable
 * （2） notifyPropertyChanged(BR.xxx)
 */

public class UserInfoBean extends BaseObservable implements Serializable {

    /**
     * 返回登录接口信息
     */
    @ParamNames("employeeid")
    private String EmployeeID;
    @ParamNames("storeuserid")
    private String StoreUserId;
    @ParamNames("storeid")
    private String StoreID;

    @Bindable
    public String getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(String employeeid) {
        EmployeeID = employeeid;
        notifyPropertyChanged(BR.employeeID);//int类型
    }

    @Bindable
    public String getStoreUserId() {
        return StoreUserId;

    }

    public void setStoreUserId(String storeuserid) {
        StoreUserId = storeuserid;
        notifyPropertyChanged(BR.storeUserId);
    }

    @Bindable
    public String getStoreID() {
        return StoreID;
    }

    public void setStoreID(String storeid) {
        this.StoreID = storeid;
        notifyPropertyChanged(BR.storeID);
    }
}
