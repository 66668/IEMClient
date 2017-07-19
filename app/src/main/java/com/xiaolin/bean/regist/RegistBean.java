package com.xiaolin.bean.regist;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.SerializedName;
import com.linzhi.isis.BR;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sjy on 2017/5/9.
 */

public class RegistBean extends BaseObservable implements Serializable {
    @SerializedName("code")
    public String code;

    @SerializedName("message")
    public String message;

    @SerializedName("result")
    public List<RegistDetailBean> result;

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
    public List<RegistDetailBean> getResult() {
        return result;
    }

    public void setResult(List<RegistDetailBean> result) {
        this.result = result;
        notifyPropertyChanged(BR.result);
    }

    @Override
    public String toString() {
        return "RegistBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", result.size()=" + result.size() +
                '}';
    }
}
