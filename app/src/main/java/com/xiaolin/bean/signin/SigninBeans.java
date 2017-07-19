package com.xiaolin.bean.signin;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.SerializedName;
import com.linzhi.isis.BR;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sjy on 2017/5/9.
 */

public class SigninBeans extends BaseObservable implements Serializable {
    @SerializedName("code")
    public String code;

    @SerializedName("message")
    public String message;

    @SerializedName("result")
    public List<SigninDetailBean> result;

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
    public List<SigninDetailBean> getResult() {
        return result;
    }

    public void setResult(List<SigninDetailBean> result) {
        this.result = result;
        notifyPropertyChanged(BR.result);
    }
}
