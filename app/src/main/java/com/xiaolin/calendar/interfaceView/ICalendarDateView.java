package com.xiaolin.calendar.interfaceView;

import com.xiaolin.calendar.listener.CalendarTopViewChangeListener;

/**
 * 由CalendarDateView具体实现该接口
 */

public interface ICalendarDateView {

    //具体实现在CalendarView方法中，calendarLayout调用
    int[] getCurrentSelectPosition();

    int getItemHeight();

    void setCalendarDateViewChangeListener(CalendarTopViewChangeListener listener);
}
