package com.xiaolin.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.SerializedName;
import com.linzhi.isis.BR;

import java.io.Serializable;

/**
 * Created by sjy on 2017/5/27.
 */

public class BaseBean<T> extends BaseObservable implements Serializable {
    @SerializedName("code")
    public String code;

    @SerializedName("message")
    public String message;

    @SerializedName("result")
    public T result;

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
    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
        notifyPropertyChanged(BR.result);
    }
}
