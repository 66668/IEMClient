package com.xiaolin.calendarlib.interfaceView;

import com.xiaolin.calendarlib.listener.CalendarTopViewChangeListener;

/**
 *
 */

public interface CalendarTopView {

    int[] getCurrentSelectPostion();

    int getItemHeight();

    void setCalendarTopViewChangeListener(CalendarTopViewChangeListener listener);
}
