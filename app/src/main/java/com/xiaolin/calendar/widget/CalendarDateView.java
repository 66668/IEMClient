package com.xiaolin.calendar.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.xiaolin.R;
import com.xiaolin.calendar.common.CalendarAdapter;
import com.xiaolin.calendar.common.CalendarItemBean;
import com.xiaolin.calendar.common.CalendarUtil;
import com.xiaolin.calendar.interfaceView.CalendarTopView;
import com.xiaolin.calendar.listener.CalendarTopViewChangeListener;
import com.xiaolin.utils.LogUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static com.xiaolin.calendar.common.CalendarFactory.getMonthOfDayList;


/**
 * Created by codbking on 2016/12/18.
 * email:codbking@gmail.com
 * github:https://github.com/codbking
 * blog:http://www.jianshu.com/users/49d47538a2dd/latest_articles
 */

public class CalendarDateView extends ViewPager implements CalendarTopView {
    private static final String TAG = "calendar";
    HashMap<Integer, CalendarView> viewMap = new HashMap<>();
    private CalendarTopViewChangeListener mCaledarLayoutChangeListener;
    private com.xiaolin.calendar.widget.CalendarView.OnItemClickListener onItemClickListener;

    private LinkedList<CalendarView> cache = new LinkedList();

    private int MAXCOUNT = 6;


    private int row = 6;

    private CalendarAdapter mAdapter;
    private int calendarItemHeight = 0;

    public CalendarDateView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CalendarDateView);
        row = a.getInteger(R.styleable.CalendarDateView_cbd_calendar_row, 6);
        a.recycle();

        init();
    }

    /**
     * 网络数据赋值，
     *
     */
    public void setSourseDate(){

    }

    public void setAdapter(CalendarAdapter adapter) {
        mAdapter = adapter;
        initData();
    }

    public void setOnItemClickListener(com.xiaolin.calendar.widget.CalendarView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int calendarHeight = 0;
        if (getAdapter() != null) {
            CalendarView view = (CalendarView) getChildAt(0);
            if (view != null) {
                calendarHeight = view.getMeasuredHeight();
                calendarItemHeight = view.getItemHeight();
            }
        }
        setMeasuredDimension(widthMeasureSpec
                , MeasureSpec.makeMeasureSpec(calendarHeight
                        , MeasureSpec.EXACTLY));
    }

    private void init() {
        final int[] dateArr = CalendarUtil.getYearMonthDay(new Date());


        //绘制日历
        setAdapter(new PagerAdapter() {

            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {

                CalendarView calendarView;

                if (!cache.isEmpty()) {
                    calendarView = cache.removeFirst();
                } else {
                    calendarView = new CalendarView(container.getContext(), row);
                }

                calendarView.setOnItemClickListener(onItemClickListener);
                calendarView.setAdapter(mAdapter);

                //赋值
                LogUtil.d(TAG, "CalendarDateView--init--PagerAdapter--instantiateItem--月：" + (dateArr[1] + position - Integer.MAX_VALUE / 2) + "--Integer.MAX_VALUE / 2=" + Integer.MAX_VALUE / 2 + "--position=" + position);

                List<CalendarItemBean> list = getMonthOfDayList(dateArr[0], dateArr[1] + position - Integer.MAX_VALUE / 2);
                calendarView.setData(list, position == Integer.MAX_VALUE / 2);

                container.addView(calendarView);
                viewMap.put(position, calendarView);

                return calendarView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
                cache.addLast((CalendarView) object);
                viewMap.remove(position);
            }
        });

        //添加页面切换监听
        addOnPageChangeListener(new SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                if (onItemClickListener != null) {
                    CalendarView view = viewMap.get(position);
                    Object[] obs = view.getSelect();
                    onItemClickListener.onItemClick((View) obs[0]
                            , (int) obs[1]
                            , (CalendarItemBean) obs[2]);
                }

                mCaledarLayoutChangeListener.onLayoutChange(CalendarDateView.this);
            }
        });
    }


    @Override
    public int[] getCurrentSelectPosition() {
        LogUtil.d(TAG, "CalendarDateView--CalendarTopView方法实现：getCurrentSelectPosition-");
        CalendarView view = viewMap.get(getCurrentItem());
        if (view == null) {
            view = (CalendarView) getChildAt(0);
        }
        if (view != null) {
            return view.getSelectPostion();
        }
        return new int[4];
    }

    @Override
    public int getItemHeight() {
        return calendarItemHeight;
    }

    @Override
    public void setCalendarTopViewChangeListener(CalendarTopViewChangeListener listener) {
        mCaledarLayoutChangeListener = listener;
    }

    private void initData() {
        setCurrentItem(Integer.MAX_VALUE / 2, false);
        getAdapter().notifyDataSetChanged();

    }

}
