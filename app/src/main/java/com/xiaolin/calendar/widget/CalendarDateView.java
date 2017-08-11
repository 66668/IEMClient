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
import com.xiaolin.calendar.interfaceView.ICalendarDateView;
import com.xiaolin.calendar.listener.CalendarTopViewChangeListener;
import com.xiaolin.utils.LogUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static com.xiaolin.calendar.common.CalendarFactory.getMonthOfDayList;


/**
 * 日历的左右滑动视图，并赋值数据
 */

public class CalendarDateView extends ViewPager implements ICalendarDateView {
    private static final String TAG = "CalendarDateView";
    HashMap<Integer, CalendarView> viewMap = new HashMap<>();
    private CalendarTopViewChangeListener mCaledarLayoutChangeListener;
    private com.xiaolin.calendar.widget.CalendarView.OnItemClickListener onItemClickListener;
    final int[] dateArr = CalendarUtil.getYearMonthDay(new Date());
    private LinkedList<CalendarView> cache = new LinkedList();

    private int row = 6;

    private CalendarAdapter mAdapter;
    private int calendarItemHeight = 0;
    MyViewAdapter viewAdapter;

    public CalendarDateView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //自定义属性解析（attrs.xml）
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CalendarDateView);//解析attrs.xml，取出属性
        row = typedArray.getInteger(R.styleable.CalendarDateView_cbd_calendar_row, 6);
        typedArray.recycle();//回收
        viewAdapter = new MyViewAdapter();
        init();
    }

    private void init() {
        LogUtil.d(TAG, "init()1");
        LogUtil.d("SJY", "setAdapter()");
        setAdapter(viewAdapter);

        //添加页面切换监听
        addOnPageChangeListener(new SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                LogUtil.d(TAG, "添加页面切换监听");
                if (onItemClickListener != null) {
                    CalendarView view = viewMap.get(position);
                    Object[] obs = view.getSelect();
                    onItemClickListener.onItemClick((View) obs[0]
                            , (int) obs[1]
                            , (CalendarItemBean) obs[2]);
                }
                //回调CalendarLayout的视图切换监听
                mCaledarLayoutChangeListener.onLayoutChange(CalendarDateView.this);
            }
        });
        LogUtil.d(TAG, "init()3");
    }

    public void setCalendarAdapter(CalendarAdapter adapter) {
        LogUtil.d("SJY", "setCalendarAdapter()");
        mAdapter = adapter;
        initData();
    }

    private void initData() {
        LogUtil.d(TAG, "setAdapter--Integer.MAX_VALUE / 2=" + Integer.MAX_VALUE / 2);
        setCurrentItem(Integer.MAX_VALUE / 2, false);
        getAdapter().notifyDataSetChanged();//通知更新

    }

    //绑定CalendarView的点击监听
    public void setOnItemClickListener(CalendarView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //确定日历的布局大小
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LogUtil.d(TAG, "onMeasure()");
        int calendarHeight = 0;
        if (getAdapter() != null) {
            CalendarView view = (CalendarView) getChildAt(0);
            if (view != null) {
                calendarHeight = view.getMeasuredHeight();
                calendarItemHeight = view.getItemHeight();
                LogUtil.d(TAG, "onMeasure()calendarHeight=" + calendarHeight + "--calendarItemHeight" + calendarItemHeight);
            }
        }

        //确定尺寸
        setMeasuredDimension(widthMeasureSpec
                , MeasureSpec.makeMeasureSpec(calendarHeight
                        , MeasureSpec.EXACTLY));
    }


    /**
     * 获取item的参数
     *
     * @return
     */
    @Override
    public int[] getCurrentSelectPosition() {
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
    public void setCalendarDateViewChangeListener(CalendarTopViewChangeListener listener) {
        mCaledarLayoutChangeListener = listener;
    }

    /**
     * 日历适配
     */

    public class MyViewAdapter extends PagerAdapter {


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

            calendarView.setTag(position);//给Viewpager加标识，getItemPosition来调用

            //赋值
            LogUtil.d(TAG, "instantiateItem（）--月：" + month);

            //将考勤状态封装到数据集合中
            List<CalendarItemBean> list = getMonthOfDayList(year, month, null);

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

            container.removeView((View) object);//移除ViewPager
            cache.addLast((CalendarView) object);
            viewMap.remove(position);
        }

        //更新已有界面的方法
        @Override
        public int getItemPosition(Object object) {
            LogUtil.d(TAG, "getItemPosition（）");
            return POSITION_NONE;//该值默认是-2
        }

    }


}
