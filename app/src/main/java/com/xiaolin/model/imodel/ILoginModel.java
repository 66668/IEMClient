package com.xiaolin.model.imodel;

import com.xiaolin.model.listener.OnLoginListener;

/**
 * model层接口
 */

public interface ILoginModel {

    void mLogin(String storeName, String userName, String passWord, String IP, OnLoginListener listener);
}
