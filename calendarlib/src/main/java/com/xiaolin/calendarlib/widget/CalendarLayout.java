package com.xiaolin.calendarlib.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ScrollerCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.xiaolin.calendarlib.interfaceView.CalendarTopView;
import com.xiaolin.calendarlib.listener.CalendarTopViewChangeListener;
import com.xiaolin.calendarlib.util.LogUtil;

/**
 * 日历控件的布局，添加了Event监听，方便切换日历视图，周视图。
 */

public class CalendarLayout extends FrameLayout {
    private static final String TAG = "calendar";
    //变量
    private boolean isSlide = false; //是否处于滑动中

    private int maxVelocity;//滑动最大加速度
    private int minVelocity;//滑动最小加速度
    private ScrollerCompat scrollerCompat;//滑动管理类

    private View view;
    private ViewGroup viewGroup;
    private CalendarTopView calendarTopView;


    public static final int TYPE_OPEN = 0; //展开
    public static final int TYPE_CLOSE = 1;//折叠
    public int type = TYPE_CLOSE;//默认折叠

    private int topHeight;
    private int itemHeigth;//calendarTopView
    private int maxDistance;//topHeight-itemHeigth 的差值
    private int bottomViewTopHeight;

    //event参数
    private float eventY;
    private float eventX;
    private boolean isClickBottomView = false;
    private int activityPointerId;
    private VelocityTracker velocityTracker;//滑动速度跟踪类

    private static final Interpolator Interpolator = new Interpolator() {
        @Override
        public float getInterpolation(float t) {
            t -= 1.0f;
            return t * t * t * t * t + 1.0f;
        }
    };

    public CalendarLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public CalendarLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        final ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        maxVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        minVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        scrollerCompat = ScrollerCompat.create(getContext(), Interpolator);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        final CalendarTopView calendarTopView = (CalendarTopView) getChildAt(0);
        this.calendarTopView = calendarTopView;
        this.view = (View) calendarTopView;
        this.viewGroup = (ViewGroup) getChildAt(1);

