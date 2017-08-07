package com.xiaolin.model.listener;

import com.xiaolin.bean.AttendStatusMonthBean;

/**
 * 月考勤的状态监听
 */

public interface OnAttendMonthStateListener {

    void onAttendMontStateSuccess(AttendStatusMonthBean bean);

    void onAttendMontStateFailed(String msg, Exception e);

}
