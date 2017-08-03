package com.xiaolin.calendarlib.listener;

import com.xiaolin.calendarlib.interfaceView.CalendarTopView;

/**
 * CalendarDateView构造init()调用，添加监听
 */

public interface CalendarTopViewChangeListener {
    void onLayoutChange(CalendarTopView topView);
}
