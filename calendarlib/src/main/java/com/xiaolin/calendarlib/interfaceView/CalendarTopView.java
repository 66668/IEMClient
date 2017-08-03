package com.xiaolin.calendarlib.interfaceView;

import com.xiaolin.calendarlib.listener.CalendarTopViewChangeListener;

/**
 * 由CalendarDateView具体实现该接口
 */

public interface CalendarTopView {

    int[] getCurrentSelectPostion();

    int getItemHeight();

    void setCalendarTopViewChangeListener(CalendarTopViewChangeListener listener);
}
