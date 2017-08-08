package com.xiaolin.calendar.common;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.xiaolin.bean.AttendDaysOFMonthStateBean;
import com.xiaolin.calendar.widget.CalendarView;
import com.xiaolin.utils.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static com.xiaolin.calendar.common.CalendarFactory.getMonthOfDayList;

/**
 * Created by sjy on 2017/8/8.
 */

public class CalendarViewAdapter extends PagerAdapter {
    private static final String TAG = "CalendarDateView";
    private LinkedList<CalendarView> cache = new LinkedList();
    CalendarAdapter mAdapter;
    int[] dateArr;
    int row;
    CalendarView.OnItemClickListener onItemClickListener;
    ArrayList<AttendDaysOFMonthStateBean> listSource = new ArrayList<>();
    HashMap<Integer, CalendarView> viewMap = new HashMap<>();

    public CalendarViewAdapter(int[] dateArr, int row, CalendarView.OnItemClickListener onItemClickListener) {
        this.cache = new LinkedList();
        this.dateArr = dateArr;
        this.row = row;
        this.onItemClickListener = onItemClickListener;
    }

    public void setCalendarAdapter(CalendarAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    public void setListSource(ArrayList<AttendDaysOFMonthStateBean> listSource) {
        this.listSource = listSource;
    }

    public HashMap<Integer, CalendarView> getViewMap() {
        return viewMap;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 为给定的位置创建相应的View。创建View之后,需要在该方法中自行添加到container中。
     */

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LogUtil.d(TAG, "instantiateItem（）");
        CalendarView calendarView;
        //初始化CalendarView布局
        if (!cache.isEmpty()) {
            calendarView = cache.removeFirst();
        } else {
            calendarView = new CalendarView(container.getContext(), row);
        }

        calendarView.setOnItemClickListener(onItemClickListener);
        calendarView.setAdapter(mAdapter);

        //切换视图后获得的 年月
        int year = dateArr[0];
        int month = dateArr[1] + position - Integer.MAX_VALUE / 2;

        String key = year + " " + month;//给Viewpager加标识

        //赋值
        LogUtil.d(TAG, "instantiateItem（）--月：" + month + "--Integer.MAX_VALUE / 2=" + Integer.MAX_VALUE / 2 + "--position=" + position);

        //将考勤状态封装到数据集合中
        List<CalendarItemBean> list;
        if (listSource != null && listSource.size() > 0) {
            if (listSource.get(0).getDMonth().equals(month)) {
                list = getMonthOfDayList(year, month, listSource);
            } else {
                list = getMonthOfDayList(year, month, null);
            }

        } else {
            list = getMonthOfDayList(year, month, null);
        }

        //打印
        for (CalendarItemBean bean : list) {
            LogUtil.d(TAG, "打印：" + bean.toString());
        }
        calendarView.setData(list, position == Integer.MAX_VALUE / 2);

        //添加viewGroup
        container.addView(calendarView);

        viewMap.put(position, calendarView);

        return calendarView;
    }

    /**
     * 为给定的位置移除相应的View
     *
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        cache.addLast((CalendarView) object);
        viewMap.remove(position);
    }

}
