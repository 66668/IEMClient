package com.xiaolin.ui.iview;

import com.xiaolin.bean.UpgradeBean;

/**
 * 主界面View接口
 */

public interface IMainView {

    //地图定位
    void turnToMapLocation();

    //考勤记录
    void turnToAttendRecord();

    //访客记录
    void turnToVisitor();

    //退出
    void quit();

    //检查更新/退出 要用的一下方法：

    void showProgress();

    void hideProgress();

    void postSuccessShow(String str);

    void showUpgradeDialog(UpgradeBean bean);

    void postFaild(String msg, Exception e);
}
