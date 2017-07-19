package com.xiaolin.bean.conference;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.SerializedName;
import com.linzhi.isis.BR;

import java.io.Serializable;
import java.util.List;

/**
 * 获取json数据基类
 * {"code":0,"message":"","result":""}
 */

public class ConferenceBean extends BaseObservable implements Serializable {
    @SerializedName("code")
    public String code;

    @SerializedName("message")
    public String message;

    @SerializedName("result")
    public List<ConferenceDetailBean> result;

    @Bindable
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
        notifyPropertyChanged(BR.code);
    }

    @Bindable
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        notifyPropertyChanged(BR.message);
    }

    @Bindable
    public List<ConferenceDetailBean> getResult() {
        return result;
    }

    public void setResult(List<ConferenceDetailBean> result) {
        this.result = result;
        notifyPropertyChanged(BR.result);
    }
}
