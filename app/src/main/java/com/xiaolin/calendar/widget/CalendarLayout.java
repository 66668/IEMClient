package com.xiaolin.calendar.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ScrollerCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.xiaolin.calendar.interfaceView.ICalendarDateView;
import com.xiaolin.calendar.listener.CalendarTopViewChangeListener;
import com.xiaolin.utils.LogUtil;

/**
 * 日历的控制布局layout
 * 负责控制日历的展开和收缩处理
 * <p>
 * 自定义ViewGroup，必须实现onLayout方法
 */

public class CalendarLayout extends FrameLayout {

    private static final String TAG = "CalendarLayout";

    private View view1;
    private ViewGroup viewGroup;
    private ICalendarDateView mTopView;
    //展开
    public static final int TYPE_OPEN = 0;
    //折叠
    public static final int TYPE_FOLD = 1;
    public int type = TYPE_FOLD;

    //是否处于滑动中
    private boolean isSilde = false;

    private int topHeigth;
    private int itemHeight;
    private int bottomViewTopHeight;
    private int maxDistance;

    private ScrollerCompat mScroller;
    private float mMaxVelocity;
    private float mMinVelocity;
    private int activitPotionerId;

    float oy, ox;
    boolean isClickBtottomView = false;

    private static final Interpolator sInterpolator = new Interpolator() {
        @Override
        public float getInterpolation(float t) {
            t -= 1.0f;
            return t * t * t * t * t + 1.0f;
        }
    };

    //一般代码调用
    public CalendarLayout(Context context) {
        super(context);
        init();
    }

