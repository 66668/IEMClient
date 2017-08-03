package com.xiaolin.calendarlib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.xiaolin.calendarlib.R;
import com.xiaolin.calendarlib.common.CalendarAdapter;
import com.xiaolin.calendarlib.common.CalendarFactory;
import com.xiaolin.calendarlib.common.CalendarItemBean;
import com.xiaolin.calendarlib.interfaceView.CalendarTopView;
import com.xiaolin.calendarlib.listener.CalendarTopViewChangeListener;
import com.xiaolin.calendarlib.util.CalendarUtil;
import com.xiaolin.calendarlib.util.LogUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 日历界面的操作
 * <p>
 * （1）CalendarView 具体绘制日历界面
 * （2）CalendarTopViewChangeListener
 * <p>CalendarFactory获取日历数据集合
 * <p>
 * Created by sjy on 2017/8/3.
 */

public class CalendarDateView extends ViewPager implements CalendarTopView {
    //常量
    private static final String TAG = "calendar";

    //变量
    Map<Integer, CalendarView> viewMap = new HashMap<>();
    private CalendarTopViewChangeListener calendarTopViewChangeListener;
    private CalendarView.OnItemClickListener onItemClickListener;//CalendarVew自定义的item监听

    private LinkedList<CalendarView> cache = new LinkedList();
    private int row = 6;//设置6行显示日历

    private CalendarAdapter calendarAdapter;
    private int calendarItemHeight = 0;

    public CalendarDateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取自定义属性
        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.CalendarDateView);
        row = typeArray.getInteger(R.styleable.CalendarDateView_cbd_calendar_row, 6);
        typeArray.recycle();

        init();
    }

    /**
     * 设置适配
     *
     * @param calendarAdapter
     */
    public void setCalendarAdapter(CalendarAdapter calendarAdapter) {
        this.calendarAdapter = calendarAdapter;
        initData();
    }

    /**
     * CalendarVew自定义的item监听
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(CalendarView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     *
     */
    private void init() {
        final int[] dateArray = CalendarUtil.getYearMonthDay(new Date());

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
            public Object instantiateItem(ViewGroup container, int position) {
                CalendarView calendarView;
                if (!cache.isEmpty()) {
                    calendarView = cache.removeFirst();
                } else {
                    calendarView = new CalendarView(container.getContext(), row);
                }
                calendarView.setOnItemClickListener(onItemClickListener);
                calendarView.setCalendarAdapter(calendarAdapter);

                calendarView.setData(CalendarFactory.getMonthOfDayList(dateArray[0], dateArray[1] + position - Integer.MAX_VALUE / 2), position == Integer.MAX_VALUE / 2);
                container.addView(calendarView);

                viewMap.put(position, calendarView);//添加到map集合中
                LogUtil.d("CalendarDateView--instantiateItem");

                return calendarView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
                cache.addLast((CalendarView) object);
                viewMap.remove(position);//移除map集合
                LogUtil.d("CalendarDateView--destroyItem");
            }
        });

        /**
         * 添加日历视图切换监听
         *
         * @param listener
         */
        addOnPageChangeListener(new SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (onItemClickListener != null) {
                    CalendarView calendarView = viewMap.get(position);
                    Object[] object = calendarView.getSelect();
                    onItemClickListener.onItemClick((View) object[0], (int) object[1], (CalendarItemBean) object[2]);
                }
                /**
                 * 添加监听
                 */
                calendarTopViewChangeListener.onLayoutChange(CalendarDateView.this);
            }
        });
    }


    /**
     * CalendarTopView接口实现
     * <p>
     * 最终在CalendarLayout--getSelectRect()方法中调用
     * 获取CalendarView的SelectPostion
     */

    @Override
    public int[] getCurrentSelectPostion() {
        CalendarView calendarView = viewMap.get(getCurrentItem());
        if (calendarView == null) {
            calendarView = (CalendarView) getChildAt(0);
        }

        if (calendarView != null) {
            return calendarView.getSelectPostion();
        }
        return new int[4];
    }

    //CalendarTopView接口实现
    @Override
    public int getItemHeight() {
        return calendarItemHeight;
    }

    /**
     * CalendarTopView接口实现
     * <p>
     * 添加监听
     */

    @Override
    public void setCalendarTopViewChangeListener(CalendarTopViewChangeListener listener) {
        calendarTopViewChangeListener = listener;
    }

    /**
     *
     */
    private void initData() {
        setCurrentItem(Integer.MAX_VALUE / 2, false);//viewpager的页面切换
        LogUtil.d(TAG, "CalendarDateView--setCalendarAdapter--viewpager的页面切换");
        getAdapter().notifyDataSetChanged();
    }
}
