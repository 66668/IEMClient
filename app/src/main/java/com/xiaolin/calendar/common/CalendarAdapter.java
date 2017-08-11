package com.xiaolin.calendar.common;

import android.view.View;
import android.view.ViewGroup;

/**
 * CalendarView/CalendarDateView调用
 *
 * 具体调用在act中实现
 */

public interface CalendarAdapter {
    View getView(View convertView, ViewGroup parentView, CalendarItemBean bean);
}
