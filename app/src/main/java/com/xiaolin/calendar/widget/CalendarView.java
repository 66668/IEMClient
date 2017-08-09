package com.xiaolin.calendar.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.xiaolin.calendar.common.CalendarAdapter;
import com.xiaolin.calendar.common.CalendarItemBean;
import com.xiaolin.calendar.common.CalendarUtil;
import com.xiaolin.utils.LogUtil;

import java.util.Date;
import java.util.List;

/**
 *
 */

public class CalendarView extends ViewGroup {

    private static final String TAG = "CalendarView";

    private int selectPostion = -1;

    private CalendarAdapter adapter;
    private List<CalendarItemBean> listBean;
    private OnItemClickListener onItemClickListener;

    private int row = 6;
    private int column = 7;
    private int itemWidth;
    private int itemHeight;

    private boolean isToday;

    /**
     * 构造方法
     */
    public CalendarView(Context context, int row) {
        super(context);
        this.row = row;
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
    }

    /**
     * 自定义item的监听
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


    public void setAdapter(CalendarAdapter adapter) {
        this.adapter = adapter;
    }

    //
    public void setData(List<CalendarItemBean> listBean, boolean isToday) {
        this.listBean = listBean;
        this.isToday = isToday;
        setItem();
        requestLayout();
    }

    /**
     * 将该月的数据显示到item上
     */

    private void setItem() {

        selectPostion = -1;

        if (adapter == null) {
            throw new RuntimeException("adapter is null,please setadapter");
        }

        for (int i = 0; i < listBean.size(); i++) {

            CalendarItemBean bean = listBean.get(i);
            LogUtil.d(TAG, "bean.toString()=" + bean.toString());
            View view = getChildAt(i);
            View chidView = adapter.getView(view, this, bean);

            if (view == null || view != chidView) {
                addViewInLayout(chidView, i, chidView.getLayoutParams(), true);
            }

            if (isToday && selectPostion == -1) {
                int[] date = CalendarUtil.getYearMonthDay(new Date());
                if (bean.year == date[0] && bean.moth == date[1] && bean.day == date[2]) {
                    selectPostion = i;
                    LogUtil.d(TAG, "CalendarView--setData--setItem--" + "isToday=" + isToday + "--selectPostion=" + i);
                }
            } else {
                if (selectPostion == -1 && bean.day == 1) {
                    LogUtil.d(TAG, "--selectPostion=" + i);
                    selectPostion = i;
                }
                //迟到早退设置
                if (bean.DayState != null) {
                    if (bean.DayState.equals("迟到") || bean.DayState.equals("早退")) {
                        //                        selectPostion = i;
                        chidView.setSelected(true);
                        LogUtil.d(TAG, "--selectPostion=迟到--早退:" + i);
                    }
                }

            }

            chidView.setSelected(selectPostion == i);

            //设置监听
            setItemClick(chidView, i, bean);

        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int parentWidth = MeasureSpec.getSize(MeasureSpec.makeMeasureSpec(widthMeasureSpec, MeasureSpec.EXACTLY));

        itemWidth = parentWidth / column;
        itemHeight = itemWidth;

        View view = getChildAt(0);
        if (view == null) {
            return;
        }
        LayoutParams params = view.getLayoutParams();
        if (params != null && params.height > 0) {
            itemHeight = params.height;
        }
        setMeasuredDimension(parentWidth, itemHeight * row);


        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            childView.measure(MeasureSpec.makeMeasureSpec(itemWidth, MeasureSpec.EXACTLY)
                    , MeasureSpec.makeMeasureSpec(itemHeight, MeasureSpec.EXACTLY));
        }

        Log.i(TAG, "onMeasure() called with: itemHeight = [" + itemHeight + "], itemWidth = [" + itemWidth + "]");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            layoutChild(getChildAt(i), i, l, t, r, b);
        }
    }

    private void layoutChild(View view, int postion, int l, int t, int r, int b) {

        int cc = postion % column;
        int cr = postion / column;

        int itemWidth = view.getMeasuredWidth();
        int itemHeight = view.getMeasuredHeight();

        l = cc * itemWidth;
        t = cr * itemHeight;
        r = l + itemWidth;
        b = t + itemHeight;
        view.layout(l, t, r, b);

    }

    public Object[] getSelect() {
        return new Object[]{getChildAt(selectPostion), selectPostion, listBean.get(selectPostion)};
    }

    /**
     * item的点击事件
     *
     * @param view
     * @param potsion
     * @param bean
     */
    public void setItemClick(final View view, final int potsion, final CalendarItemBean bean) {
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                LogUtil.d(TAG, "CalendarView--点击的位置potsion=" + potsion + "bean:" + bean.toString());
                if (selectPostion != -1) {

                    getChildAt(selectPostion).setSelected(false);
                    getChildAt(potsion).setSelected(true);

                }
                selectPostion = potsion;

                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(view, potsion, bean);
                }
            }
        });
    }


    public int[] getSelectPostion() {
        Rect rect = new Rect();
        try {
            getChildAt(selectPostion).getHitRect(rect);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int[] selectposition = new int[]{rect.left, rect.top, rect.right, rect.top};
        return selectposition;
    }
}
