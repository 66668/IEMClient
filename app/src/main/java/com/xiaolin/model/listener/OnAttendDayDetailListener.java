package com.xiaolin.model.listener;

import com.xiaolin.bean.AttendDaysOFMonthBean;

/**
 * 月考勤的状态监听
 */

public interface OnAttendDayDetailListener {

    void onAttendDaysSuccess(AttendDaysOFMonthBean bean);

    void onAttendDaysFailed(String msg, Exception e);

}
