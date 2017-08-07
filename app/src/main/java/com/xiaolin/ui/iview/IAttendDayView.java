package com.xiaolin.ui.iview;

import com.xiaolin.bean.AttendDaysOFMonthBean;

import java.util.List;

/**
 * 月考勤view
 */

public interface IAttendDayView {
    void showProgress();

    void hideProgress();

    void postSuccessShow(AttendDaysOFMonthBean list);

    void postSuccessUse(List<AttendDaysOFMonthBean> list);

    void postFaild(String msg, Exception e);
}