        calendarTopView.setCalendarTopViewChangeListener(new CalendarTopViewChangeListener() {
            @Override
            public void onLayoutChange(CalendarTopView topView) {
                CalendarLayout.this.requestLayout();//请求重新布局View
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        itemHeigth = calendarTopView.getItemHeight();//item的高
        topHeight = view.getMeasuredHeight();//view的高
        maxDistance = topHeight - itemHeigth;
        switch (type) {
            case TYPE_OPEN:
                bottomViewTopHeight = topHeight;
                break;
            case TYPE_CLOSE:
                bottomViewTopHeight = itemHeigth;
                break;
        }
        // 测量高
        int sizeheight = MeasureSpec.makeMeasureSpec((MeasureSpec.getSize(heightMeasureSpec) - calendarTopView.getItemHeight())
                , MeasureSpec.EXACTLY);

        viewGroup.measure(widthMeasureSpec, sizeheight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        viewGroup.offsetTopAndBottom(bottomViewTopHeight);//viewgroup的滑动方式 上下滑动
        int[] selectRect = getSelectRect();
        if (type == TYPE_CLOSE) {//初始状态若是关闭状态
            view.offsetTopAndBottom(-selectRect[1]);//可以展开
        }
    }

    /**
     * 拦截监听
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        boolean isFlag = false;
        //滑动监听
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                eventY = ev.getY();
                eventX = ev.getX();
                isClickBottomView = isClickView(viewGroup, ev);
                closeVelocityTracker();//关闭跟踪类
                activityPointerId = ev.getPointerId(0);
                int top = viewGroup.getTop();
                LogUtil.d(TAG, "ACTION_DOWN: top值：" + top);
                if (top < topHeight) {
                    type = TYPE_CLOSE;
                } else {
                    type = TYPE_OPEN;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float x = ev.getX();
                float y = ev.getY();

                float xDiff = x - eventX;
                float yDiff = y - eventY;

                if (Math.abs(yDiff) > 5 && Math.abs(yDiff) > Math.abs(xDiff)) {
                    LogUtil.d(TAG, "ACTION_MOVE:");
                    isFlag = true;
                    if (isClickBottomView) {
                        boolean isScroll = isScroll(viewGroup);
                        if (yDiff > 0) {
                            //向下
                            LogUtil.d(TAG, "ACTION_MOVE:向下");
                            if (type == TYPE_OPEN) {
                                LogUtil.d(TAG, "ACTION_MOVE:向下TYPE_OPEN");
                                return super.onInterceptTouchEvent(ev);
                            } else {
                                LogUtil.d(TAG, "ACTION_MOVE:向下TYPE_CLOSE");
                                if (isScroll) {
                                    LogUtil.d(TAG, "ACTION_MOVE:向下TYPE_CLOSE---isScroll=true");
                                    return super.onInterceptTouchEvent(ev);
                                }
                            }

                        } else {
                            //向上
                            LogUtil.d(TAG, "ACTION_MOVE:向上");
                            if (type == TYPE_CLOSE) {
                                LogUtil.d(TAG, "ACTION_MOVE:向上--TYPE_CLOSE");
                                return super.onInterceptTouchEvent(ev);
                            } else {
                                if (isScroll) {
                                    LogUtil.d(TAG, "ACTION_MOVE:向上--TYPE_CLOSE--isScroll=true");
                                    return super.onInterceptTouchEvent(ev);
                                }
                            }
                        }
                    }
                }
                eventX = x;
                eventY = y;

                break;
            case MotionEvent.ACTION_UP:

                break;
        }


        return isSlide || isFlag || super.onInterceptTouchEvent(ev);

    }

    /**
     * 事件处理
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        processTouchEvent(event);
        return true;

    }

    private void processTouchEvent(MotionEvent event) {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                if (isSlide) {
                    return;
                }
                float cy = event.getY();//最后的移动点

                int dy = (int) (cy - eventY);//滑动的距离
                if (dy == 0) {
                    return;
                }
                eventY = cy;
                moveEvent(dy);
                break;
            case MotionEvent.ACTION_UP:
                if (isSlide) {
                    closeVelocityTracker();
                    return;
                }

                //判断速度
                final int pointerId = activityPointerId;
                velocityTracker.computeCurrentVelocity(1000, maxVelocity);
                float currentV = VelocityTrackerCompat.getYVelocity(velocityTracker, pointerId);
                if (Math.abs(currentV) > 2000) {
                    if (currentV > 0) {
                        openCalendar();//展开calendar;
                    } else {
                        closeCalendar();//折叠calendar;
                    }
                    closeVelocityTracker();
                    return;
                }

                int top = viewGroup.getTop() - topHeight;
                int maxD = maxDistance;

                if (Math.abs(top) < maxD / 2) {
                    openCalendar();//展开calendar;
                } else {
                    closeCalendar();//折叠calendar;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                closeVelocityTracker();
                break;
        }
    }

    private void openCalendar() {
        startScroll(viewGroup.getTop(), topHeight);
    }

    private void closeCalendar() {
        startScroll(viewGroup.getTop(), topHeight - maxDistance);
    }

    private void startScroll(int startY, int endY) {
        float distance = endY - startY;
        float t = distance / maxDistance * 600;
        scrollerCompat.startScroll(0, 0, 0, endY - startY, (int) Math.abs(t));
        postInvalidate();//界面刷新
    }

    /**
     * 处理上下滑动
     *
     * @param dy 滑动的距离
     */
    private void moveEvent(int dy) {
        int[] selectRect = getSelectRect();
        int itemHeight = calendarTopView.getItemHeight();

        int dy1 = getAreaValue(view.getTop(), dy, -selectRect[1], 0);
        int dy2 = getAreaValue((viewGroup.getTop() - topHeight), dy, -(topHeight - itemHeight), 0);

        if (dy1 != 0) {
            ViewCompat.offsetTopAndBottom(view, dy1);//设置上下滑动
        }

        if (dy2 != 0) {
            ViewCompat.offsetTopAndBottom(viewGroup, dy2);
        }

    }

    /**
     * 计算上下滑动距离
     *
     * @param top      view的高度
     * @param dy       移动距离
     * @param minValue
     * @param maxValue
     * @return
     */
    private int getAreaValue(int top, int dy, int minValue, int maxValue) {
        if (top + dy < minValue) {
            return minValue - top;
        }
        if (top + dy > maxValue) {
            return maxValue - top;

        }
        return dy;
    }

    private boolean isScroll(ViewGroup viewgroup) {
        View firstChildView = viewgroup.getChildAt(0);
        if (firstChildView == null) {
            return false;
        }
        if (viewgroup instanceof ListView) {
            AbsListView list = (AbsListView) viewgroup;
            if (firstChildView.getTop() != 0) {
                return true;
            } else {
                if (list.getPositionForView(firstChildView) != 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isClickView(View view, MotionEvent event) {
        Rect rect = new Rect();
        view.getHitRect(rect);
        boolean isClick = rect.contains((int) event.getX(), (int) event.getY());
        LogUtil.d("SJY", "CalendarLayout--isClickView() called with: isClick = [" + isClick + "]");
        return isClick;
    }

    /**
     * @return
     */
    private int[] getSelectRect() {
        return calendarTopView.getCurrentSelectPostion();
    }

    public void closeVelocityTracker() {
        if (velocityTracker != null) {
            velocityTracker.recycle();
            velocityTracker = null;
        }
    }
}
