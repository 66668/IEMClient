package com.xiaolin.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaolin.R;
import com.xiaolin.calendarlib.common.CalendarAdapter;
import com.xiaolin.calendarlib.common.CalendarItemBean;
import com.xiaolin.calendarlib.util.CalendarUtil;
import com.xiaolin.calendarlib.widget.CalendarDateView;
import com.xiaolin.calendarlib.widget.CalendarView;
import com.xiaolin.ui.base.BaseActivity;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 考勤记录的 日历日期具体显示
 */

public class AttendDayActivity extends BaseActivity {

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_attend_day);
        ButterKnife.bind(this);
        initMyView();
        initList();
    }

    private void initMyView() {
        tv_right.setText("");
        tv_title.setText("考勤记录");
        int[] data = CalendarUtil.getYearMonthDay(new Date());
        tv_CalendarCenter.setText(data[0] + "年" + data[1] + "月" + data[2] + "日");

        calendarDateView.setCalendarAdapter(new CalendarAdapter() {
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
            }
        });
    }

    private void initList() {
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 100;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(AttendDayActivity.this).inflate(android.R.layout.simple_list_item_1, null);
                }

                TextView textView = (TextView) convertView;
                textView.setText("position:" + position);

                return convertView;
            }
        });
    }
}
