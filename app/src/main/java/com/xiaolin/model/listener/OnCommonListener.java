package com.xiaolin.model.listener;

/**
 * Created by sjy on 2017/7/28.
 */

public interface OnCommonListener {
    void onSuccess(String str);
    void onFailed(String msg, Exception e);
}
