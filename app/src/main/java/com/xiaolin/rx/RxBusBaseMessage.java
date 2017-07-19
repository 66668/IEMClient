package com.xiaolin.rx;

/**
 * 事件统一处理类
 */
public class RxBusBaseMessage {
    private int code;
    private Object object;
    public RxBusBaseMessage(int code, Object object){
        this.code=code;
        this.object=object;
    }
    public RxBusBaseMessage(){}

    public int getCode() {
        return code;
    }

    public Object getObject() {
        return object;
    }
}
