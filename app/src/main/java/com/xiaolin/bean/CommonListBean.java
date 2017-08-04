package com.xiaolin.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 返回数据是list
 * Created by sjy on 2017/7/24.
 */

public class CommonListBean<T> implements Serializable {
    public String code;
    public String message;
    public List<T> result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "VisitorListBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", result=" + result.toArray().toString() +
                '}';
    }
}
