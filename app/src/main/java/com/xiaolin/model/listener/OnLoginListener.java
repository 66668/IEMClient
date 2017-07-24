package com.xiaolin.model.listener;

import com.xiaolin.bean.LoginBean;

/**
 * Created by sjy on 2017/7/24.
 */

public interface OnLoginListener {
    void onLoginSuccess(LoginBean bean);
    void onLoginFailed(String msg, Exception e);

}
