package com.xiaolin.ui;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaolin.R;
import com.xiaolin.bean.AttendDaysOFMonthStateBean;
import com.xiaolin.bean.AttendStatusMonthBean;
import com.xiaolin.calendar.common.CalendarCache;
import com.xiaolin.presenter.AttendPersenterImpl;
import com.xiaolin.ui.base.BaseActivity;
import com.xiaolin.ui.iview.IAttendMonthStateView;
import com.xiaolin.utils.DebugUtil;
import com.xiaolin.utils.Utils;
import com.xiaolin.widget.CenterShowHorizontalScrollView;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 考勤记录的 月份显示
 */

public class AttendMonthActivity extends BaseActivity implements IAttendMonthStateView {
    private static final String TAG = "month";
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

    CenterShowHorizontalScrollView mScrollView;

    //变量
    private int currentYear;
    private int currentMonth = -1;
    private boolean centerFlag = true;//是否居中,初始化只用一次，
    AttendPersenterImpl attendPersenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_attend_month);
        ButterKnife.bind(this);
        initMyView();
        initMonthCalendar();//月份横向滚动

        getCacheDate();
    }

    private final int middlePadding = 20;


    private void initMyView() {
        img_right.setImageDrawable(ContextCompat.getDrawable(AttendMonthActivity.this, R.mipmap.topbar_calendar));
        tv_title.setText("考勤记录");
        int[] currentDate = Utils.getYearMonthDayInt(new Date());

        currentYear = currentDate[0];
        currentMonth = currentDate[1] - 1;

        tv_CalendarCenter.setText(currentYear + "");
        attendPersenter = new AttendPersenterImpl(AttendMonthActivity.this, this);
    }

    View currentView;

    private void initMonthCalendar() {
        //自定义的月份
        mScrollView = (CenterShowHorizontalScrollView) findViewById(R.id.scrollView);
        mScrollView.getLinear().removeAllViews();
        for (int i = 0; i < 12; i++) {
            final View titleItem = View.inflate(AttendMonthActivity.this, R.layout.item_month, null);
            mScrollView.addItemView(titleItem, i);

            LinearLayout.LayoutParams titleParams = (LinearLayout.LayoutParams) titleItem.getLayoutParams();
            titleParams.leftMargin = middlePadding / 2;
            titleParams.rightMargin = middlePadding / 2;

            final TextView textView = (TextView) titleItem.findViewById(R.id.text);
            textView.setText((i + 1) + "月");

            if (currentMonth == i) {
                DebugUtil.d(TAG, "currentMonth=" + currentMonth);
                textView.setBackground(ContextCompat.getDrawable(AttendMonthActivity.this, R.mipmap.bluecircle));
                textView.setTextColor(ContextCompat.getColor(AttendMonthActivity.this, R.color.white));
                currentView = titleItem;

                //
                //调接口修改数据
                attendPersenter.getAttendMonthState(currentYear + "", (currentMonth + 1) + "");
            }

            final int j = i;
            titleItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DebugUtil.d(TAG, "item监听" + (j + 1));
                    mScrollView.onClicked(v);
                    currentMonth = j;
                    initMonthCalendar();//重新布局,将点击的item添加颜色，不影响布局
                }
            });
        }
        //做延迟处理，居中
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(200);//延迟0.2s
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sendMessage(1);
            }
        }).start();

    }

    @Override
    protected void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (msg.what == 1) {
            //居中
            if (centerFlag) {
                DebugUtil.d(TAG, "居中");
                //            currentView.setTag(R.id.item_position, currentMonth);
                mScrollView.onClicked(currentView);
                centerFlag = false;
            }
        }

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
    @OnClick({R.id.img_lessYear, R.id.layout_right, R.id.img_addyear, R.id.layout_back})
    public void multiClick(View view) {
        switch (view.getId()) {
            case R.id.layout_back://退出
                this.finish();
                break;
            case R.id.layout_right://详情日历
                startActivity(AttendDayActivity.class);
                break;
            case R.id.img_lessYear://切换年

                currentYear -= 1;
                tv_CalendarCenter.setText(currentYear + "");
                break;
            case R.id.img_addyear://切换年
                currentYear += 1;
                tv_CalendarCenter.setText(currentYear + "");
                break;

        }
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

    String month;
    String year;

    void getCacheDate() {
        String[] currentDate = Utils.getYearMonthDayStr(new Date());
        year = currentDate[0];
        month = currentDate[1];

        //为缓存三个月数据，设置key值 year+" "+month
        key2 = year + " " + month;
        if (month.equals("1")) {
            key1 = (Integer.parseInt(year) - 1) + " 12";
            key3 = year + " 2";
        } else if (month.equals("12")) {
            key1 = year + " 11";
            key3 = (Integer.parseInt(year) + 1) + " 1";
        } else {
            key1 = year + " " + (Integer.parseInt(month) - 1);
            key3 = year + " " + (Integer.parseInt(month) + 1);
        }
        attendPersenter.getAttendMonthOfThreeState(year, month);
    }

    /**
     * 为下个界面缓存三个月的数据
     *
     * @param listStateBean
     */
    ArrayList<AttendDaysOFMonthStateBean> list1 = new ArrayList<>();
    ArrayList<AttendDaysOFMonthStateBean> list2 = new ArrayList<>();
    ArrayList<AttendDaysOFMonthStateBean> list3 = new ArrayList<>();
    String key1, key2, key3;

    @Override
    public void cacheDateSuccess(ArrayList<AttendDaysOFMonthStateBean> listStateBean) {

        int size = listStateBean.size();
        for (int i = 0; i < size; i++) {
            //在当前月是1月 12月时特殊情况处理
            if (month.equals("1")) {
                if (listStateBean.get(i).getDMonth().equals("12")) {
                    list1.add(listStateBean.get(i));
                }
                if (listStateBean.get(i).getDMonth().equals(month)) {
                    list2.add(listStateBean.get(i));
                }
                if (listStateBean.get(i).getDMonth().equals("2")) {
                    list3.add(listStateBean.get(i));
                }
            } else if (month.equals("12")) {
                if (listStateBean.get(i).getDMonth().equals("11")) {
                    list1.add(listStateBean.get(i));
                }
                if (listStateBean.get(i).getDMonth().equals(month)) {
                    list2.add(listStateBean.get(i));
                }
                if (listStateBean.get(i).getDMonth().equals("1")) {
                    list3.add(listStateBean.get(i));
                }
            } else {
                if (listStateBean.get(i).getDMonth().equals(Integer.parseInt(month) - 1 + "")) {
                    list1.add(listStateBean.get(i));
                }
                if (listStateBean.get(i).getDMonth().equals(month)) {
                    list2.add(listStateBean.get(i));
                }
                if (listStateBean.get(i).getDMonth().equals(Integer.parseInt(month) + 1 + "")) {
                    list3.add(listStateBean.get(i));
                }
            }
        }
        CalendarCache.getInstance().setCacahe(key1, list1);
        CalendarCache.getInstance().setCacahe(key2, list2);
        CalendarCache.getInstance().setCacahe(key3, list3);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //退出这个界面，清空全局变量缓存
        CalendarCache.getInstance().clearAll();
    }
}
