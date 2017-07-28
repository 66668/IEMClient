package com.xiaolin.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 登录返回 数据存储
 * Created by sjy on 2017/7/24.
 */

public class VisitorListBean implements Serializable {
    public String code;
    public String message;
    public List<VisitorBean> result;

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

    public List<VisitorBean> getResult() {
        return result;
    }

    public void setResult(List<VisitorBean> result) {
        this.result = result;
    }
}
