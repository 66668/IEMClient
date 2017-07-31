package com.xiaolin.ui.iview;

import com.xiaolin.bean.UpgradeBean;

/**
 * 主界面View接口
 */

public interface IMainView {

    //跳转到修改密码界面
    void turnToChangePs();

    //退出
    void quitApp();

    //地图定位
    void turnToMapLocation();

    //考勤记录
    void turnToAttendRecord();

    //访客记录
    void turnToVisitor();

    //检查更新/退出 要用的一下方法：

    void showProgress();

    void hideProgress();

    void postSuccessShow(String str);

    void showUpgradeDialog(UpgradeBean bean);

    void postFaild(String msg, Exception e);
}
