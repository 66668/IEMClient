package com.xiaolin.ui.iview;

import com.xiaolin.bean.AttendStatusMonthBean;

/**
 * 月考勤view
 */

public interface IAttendMonthStateView {
    void showProgress();

    void hideProgress();

    void postSuccessShow(AttendStatusMonthBean bean);

    void postFaild(String msg, Exception e);
}
