package com.xiaolin.calendar.interfaceView;

import com.xiaolin.calendar.listener.CalendarTopViewChangeListener;

/**
 * 由CalendarDateView具体实现该接口
 */

public interface CalendarTopView {

    int[] getCurrentSelectPosition();

    int getItemHeight();

    void setCalendarTopViewChangeListener(CalendarTopViewChangeListener listener);
}
