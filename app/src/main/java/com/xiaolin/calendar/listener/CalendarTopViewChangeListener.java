package com.xiaolin.calendar.listener;

import com.xiaolin.calendar.interfaceView.ICalendarDateView;

/**
 * CalendarDateView构造init()调用，添加监听
 */

public interface CalendarTopViewChangeListener {
    void onLayoutChange(ICalendarDateView topView);
}