    //xml创建时，就调用该方法
    public CalendarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LogUtil.d(TAG, "init（）");
        //滑轮控件
        final ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        //用于设置最小加速率和最大速率
        mMaxVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        mMinVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        mScroller = ScrollerCompat.create(getContext(), sInterpolator);
    }

    //当系统解析完View之后调用onFinishInflate方法
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        LogUtil.d(TAG, "onFinishInflate（）--getChildCount=" + getChildCount());
        final ICalendarDateView calendarDateView = (ICalendarDateView) getChildAt(0);

        mTopView = calendarDateView;
        view1 = (View) calendarDateView;
        viewGroup = (ViewGroup) getChildAt(1);

        //日历视图切换监听，由CalendarDateView回调，CalendarLayout具体处理
        mTopView.setCalendarDateViewChangeListener(new CalendarTopViewChangeListener() {
            @Override
            public void onLayoutChange(ICalendarDateView topView) {
                LogUtil.d(TAG, "onFinishInflate--日历视图切换，重新布局");
                CalendarLayout.this.requestLayout();
            }
        });
    }

    /**
     * 计算所有子View的大小
     * getWidth()和getMeasuredWidth()的区别:
     * <p>
     * getMeasuredWidth()：只要一执行完 setMeasuredDimension() 方法，就有值了，并且不再改变
     * getWidth()：必须执行完 onMeasure() 才有值，可能发生改变
     * <p>
     * 如果 onLayout 没有对子 View 实际显示的宽高进行修改，
     * 那么 getWidth() 的值 == getMeasuredWidth() 的值
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);//match_parent用super

        itemHeight = mTopView.getItemHeight();
        topHeigth = view1.getMeasuredHeight();
        maxDistance = topHeigth - itemHeight;

        LogUtil.d(TAG, "onMeasure--itemHeight=" + itemHeight + "--topHeigth=" + topHeigth + "--maxDistance" + maxDistance);
        switch (type) {
            case TYPE_FOLD:
                bottomViewTopHeight = itemHeight;
                LogUtil.d(TAG, "onMeasure--TYPE_FOLD--bottomViewTopHeight=" + bottomViewTopHeight);
                break;
            case TYPE_OPEN:
                bottomViewTopHeight = topHeigth;
                LogUtil.d(TAG, "onMeasure--TYPE_OPEN--bottomViewTopHeight=" + bottomViewTopHeight);
                break;
        }
        //使用子view自身的测量方法
        viewGroup.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec) - mTopView.getItemHeight(), MeasureSpec.EXACTLY));
    }

    //当ViewGroup分配所有的子View的大小和位置时触发
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        LogUtil.d(TAG, "onLayout（）");
        viewGroup.offsetTopAndBottom(bottomViewTopHeight);
        int[] selectRect = getSelectRect();
        if (type == TYPE_FOLD) {
            LogUtil.d(TAG, "onLayout--selectRect[]=" + selectRect.length + "--" + selectRect[0] + "--" + selectRect[1]);
            view1.offsetTopAndBottom(-selectRect[1]);
        }
    }


    //拦截器，拦截手势处理事件分发
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        LogUtil.d(TAG, "onInterceptTouchEvent");
        boolean isflag = false;

        //上下运动进行拦截
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:

                oy = ev.getY();
                ox = ev.getX();

                isClickBtottomView = isClickView(viewGroup, ev);
                cancel();
                activitPotionerId = ev.getPointerId(0);

                int top = viewGroup.getTop();

                if (top < topHeigth) {
                    type = TYPE_FOLD;
                } else {
                    type = TYPE_OPEN;
                }
                break;
            case MotionEvent.ACTION_MOVE:

                float y = ev.getY();
                float x = ev.getX();

                float xdiff = x - ox;
                float ydiff = y - oy;

                if (Math.abs(ydiff) > 5 && Math.abs(ydiff) > Math.abs(xdiff)) {
                    isflag = true;

                    if (isClickBtottomView) {
                        boolean isScroll = isScroll(viewGroup);
                        if (ydiff > 0) {
                            //向下
                            if (type == TYPE_OPEN) {
                                return super.onInterceptTouchEvent(ev);
                            } else {
                                if (isScroll) {
                                    return super.onInterceptTouchEvent(ev);
                                }

                            }
                        } else {
                            //向上
                            if (type == TYPE_FOLD) {
                                return super.onInterceptTouchEvent(ev);
                            } else {
                                if (isScroll) {
                                    return super.onInterceptTouchEvent(ev);
                                }
                            }
                        }

                    }
                }
                ox = x;
                oy = y;
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return isSilde || isflag || super.onInterceptTouchEvent(ev);
    }

    //触屏事件：处理日历展开收缩
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.d(TAG, "onTouchEvent");
        processTouchEvent(event);
        return true;
    }

    int oldY = 0;

    /**
     * 根据滑动状况修改视图
     */
    @Override
    public void computeScroll() {
        super.computeScroll();

        LogUtil.d(TAG, "computeScroll（）");

        bottomViewTopHeight = viewGroup.getTop();
        if (mScroller.computeScrollOffset()) {
            isSilde = true;
            int cy = mScroller.getCurrY();
            int dy = cy - oldY;
            moveEvent(dy);
            oldY = cy;
            postInvalidate();//View树重绘
        } else {
            oldY = 0;
            isSilde = false;
        }
    }

    //VelocityTracker类的使用全在下边方法中标记
    private VelocityTracker mVelocityTracker;


    public void processTouchEvent(MotionEvent event) {
        /**
         * 1 首先获得VelocityTracker的实例
         * obtain()的方法介绍
         * Retrieve a new VelocityTracker object to watch the velocity of a motion.
         * Be sure to call recycle() when done. You should generally only maintain
         * an active object while tracking a movement, so that the VelocityTracker
         * can be re-used elsewhere.
         * 翻译：
         * 得到一个速率追踪者对象去检测一个事件的速率。确认在完成的时候调用recycle()方法。
         * 一般情况下，你只要维持一个活动的速率追踪者对象去追踪一个事件，那么，这个速率追踪者
         * 可以在别的地方重复使用。
         */
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        /**
         * 2 事件event,将事件加入到VelocityTracker类实例中
         *
         * addMovement (MotionEvent event)方法介绍
         * Add a user's movement to the tracker. You should call this for the initial
         * ACTION_DOWN, the following ACTION_MOVE events that you receive,
         *  and the final ACTION_UP. You can, however, call this for whichever events
         *  you desire.
         *  翻译：向速率追踪者中加入一个用户的移动事件，你应该最先在ACTION_DOWN调用这个方法，
         *  然后在你接受的ACTION_MOVE，最后是ACTION_UP。你可以为任何一个你愿意的事件调用该方法
         */
        mVelocityTracker.addMovement(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                if (isSilde) {
                    return;
                }
                float cy = event.getY();
                int dy = (int) (cy - oy);

                if (dy == 0) {
                    return;
                }
                oy = cy;
                moveEvent(dy);

                break;
            case MotionEvent.ACTION_UP:

                if (isSilde) {
                    LogUtil.d(TAG, "ACTION_UP--isSilde=true--cancel()");
                    cancel();
                    return;
                }

                //判断速度
                /**
                 * 3 MotionEvent.ACTION_UP的时候，调用下面的方法
                 *
                 * public void computeCurrentVelocity (int units, float maxVelocity)方法介绍：
                 * Compute the current velocity based on the points that have been
                 * collected. Only call this when you actually want to retrieve velocity
                 * information, as it is relatively expensive. You can then retrieve the
                 * velocity with {@link #getXVelocity()} and {@link #getYVelocity()}.
                 *
                 * @param units
                 *            The units you would like the velocity in. A value of 1
                 *            provides pixels per millisecond, 1000 provides pixels per
                 *            second, etc.
                 * @param maxVelocity
                 *            The maximum velocity that can be computed by this method. This
                 *            value must be declared in the same unit as the units
                 *            parameter. This value must be positive.
                 * 翻译：基于你所收集到的点计算当前的速率。       当你确定要获得速率信息的时候，在调用该方法，
                 * 因为使用它需要消耗很大的性能。然后，你可以通过getXVelocity()和getYVelocity()获得横向和竖向的速率。
                 *
                 * 参数：units  你想要指定的得到的速度单位，如果值为1，代表1毫秒运动了多少像素。如果值为1000，代表
                 * 1秒内运动了多少像素。
                 *
                 * 参数：maxVelocity  该方法所能得到的最大速度，这个速度必须和你指定的units使用同样的单位，而且
                 * 必须是整数。（也就是，你指定一个速度的最大值，如果计算超过这个最大值，就使用这个最大值，否则，使用计算的的结果）
                 *
                 * public void computeCurrentVelocity (int units)方法介绍
                 * 这个方法与上面的方法没什么差别，就是在maxVelocity上，他会自动使用Float.MAX_VALUE常量
                 */
                mVelocityTracker.computeCurrentVelocity(1000, mMaxVelocity);
                final int pointerId = activitPotionerId;

                //然后调用getXVelocity ()、getXVelocity (int id)、getYVelocity ()、getYVelocity (int id)得到速率
                /**
                 *  4 调用这几个方法之前，必须确定你之前调用了computeCurrentVelocity方法。
                 * 参数 id   代表返回指定触点的速率
                 */
                float crrentV = VelocityTrackerCompat.getYVelocity(mVelocityTracker, pointerId);

                if (Math.abs(crrentV) > 2000) {
                    if (crrentV > 0) {
                        open();
                    } else {
                        flod();
                    }
                    cancel();
                    return;
                }

                int top = viewGroup.getTop() - topHeigth;
                int maxd = maxDistance;

                if (Math.abs(top) < maxd / 2) {
                    open();
                } else {
                    flod();
                }
                cancel();

                break;
            case MotionEvent.ACTION_CANCEL:
                cancel();
                break;
        }
    }

    public void open() {
        LogUtil.d(TAG, "展开日历");
        startScroll(viewGroup.getTop(), topHeigth);
    }

    public void flod() {
        LogUtil.d(TAG, "收缩日历");
        startScroll(viewGroup.getTop(), topHeigth - maxDistance);
    }

    private void startScroll(int starty, int endY) {

        float distance = endY - starty;
        float t = distance / maxDistance * 600;

        mScroller.startScroll(0, 0, 0, endY - starty, (int) Math.abs(t));
        postInvalidate();
    }

    private void moveEvent(int dy) {

        int[] selectRect = getSelectRect();
        int itemHeight = mTopView.getItemHeight();

        int dy1 = getAreaValue(view1.getTop(), dy, -selectRect[1], 0);
        int dy2 = getAreaValue(viewGroup.getTop() - topHeigth, dy, -(topHeigth - itemHeight), 0);

        if (dy1 != 0) {
            ViewCompat.offsetTopAndBottom(view1, dy1);
        }

        if (dy2 != 0) {
            ViewCompat.offsetTopAndBottom(viewGroup, dy2);
        }

    }

    private int getAreaValue(int top, int dy, int minValue, int maxValue) {

        if (top + dy < minValue) {
            return minValue - top;
        }

        if (top + dy > maxValue) {
            return maxValue - top;
        }
        return dy;
    }

    private boolean isScroll(ViewGroup viewGroup) {
        View fistChildView = viewGroup.getChildAt(0);
        if (fistChildView == null) {
            return false;
        }

        if (viewGroup instanceof ListView) {
            AbsListView list = (AbsListView) viewGroup;
            if (fistChildView.getTop() != 0) {
                return true;
            } else {
                if (list.getPositionForView(fistChildView) != 0) {
                    return true;
                }
            }
        }

        return false;
    }


    public boolean isClickView(View view, MotionEvent ev) {
        Rect rect = new Rect();
        view.getHitRect(rect);
        boolean isClick = rect.contains((int) ev.getX(), (int) ev.getY());
        Log.d(TAG, "isClickView() called with: isClick = [" + isClick + "]");
        return isClick;
    }

    private int[] getSelectRect() {
        return mTopView.getCurrentSelectPosition();
    }

    public void cancel() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

}
