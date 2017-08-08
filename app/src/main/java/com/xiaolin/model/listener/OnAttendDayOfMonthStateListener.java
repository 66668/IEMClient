package com.xiaolin.model.listener;

import com.xiaolin.bean.AttendDaysOFMonthStateBean;

import java.util.ArrayList;

/**
 * 月考勤的状态监听
 */

public interface OnAttendDayOfMonthStateListener {

    void onAttendDaysSuccess(ArrayList<AttendDaysOFMonthStateBean> list);

    void onAttendDaysFailed(String msg, Exception e);

}
