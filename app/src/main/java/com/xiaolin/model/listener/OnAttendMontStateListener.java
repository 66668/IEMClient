package com.xiaolin.model.listener;

import com.xiaolin.bean.AttendStatusMonthBean;

/**
 * 月考勤的状态监听
 */

public interface OnAttendMontStateListener {

    void onAttendMontStateSuccess(AttendStatusMonthBean bean);

    void onAttendMontStateFailed(String msg, Exception e);

}
