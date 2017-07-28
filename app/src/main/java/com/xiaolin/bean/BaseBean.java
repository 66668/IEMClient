package com.xiaolin.bean;

import java.io.Serializable;

/**
 * 处理 {code:"",message:"",result:""}
 * 签到返回结果
 * <p>
 * Created by sjy on 2017/7/24.
 */

public class BaseBean implements Serializable {
    public String code;
    public String message;
    public String result;

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

    public String getResult() {

        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", result=" + result.toString() +
                '}';
    }
}
