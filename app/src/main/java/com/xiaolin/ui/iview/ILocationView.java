package com.xiaolin.ui.iview;

/**
 * 地图签到接口
 */

public interface ILocationView {
    void showProgress();

    void hideProgress();

    void postSuccessShow(String str);

    void postFaild(String msg, Exception e);
}
