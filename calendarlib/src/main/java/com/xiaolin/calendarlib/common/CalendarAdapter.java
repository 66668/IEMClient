package com.xiaolin.calendarlib.common;

import android.view.View;
import android.view.ViewGroup;

/**
 * CalendarView/CalendarDateView使用
 */

public interface CalendarAdapter {
    View getView(View convertView, ViewGroup parentView, CalendarItemBean bean);
}
