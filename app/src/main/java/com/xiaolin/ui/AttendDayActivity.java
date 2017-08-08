package com.xiaolin.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaolin.R;
import com.xiaolin.adpter.AttendDayAdapter;
import com.xiaolin.bean.AttendDaysOFMonthBean;
import com.xiaolin.bean.AttendDaysOFMonthStateBean;
import com.xiaolin.calendar.common.CalendarAdapter;
import com.xiaolin.calendar.common.CalendarItemBean;
import com.xiaolin.calendar.common.CalendarUtil;
import com.xiaolin.calendar.widget.CalendarDateView;
import com.xiaolin.calendar.widget.CalendarLayout;
import com.xiaolin.calendar.widget.CalendarView;
import com.xiaolin.presenter.AttendPersenterImpl;
import com.xiaolin.ui.base.BaseActivity;
import com.xiaolin.ui.iview.IAttendDayView;
import com.xiaolin.utils.DebugUtil;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 考勤记录的 日历日期具体显示
 */

public class AttendDayActivity extends BaseActivity implements IAttendDayView {

    private static final String TAG = "calendar";

    @BindView(R.id.layout_back)
    RelativeLayout layout_back;

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_right)
    TextView tv_right;

    @BindView(R.id.tv_CalendarCenter)
    TextView tv_CalendarCenter;

    @BindView(R.id.calendarDateView)
    CalendarDateView calendarDateView;

    @BindView(R.id.list)
    ListView listView;

    AttendPersenterImpl attendPersenter;
    CalendarLayout calendarLayout;
    AttendDayAdapter adapter;
    ArrayList<AttendDaysOFMonthBean> listBean;
    int[] currentDate;
    String currentYear;
    String currentMonth;
    String currentDay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_attend_day);
        ButterKnife.bind(this);
        initMyView();
        initCalendar();

        getDate();
        getDayDate();
    }

    private void initMyView() {
        tv_right.setText("");
        tv_title.setText("考勤记录");

        calendarLayout = (CalendarLayout) findViewById(R.id.calendarLayout);
        listView = (ListView) findViewById(R.id.list);

        attendPersenter = new AttendPersenterImpl(AttendDayActivity.this, this);
        currentDate = CalendarUtil.getYearMonthDay(new Date());
        currentYear = currentDate[0] + "";
        currentMonth = currentDate[1] + "";
        currentDay = currentDate[2] + "";
        listBean = new ArrayList<>();
    }

    private void initCalendar() {

        tv_CalendarCenter.setText(currentYear + "年" + currentMonth + "月" + currentDay + "日");

        calendarDateView.setAdapter(new CalendarAdapter() {
            @Override
            public View getView(View convertView, ViewGroup parentView, CalendarItemBean bean) {

                if (convertView == null) {
                    convertView = LayoutInflater.from(parentView.getContext()).inflate(R.layout.item_calendar, null);
                }

                //TextView chinaText = (TextView) convertView.findViewById(R.id.chinaText);
                TextView text = (TextView) convertView.findViewById(R.id.text);
                text.setText("" + bean.day);
                if (bean.mothFlag != 0) {
                    //非本月
                    text.setTextColor(ContextCompat.getColor(AttendDayActivity.this, R.color.item_otherMonth));
                } else {
                    //本月
                    text.setTextColor(ContextCompat.getColor(AttendDayActivity.this, R.color.item_thisMonth));
                }
                // chinaText.setText(bean.chinaDay);//农历设置

                return convertView;
            }
        });

        calendarDateView.setOnItemClickListener(new CalendarView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion, CalendarItemBean bean) {
                tv_CalendarCenter.setText(bean.year + "年" + bean.moth + "月" + bean.day + "日");
                currentYear = bean.year + "";
                currentMonth = bean.moth + "";
                currentDay = bean.day + "";
                getDayDate();
                getDate();
            }
        });
    }

    /**
     * 获取月记录的状态记录
     */
    private void getDate() {
        DebugUtil.d(TAG, "currentYear=" + currentYear + "--currentMonth=" + currentMonth);
        attendPersenter.getAttendStateList(currentYear, currentMonth);
    }

    /**
     * 获取日记录数据
     */
    private void getDayDate() {
        attendPersenter.getAttendDayDetail(currentYear, currentMonth, currentDay);

    }

    @OnClick(R.id.layout_back)
    public void BackClick(View view) {
        this.finish();
    }

    @Override
    public void showProgress() {
        loadingDialog.show();

    }

    @Override
    public void hideProgress() {
        loadingDialog.dismiss();
    }

    //日记录使用 listView显示
    @Override
    public void postSuccessShow(AttendDaysOFMonthBean bean) {
        //
        listBean.clear();
        listBean.add(bean);
        adapter = new AttendDayAdapter(AttendDayActivity.this, listBean);
        listView.setAdapter(adapter);
    }

    //月记录使用
    @Override
    public void postSuccessUse(ArrayList<AttendDaysOFMonthStateBean> list) {
        //调用下边方法，更新日历视图
        calendarDateView.setSourseDate(list);
    }

    @Override
    public void postFaild(String msg, Exception e) {
        DebugUtil.ToastShort(AttendDayActivity.this, msg);
        DebugUtil.e(TAG, e.toString());
    }
}
