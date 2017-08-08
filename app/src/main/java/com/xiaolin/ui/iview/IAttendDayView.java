package com.xiaolin.ui.iview;

import com.xiaolin.bean.AttendDaysOFMonthBean;
import com.xiaolin.bean.AttendDaysOFMonthStateBean;

import java.util.ArrayList;

/**
 * 月考勤view
 */

public interface IAttendDayView {
    void showProgress();

    void hideProgress();

    void postSuccessShow(AttendDaysOFMonthBean list);

    void postSuccessUse(ArrayList<AttendDaysOFMonthStateBean> list);

    void postFaild(String msg, Exception e);
}
