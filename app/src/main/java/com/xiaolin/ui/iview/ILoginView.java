package com.xiaolin.ui.iview;

import com.xiaolin.bean.LoginBean;

/**
 * 登录View接口
 */

public interface ILoginView {

    //登录获取数据过程中显示progress
    void showProgress();

    //获取数据结束progress消失
    void hideProgress();
    void onSuccess(LoginBean bean);

    //登录失败提示
    void showDialogFailed(String msg, Exception e);
}
