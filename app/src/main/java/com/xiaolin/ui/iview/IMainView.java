package com.xiaolin.ui.iview;

/**
 * 主界面View接口
 */

public interface IMainView {

    //跳转到个人信息界面
    void turnToInfo();

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
}
