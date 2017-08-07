package com.xiaolin.model.imodel;

import com.xiaolin.model.listener.OnAttendDayDetailListener;
import com.xiaolin.model.listener.OnAttendDayOfMonthListener;
import com.xiaolin.model.listener.OnAttendMonthStateListener;

/**
 * m层，m接口层
 */

public interface IAttendModel {

    /**
     * 获取月考勤状态
     */

    void getAttendStatusByMonth(String year, String month, final OnAttendMonthStateListener listener);


    /**
     *
     */
    void getAttendList(String year, String month, OnAttendDayOfMonthListener listener);

    /**
     *
     */

    void getAttendDetailDay(String year, String month,String day, OnAttendDayDetailListener listener);
}
