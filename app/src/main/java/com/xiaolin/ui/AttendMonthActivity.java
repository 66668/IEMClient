package com.xiaolin.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaolin.R;
import com.xiaolin.bean.AttendStatusMonthBean;
import com.xiaolin.presenter.AttendPersenterImpl;
import com.xiaolin.ui.base.BaseActivity;
import com.xiaolin.ui.iview.IAttendMonthStateView;
import com.xiaolin.utils.DebugUtil;
import com.xiaolin.utils.Utils;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 考勤记录的 月份显示
 */

public class AttendMonthActivity extends BaseActivity implements IAttendMonthStateView {
    private static final String TAG = "calendar";
    //后退
    @BindView(R.id.layout_back)
    RelativeLayout layout_back;

    //标题
    @BindView(R.id.tv_title)
    TextView tv_title;

    //右
    @BindView(R.id.layout_right)
    RelativeLayout layout_right;
    @BindView(R.id.img_right)
    ImageView img_right;


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

    //变量
    private String currentYear;
    private String currentMonth;
    private String secendCurrentMonth;//
    private boolean flag = false;
    AttendPersenterImpl attendPersenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_attend_month);
        ButterKnife.bind(this);
        initMyView();
    }

    private void initMyView() {
        img_right.setImageDrawable(ContextCompat.getDrawable(AttendMonthActivity.this, R.mipmap.topbar_calendar));
        tv_title.setText("考勤记录");
        String[] currentDate = Utils.getYearMonthDayStr(new Date());
        currentYear = currentDate[0];
        currentMonth = currentDate[1];
        secendCurrentMonth = currentDate[1];
        tv_CalendarCenter.setText(currentYear);
        attendPersenter = new AttendPersenterImpl(AttendMonthActivity.this, this);
        changeState(currentMonth, secendCurrentMonth);
    }

    private void setDate(AttendStatusMonthBean bean) {
        //填写总状态
        tv_normal.setText(bean.getNormalNumber());
        tv_gone.setText(bean.getNotSignNumber());
        tv_late.setText(bean.getLateNumber());
        tv_leave.setText(bean.getEarlyNumber());

        //填写具体状态
        //迟到
        StringBuilder lateBuilder = new StringBuilder();
        for (String itemStr : bean.getLateData()) {
            lateBuilder.append(itemStr).append("\n");
        }
        tv_lateContent.setText(TextUtils.isEmpty(lateBuilder.toString()) ? "暂没有数据" : lateBuilder.toString());

        //早退
        StringBuilder leaveBuilder = new StringBuilder();
        for (String leaveItem : bean.getEarlyData()) {
            leaveBuilder.append(leaveItem).append("\n");
        }
        tv_leaveContent.setText(TextUtils.isEmpty(leaveBuilder.toString()) ? "暂没有数据" : leaveBuilder.toString());

        //缺勤
        StringBuilder goneBuilder = new StringBuilder();
        for (String gone : bean.getNotSignData()) {
            goneBuilder.append(gone).append("\n");
        }
        tv_goneContent.setText(TextUtils.isEmpty(goneBuilder.toString()) ? "暂没有数据" : goneBuilder.toString());

    }

    /**
     * @param view
     */
    @OnClick({R.id.img_lessYear, R.id.layout_right, R.id.img_addyear, R.id.layout_back, R.id.month1, R.id.month2, R.id.month3, R.id.month4, R.id.month5, R.id.month6, R.id.month7, R.id.month8, R.id.month9, R.id.month10, R.id.month11, R.id.month12,})
    public void multiClick(View view) {
        switch (view.getId()) {
            case R.id.layout_back://退出
                this.finish();
                break;
            case R.id.layout_right://详情日历
                startActivity(AttendDayActivity.class);
                break;
            case R.id.month1:
                currentMonth = "1";
                changeState(currentMonth, secendCurrentMonth);
                break;
            case R.id.month2:
                currentMonth = "2";
                changeState(currentMonth, secendCurrentMonth);
                break;
            case R.id.month3:
                currentMonth = "3";
                changeState(currentMonth, secendCurrentMonth);
                break;
            case R.id.month4:
                currentMonth = "4";
                changeState(currentMonth, secendCurrentMonth);
                break;
            case R.id.month5:
                currentMonth = "5";
                changeState(currentMonth, secendCurrentMonth);
                break;
            case R.id.month6:
                currentMonth = "6";
                changeState(currentMonth, secendCurrentMonth);
                break;
            case R.id.month7:
                currentMonth = "7";
                changeState(currentMonth, secendCurrentMonth);
                break;
            case R.id.month8:
                currentMonth = "8";
                changeState(currentMonth, secendCurrentMonth);
                break;
            case R.id.month9:
                currentMonth = "9";
                changeState(currentMonth, secendCurrentMonth);
                break;
            case R.id.month10:
                currentMonth = "10";
                changeState(currentMonth, secendCurrentMonth);
                break;
            case R.id.month11:
                currentMonth = "11";
                changeState(currentMonth, secendCurrentMonth);
                break;
            case R.id.month12:
                currentMonth = "12";
                changeState(currentMonth, secendCurrentMonth);
                break;
            case R.id.img_lessYear://切换年
                int intYear = Integer.valueOf(currentYear).intValue();
                currentYear = (intYear - 1) + "";
                tv_CalendarCenter.setText(currentYear);
                break;
            case R.id.img_addyear://切换年
                int intYears = Integer.valueOf(currentYear).intValue();
                currentYear = (intYears + 1) + "";
                tv_CalendarCenter.setText(currentYear);
                break;

        }
    }

    //状态
    private synchronized void changeState(String currentMonth, String secendCurrentMonth) {
        DebugUtil.d(TAG, "currentMonth=" + currentMonth + "--secendCurrentMonth=" + secendCurrentMonth);
        if (currentMonth.equals(secendCurrentMonth)) {
            selectChangeText(currentMonth);
        } else {
            selectChangeText(currentMonth);//修改点击的颜色
            selectRestoreText(secendCurrentMonth);//还原上一次点击的颜色
            this.secendCurrentMonth = currentMonth;
        }
        //调接口修改数据
        attendPersenter.getAttendMonthState(currentYear, currentMonth);
        DebugUtil.d(TAG, "可以调接口修改数据了！！");
    }

    //根据currentMonth找出对应的tv,并修改tv的状态
    private void selectChangeText(String currentMonth) {
        switch (currentMonth) {
            case "1":
                setChange(month1);
                break;
            case "2":
                setChange(month2);
                break;
            case "3":
                setChange(month3);
                break;
            case "4":
                setChange(month4);
                break;
            case "5":
                setChange(month5);
                break;
            case "6":
                setChange(month6);
                break;
            case "7":
                setChange(month7);
                break;
            case "8":
                setChange(month8);
                break;
            case "9":
                setChange(month9);
                break;
            case "10":
                setChange(month10);
                break;
            case "11":
                setChange(month11);
                break;
            case "12":
                setChange(month12);
                break;
        }
    }

    //根据secendCurrentMonth找出对应的tv,还原之前tv的状态
    private void selectRestoreText(String currentMonth) {
        switch (currentMonth) {
            case "1":
                setRestore(month1);
                break;
            case "2":
                setRestore(month2);
                break;
            case "3":
                setRestore(month3);
                break;
            case "4":
                setRestore(month4);
                break;
            case "5":
                setRestore(month5);
                break;
            case "6":
                setRestore(month6);
                break;
            case "7":
                setRestore(month7);
                break;
            case "8":
                setRestore(month8);
                break;
            case "9":
                setRestore(month9);
                break;
            case "10":
                setRestore(month10);
                break;
            case "11":
                setRestore(month11);
                break;
            case "12":
                setRestore(month12);
                break;
        }

    }

    //还原状态
    private void setRestore(TextView tv) {
        tv.setTextColor(ContextCompat.getColor(AttendMonthActivity.this, R.color.weektextColor));
        tv.setBackground(null);
    }

    //修改状态
    private void setChange(TextView tv) {
        tv.setTextColor(ContextCompat.getColor(AttendMonthActivity.this, R.color.white));
        tv.setBackground(ContextCompat.getDrawable(AttendMonthActivity.this, R.mipmap.bluecircle));
    }


    @Override
    public void showProgress() {
        loadingDialog.show();

    }

    @Override
    public void hideProgress() {
        loadingDialog.dismiss();
    }

    @Override
    public void postSuccessShow(AttendStatusMonthBean bean) {
        //显示数据
        setDate(bean);
    }

    @Override
    public void postFaild(String msg, Exception e) {
        DebugUtil.ToastShort(AttendMonthActivity.this, msg);
        DebugUtil.e(TAG, e.toString());

    }
}
