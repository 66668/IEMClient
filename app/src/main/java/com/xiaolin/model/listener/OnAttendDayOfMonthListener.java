package com.xiaolin.model.listener;

import com.xiaolin.bean.AttendDaysOFMonthBean;

import java.util.List;

/**
 * 月考勤的状态监听
 */

public interface OnAttendDayOfMonthListener {

    void onAttendDaysSuccess(List<AttendDaysOFMonthBean> list);

    void onAttendDaysFailed(String msg, Exception e);

}
