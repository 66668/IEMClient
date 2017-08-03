package com.xiaolin.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaolin.R;
import com.xiaolin.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 考勤记录的 月份显示
 */

public class AttendMonthActivity extends BaseActivity {
    //后退
    @BindView(R.id.layout_back)
    RelativeLayout layout_back;

    //标题
    @BindView(R.id.tv_title)
    TextView tv_title;

    //右
    @BindView(R.id.tv_right)
    TextView tv_right;

    //时间显示
    @BindView(R.id.tv_CalendarCenter)
    TextView tv_CalendarCenter;

    //月份
    @BindView(R.id.month1)
    TextView month1;
    @BindView(R.id.month2)
    TextView month2;
    @BindView(R.id.month3)
    TextView month3;
    @BindView(R.id.month4)
    TextView month4;
    @BindView(R.id.month5)
    TextView month5;
    @BindView(R.id.month6)
    TextView month6;
    @BindView(R.id.month7)
    TextView month7;
    @BindView(R.id.month8)
    TextView month8;
    @BindView(R.id.month9)
    TextView month9;
    @BindView(R.id.month10)
    TextView month10;
    @BindView(R.id.month11)
    TextView month11;
    @BindView(R.id.month12)
    TextView month12;

    //正常打卡
    @BindView(R.id.tv_normal)
    TextView tv_normal;

    //迟到
    @BindView(R.id.tv_late)
    TextView tv_late;

    //早退
    @BindView(R.id.tv_leave)
    TextView tv_leave;

    //缺勤
    @BindView(R.id.tv_gone)
    TextView tv_gone;

    //迟到内容
    @BindView(R.id.tv_lateContent)
    TextView tv_lateContent;

    //早退内容
    @BindView(R.id.tv_leaveContent)
    TextView tv_leaveContent;

    //缺勤内容
    @BindView(R.id.tv_goneContent)
    TextView tv_goneContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_attend_month);
        ButterKnife.bind(AttendMonthActivity.this);
        initMyView();
    }

    private void initMyView() {
        tv_right.setText("");
        tv_title.setText("考勤记录");
    }

    @OnClick({R.id.layout_back})
    public void multiClick(View view) {
        switch (view.getId()) {
            case R.id.layout_back://退出
                this.finish();
                break;
        }
    }

}
