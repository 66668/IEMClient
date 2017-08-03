package com.xiaolin.calendarlib.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.xiaolin.calendarlib.common.CalendarAdapter;
import com.xiaolin.calendarlib.common.CalendarItemBean;
import com.xiaolin.calendarlib.util.CalendarUtil;
import com.xiaolin.calendarlib.util.LogUtil;

import java.util.Date;
import java.util.List;

/**
 * 绘制日历视图
 * calendarDateVie中使用
 */

public class CalendarView extends ViewGroup {
    //常量
    private static final String TAG = "calendar";
    //变量
    private int row = 6;
    private int column = 7;
    private int itemWidth;//item的高
    private int itemHeight;//item的高
    private boolean isToday;
    private int selectPosition = -1;

    private List<CalendarItemBean> listItemBean;
    private OnItemClickListener onItemClickListener;//自定义的item监听
    private CalendarAdapter calendarAdapter;


    /**
     * 构造方法
     * row需要变化
     */

    public CalendarView(Context context, int row) {
        super(context);
        this.row = row;
    }

    /**
     * 构造方法
     */
    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        /**
         * viewgroup默认情况下调用WILL_NOT_DROW，
         * 调用以下方法取消这个设置，满足自定义时重写onDraw
         */
        setWillNotDraw(false);
    }

    /**
     * 自定义item的监听
     * <p>
     * setItemClick（）中调用
     */
    public interface OnItemClickListener {

        void onItemClick(View view, int postion, CalendarItemBean bean);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * item的高
     *
     * @return
     */
    public int getItemHeight() {
        return itemHeight;
    }

    /**
     * 设置日历item的adpater
     * CalendarDateView构造方法中调用
     *
     * @param calendarAdapter
     */
    public void setCalendarAdapter(CalendarAdapter calendarAdapter) {
        this.calendarAdapter = calendarAdapter;
    }

    /**
     * CalendarDateView中调用
     * 将计算出的日历集合赋值到CalendarView
     *
     * @param listItemBean
     * @param isToday
     */
    public void setData(List<CalendarItemBean> listItemBean, boolean isToday) {
        this.listItemBean = listItemBean;
        this.isToday = isToday;
        setItem();
        requestLayout();//唤醒onLayout方法重绘布局
    }

    private void setItem() {
        selectPosition = -1;
        if (calendarAdapter == null) {
            throw new RuntimeException("请在CalendarView中设置calendarAdapte的值");
        }
        int size = listItemBean.size();
        LogUtil.d(TAG, "setItem: listItemBean.size()=" + listItemBean.size());
        for (int i = 0; i < size; i++) {
            CalendarItemBean bean = listItemBean.get(i);
            View view = getChildAt(i);
            View childView = calendarAdapter.getView(view, this, bean);//

            //添加item布局
            if (view == null || view != childView) {
                /**
                 * addView 内部触发 requestLayout 最终将调用 onLayout
                 * addViewInLayout 是这样一个“更安全”的版本 addView 你真的要添加一个新的视图在 onLayout
                 * 它基本上不会触发布局传递（不调用requestLayout内部）
                 */
                addViewInLayout(childView, i, childView.getLayoutParams(), true);
            }

            /**
             * selectPosition赋值
             */
            if (isToday && selectPosition == -1) {
                int[] date = CalendarUtil.getYearMonthDay(new Date());//获取当前日期

                if (bean.year == date[0] && bean.moth == date[1] && bean.day == date[2]) {
                    LogUtil.d(TAG, "setItem: 今天 selectPosition=" + i);
                    selectPosition = i;
                } else {
                    if (selectPosition == -1 && bean.day == 1) {
                        LogUtil.d(TAG, "setItem: selectPosition == -1 && bean.day == 1");
                        selectPosition = i;
                    }
                }
            }
            childView.setSelected(selectPosition == i);

            //设置item监听
            setItemClick(childView, i, bean);

        }
    }

    /**
     * item的监听
     *
     * @param view
     * @param position
     * @param bean
     */
    public void setItemClick(final View view, final int position, final CalendarItemBean bean) {
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //设置正确的item点击颜色
                if (selectPosition != -1) {
                    getChildAt(selectPosition).setSelected(false);
                    getChildAt(position).setSelected(true);
                }
                selectPosition = position;
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(view, position, bean);//自定义监听
                }
            }
        });
    }

    /**
     * CalendarDateView构造中init()调用
     *
     * @return
     */
    public Object[] getSelect() {
        return new Object[]{getChildAt(selectPosition), selectPosition, listItemBean.get(selectPosition)};
    }

    /**
     * 最终在CalendarLayout--getSelectRect()方法中调用
     *
     * @return
     */
    public int[] getSelectPostion() {
        Rect rect = new Rect();
        getChildAt(selectPosition).getHitRect(rect);
        return new int[]{rect.left, rect.top, rect.right, rect.top};
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LogUtil.d(TAG, "CalendarView--onMeasure:");

        //获取屏幕宽度
        int parentWidth = MeasureSpec.getSize(MeasureSpec.makeMeasureSpec(widthMeasureSpec, MeasureSpec.EXACTLY));
        LogUtil.d(TAG, "CalendarView--onMeasure: 屏幕宽度parentWidth=" + parentWidth);
        itemWidth = parentWidth / column;
        itemHeight = itemWidth;

        View view = getChildAt(0);
        if (view == null) {
            return;
        }

        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params != null && params.height > 0) {
            itemHeight = params.height;
        }
        //设置日历视图的大小
        setMeasuredDimension(parentWidth, itemHeight * row);
        LogUtil.d(TAG, "CalendarView--onMeasure:getChildCount=" + getChildCount());
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            childView.measure(MeasureSpec.makeMeasureSpec(itemWidth, MeasureSpec.EXACTLY)
                    , MeasureSpec.makeMeasureSpec(itemHeight, MeasureSpec.EXACTLY));
        }
        LogUtil.d(TAG, "CalendarView--onMeasure()item的大小设置: itemHeight = ["
                + itemHeight + "], itemWidth = [" + itemWidth + "]");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        LogUtil.d(TAG, "CalendarView--onLayout:");
        for (int i = 0; i < getChildCount(); i++) {
            layoutChild(getChildAt(i), i, left, top, right, bottom);

        }

    }

    private void layoutChild(View view, int position, int left, int top, int right, int bottom) {
        int cColum = position % column;
        int cRow = position / column;
        int itemWidth = view.getMeasuredWidth();
        int itemHeight = view.getMeasuredHeight();

        left = cColum * itemWidth;
        top = cRow * itemHeight;
        right = left + itemWidth;
        bottom = top + itemHeight;
        LogUtil.d(TAG, "CalendarView--onLayout--i=" + position +
                "left=" + left + "--top" + top + "--right=" + right + "--bottom=" + bottom);
        view.layout(left, top, right, bottom);
    }
}
