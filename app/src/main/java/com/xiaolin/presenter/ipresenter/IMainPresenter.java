package com.xiaolin.presenter.ipresenter;

/**
 * p层主界面接口
 */

public interface IMainPresenter {

    /**
     * navigationView控件中的监听
     *
     * @param id
     */
    void switchNavigation(int id);

    /**
     * 地图定位
     */
    void mapLocation();

    /**
     * 访客记录
     */
    void toVisitor();


    /**
     * 考勤记录
     */
    void toAttendRecord();
}
