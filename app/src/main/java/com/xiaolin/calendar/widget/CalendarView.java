package com.xiaolin.calendar.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.xiaolin.R;
import com.xiaolin.calendar.common.CalendarAdapter;
import com.xiaolin.calendar.common.CalendarItemBean;
import com.xiaolin.calendar.common.CalendarUtil;
import com.xiaolin.utils.DebugUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 日历具体item的视图
 */

public class CalendarView extends ViewGroup {

    private static final String TAG = "CalendarView";
    //    private static final String TAG = "SJY";

    private int selectPostion = -1;

    private CalendarAdapter adapter;
    private List<CalendarItemBean> listBean;
    private OnItemClickListener onItemClickListener;
    private HashMap<Integer, String> stateMap = new HashMap<>();//利用map存储考勤状态

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
        isFisrtClick = true;
        if (adapter == null) {
            throw new RuntimeException("adapter is null,please setadapter");
        }

        for (int i = 0; i < listBean.size(); i++) {
            CalendarItemBean bean = listBean.get(i);
            DebugUtil.d(TAG, "setItem()--bean.toString()=" + bean.toString());
            View view = getChildAt(i);
            View chidView = adapter.getView(view, this, bean);

            if (view == null || view != chidView) {
                addViewInLayout(chidView, i, chidView.getLayoutParams(), true);
            }

            //今天状态存储
            if (isToday && selectPostion == -1) {
                int[] date = CalendarUtil.getYearMonthDay(new Date());
                if (bean.year == date[0] && bean.moth == date[1]) {
                    if (bean.day == date[2]) {
                        selectPostion = i;
                        stateMap.put(i, bean.DayState);
                        chidView.setBackground(ContextCompat.getDrawable(chidView.getContext(), R.drawable.item_calendar_today_bg));//设置无色
                    }
                    DebugUtil.d(TAG, "isToday=" + isToday + "--selectPostion=" + i);
                }
            } else {
                if (selectPostion == -1 && bean.day == 1) {
                    DebugUtil.d(TAG, "--selectPostion=" + i);
                    selectPostion = i;
                    chidView.setBackground(ContextCompat.getDrawable(chidView.getContext(), R.drawable.item_calendar_today_bg));
                }
            }

            //迟到早退状态存储
            if (bean.DayState != null) {
                DebugUtil.d(TAG, "bean.DayState:" + bean.DayState);
                if (bean.DayState.contains("迟到") || bean.DayState.contains("早退")) {
                    DebugUtil.d(TAG, "--selectPostion=迟到--早退:" + i);
                    selectPostion = i;
                    chidView.setBackground(ContextCompat.getDrawable(chidView.getContext(), R.drawable.item_calendar_late_bg));//设置早退背景色
                    stateMap.put(i, bean.DayState);
                } else if (bean.DayState.contains("缺勤")) { //缺勤状态存储
                    DebugUtil.d(TAG, "--selectPostion=缺勤:" + i);
                    selectPostion = i;
                    chidView.setBackground(ContextCompat.getDrawable(chidView.getContext(), R.drawable.item_calendar_gone_bg));//设置早退背景色
                    stateMap.put(i, bean.DayState);
                } else if (bean.DayState.contains("正常")) {
                    DebugUtil.d(TAG, "--selectPostion=正常:" + i);
                    stateMap.put(i, bean.DayState);
                } else {
                }


            }


            if (bean.DayState != null) {
                DebugUtil.d(TAG, "bean.DayState:" + bean.DayState);

            }

            //满足就将该item设为选定状态
            chidView.setSelected(selectPostion == i);

            //设置监听
            setItemClick(chidView, i, bean);

            DebugUtil.d(TAG, "selectPostion=" + selectPostion);
        }
    }

    /**
     * item的点击事件(疑难点)
     * <p>
     * 具体处理 后台传值状态有：早退 迟到/缺勤/正常/"",根据这四种状态具体处理显示颜色
     *
     * @param view
     * @param potsion
     * @param bean
     */
    boolean isFisrtClick = true;

    public void setItemClick(final View view, final int potsion, final CalendarItemBean bean) {
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                DebugUtil.d(TAG, "potsion=" + potsion + "selectPostion=" + selectPostion);

                //处理 早退迟到/缺勤/正常
                if (bean.DayState != null && !TextUtils.isEmpty(bean.DayState)) {

                    //点击的item状态
                    if (bean.DayState.contains("迟到") || bean.DayState.contains("早退")) {
                        DebugUtil.d(TAG, "1--" + bean.DayState);

                        //处理selectPosition的状态
                        if (stateMap.get(selectPostion) != null) {
                            DebugUtil.d(TAG, "监听item=迟到--stateMap.get(selectPostion)非空--select处理");
                            handleSelect(view);
                        } else {
                            DebugUtil.d(TAG, "监听item=迟到--stateMap.get(selectPostion)空--select处理");
                            getChildAt(selectPostion).setSelected(false);
                        }
                        view.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.item_calendar_late_bg));//
                        getChildAt(potsion).setSelected(true);

                    } else if (bean.DayState.contains("缺勤")) {
                        DebugUtil.d(TAG, "2--" + bean.DayState);

                        //处理selectPosition的状态
                        if (stateMap.get(selectPostion) != null) {
                            DebugUtil.d(TAG, "监听item=缺勤--stateMap.get(selectPostion)非空--select处理");
                            handleSelect(view);
                        } else {
                            DebugUtil.d(TAG, "监听item=缺勤--stateMap.get(selectPostion)空--select处理");
                            getChildAt(selectPostion).setSelected(false);
                        }
                        view.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.item_calendar_gone_bg));//
                        getChildAt(potsion).setSelected(true);

                    } else {//点击item状态为 正常
                        DebugUtil.d(TAG, "3--" + bean.DayState);

                        //处理selectPosition的状态
                        if (stateMap.get(selectPostion) != null) {
                            DebugUtil.d(TAG, "监听item=正常--stateMap.get(selectPostion)非空--select处理");
                            handleSelect(view);
                        } else {
                            DebugUtil.d(TAG, "监听item=正常--stateMap.get(selectPostion)空--select处理");
                            getChildAt(selectPostion).setSelected(false);
                        }
                        view.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.item_calendar_today));//
                        getChildAt(potsion).setSelected(true);


                    }

                } else {//处理状态： ""/null
                    DebugUtil.d(TAG, "bean.DayState空=");

                    DebugUtil.d(TAG, "监听item空--stateMap.get(selectPostion)非空--select处理");
                    //处理selectPosition的状态
                    if (stateMap.get(selectPostion) != null) {
                        handleSelect(view);
                    } else {
                        DebugUtil.d(TAG, "监听item=空--stateMap.get(selectPostion)空--select处理");
                        getChildAt(selectPostion).setSelected(false);
                    }

                    view.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.item_calendar_today));//
                    getChildAt(potsion).setSelected(true);

                }

                selectPostion = potsion;

                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(view, potsion, bean);
                }
            }
        });
    }

    private void handleSelect(View view) {
        if (stateMap.get(selectPostion).contains("迟到") || stateMap.get(selectPostion).contains("早退")) {
            view.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.item_calendar_late_bg));//
            getChildAt(selectPostion).setSelected(true);
        } else if (stateMap.get(selectPostion).contains("缺勤")) {
            view.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.item_calendar_gone_bg));//
            getChildAt(selectPostion).setSelected(true);
        } else if (stateMap.get(selectPostion).contains("正常") || stateMap.get(selectPostion).contains("")) {
            getChildAt(selectPostion).setSelected(false);
        } else {

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
