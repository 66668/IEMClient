package com.xiaolin.calendar.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.xiaolin.R;
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

    //    private static final String TAG = "CalendarView";
    private static final String TAG = "SJY";

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
            isLateFlag = false;
            CalendarItemBean bean = listBean.get(i);
            LogUtil.d(TAG, "bean.toString()=" + bean.toString());
            View view = getChildAt(i);
            View chidView = adapter.getView(view, this, bean);

            if (view == null || view != chidView) {
                addViewInLayout(chidView, i, chidView.getLayoutParams(), true);
            }

            if (isToday && selectPostion == -1) {
                int[] date = CalendarUtil.getYearMonthDay(new Date());
                if (bean.year == date[0] && bean.moth == date[1]) {
                    if (bean.day == date[2]) {
                        selectPostion = i;
                        chidView.setBackground(ContextCompat.getDrawable(chidView.getContext(), R.drawable.item_calendar_today_bg));
                    }
                    LogUtil.d(TAG, "CalendarView--setData--setItem--" + "isToday=" + isToday + "--selectPostion=" + i);
                }
            } else {
                if (selectPostion == -1 && bean.day == 1) {
                    LogUtil.d(TAG, "--selectPostion=" + i);
                    selectPostion = i;
                    chidView.setBackground(ContextCompat.getDrawable(chidView.getContext(), R.drawable.item_calendar_today_bg));
                }
            }

            //迟到早退设置
            if (bean.DayState != null) {
                LogUtil.d(TAG, "bean.DayState:" + bean.DayState);
                if (bean.DayState.contains("迟到") || bean.DayState.contains("早退")) {
                    LogUtil.d(TAG, "--selectPostion=迟到--早退:" + i);
                    selectPostion = i;
                    chidView.setBackground(ContextCompat.getDrawable(chidView.getContext(), R.drawable.item_calendar_late_bg));//设置早退背景色
                    isLateFlag = true;
                    oldIsLate = true;
                } else if (bean.DayState.contains("正常")) {
                    LogUtil.d(TAG, "--selectPostion=正常:" + i);
                } else {

                }
            }

            //满足就将该item设为选定状态
            chidView.setSelected(selectPostion == i);

            //设置监听
            setItemClick(chidView, i, bean, isLateFlag);

            LogUtil.d(TAG, "selectPostion=" + selectPostion);
        }
    }

    boolean isLateFlag = false;

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
     * <p>
     * 具体处理
     *
     * @param view
     * @param potsion
     * @param bean
     */
    boolean oldIsLate = false;//

    public void setItemClick(final View view, final int potsion, final CalendarItemBean bean, final boolean isLate) {
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                LogUtil.d(TAG, "监听potsion=" + potsion + "--selectPostion:" + selectPostion + "是否迟到：" + "isLate" + isLate + "--oldIsLate=" + oldIsLate);
                if (selectPostion != -1) {

                    if (isLate) {//点击的item是否是迟到状态,且上一个状态也是
                        LogUtil.d(TAG, "1-isLate=true");

                        if (oldIsLate) {
                            LogUtil.d(TAG, "1-oldIsLate=ture");
                            view.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.item_calendar_late_bg));//
                            getChildAt(selectPostion).setSelected(true);
                        } else {
                            LogUtil.d(TAG, "1-oldIsLate=false");
                            view.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.item_calendar_today));//
                            getChildAt(selectPostion).setSelected(false);
                        }
                        view.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.item_calendar_late_bg));//
                        getChildAt(potsion).setSelected(true);
                    } else {
                        LogUtil.d(TAG, "2-isLate=false");

                        if (oldIsLate) {
                            LogUtil.d(TAG, "2-oldIsLate=ture");
                            view.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.item_calendar_late_bg));//
                            getChildAt(selectPostion).setSelected(true);
                        } else {
                            LogUtil.d(TAG, "2-oldIsLate=false");
                            view.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.item_calendar_today));//
                            getChildAt(selectPostion).setSelected(false);
                        }
                        view.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.item_calendar_today));//
                        getChildAt(potsion).setSelected(true);

                    }

                }
                
                selectPostion = potsion;
                oldIsLate = isLate;
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
