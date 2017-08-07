package com.xiaolin.calendar.common;

import android.view.View;
import android.view.ViewGroup;

/**
 * CalendarView/CalendarDateView使用
 * 具体调用act中使用
 */

public interface CalendarAdapter {
    View getView(View convertView, ViewGroup parentView, CalendarItemBean bean);
}
