package com.xiaolin.model.imodel;

import com.xiaolin.model.listener.OnAttendDayDetailListener;
import com.xiaolin.model.listener.OnAttendDayOfMonthStateListener;
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
    void getAttendSateList(String year, String month, OnAttendDayOfMonthStateListener listener);

    /**
     *
     */

    void getAttendDetailDay(String year, String month,String day, OnAttendDayDetailListener listener);
}
