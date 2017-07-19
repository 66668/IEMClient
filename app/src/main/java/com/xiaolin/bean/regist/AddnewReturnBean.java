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

public class AddnewReturnBean extends BaseObservable implements Serializable {
    @SerializedName("employeeID")
    public String EmployeeID;

    @Bindable
    public String getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(String employeeID) {
        EmployeeID = employeeID;
        notifyPropertyChanged(BR.employeeID);
    }
}
