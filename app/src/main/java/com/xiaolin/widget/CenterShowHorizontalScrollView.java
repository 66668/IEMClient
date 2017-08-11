package com.xiaolin.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.xiaolin.R;

/**
 * 横滑 点击居中
 */

public class CenterShowHorizontalScrollView extends HorizontalScrollView {
    private LinearLayout mShowLinear;
    int scrollViewWidth;

    public CenterShowHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        scrollViewWidth = this.getWidth();
        mShowLinear = new LinearLayout(context);
        mShowLinear.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams params = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        mShowLinear.setGravity(Gravity.CENTER_VERTICAL);
        this.addView(mShowLinear, params);
    }

    public void onClicked(View v) {
        if (v.getTag(R.id.item_position) != null) {
            int position = (Integer) v.getTag(R.id.item_position);
            Log.d("month", "-------------------------------onClicked: " + position);
            View itemView = mShowLinear.getChildAt(position);
            Log.d("month", "-------------------------------onClicked:itemView==null " + (itemView == null));
            int itemWidth = itemView.getWidth();
            scrollViewWidth = this.getWidth();
            Log.d("month", "-------------------------------onClicked:itemWidth=" + itemWidth + "scrollViewWidth=" + scrollViewWidth);
            smoothScrollTo(itemView.getLeft() - (scrollViewWidth / 2 - itemWidth / 2), 0);
        }
    }

    public LinearLayout getLinear() {
        return mShowLinear;
    }

    public void addItemView(View itemView, int position) {
        itemView.setTag(R.id.item_position, position);
        mShowLinear.addView(itemView);
    }


}