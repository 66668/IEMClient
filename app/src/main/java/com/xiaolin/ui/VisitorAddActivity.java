package com.xiaolin.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaolin.R;
import com.xiaolin.dialog.DateChooseWheelViewDialog;
import com.xiaolin.ui.base.BaseActivity;
import com.xiaolin.ui.iview.ICommonView;
import com.xiaolin.utils.DebugUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 添加访客
 */

public class VisitorAddActivity extends BaseActivity implements ICommonView {
    //topbar
    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_right)
    TextView tv_right;

    @BindView(R.id.layout_back)
    RelativeLayout layout_back;

    //参数
    @BindView(R.id.pic_img)
    ImageView pic_img;


    @BindView(R.id.tv_name)
    EditText tv_name;

    @BindView(R.id.tv_phone)
    EditText tv_phone;

    @BindView(R.id.tv_startTime)
    TextView tv_startTime;

    @BindView(R.id.tv_endTime)
    TextView tv_endTime;

    @BindView(R.id.tv_visitorTo)
    TextView tv_visitorTo;

    @BindView(R.id.tv_purpose)
    EditText tv_purpose;

    @BindView(R.id.tv_company)
    EditText tv_company;

    @BindView(R.id.tv_remark)
    EditText tv_remark;

    @BindView(R.id.btn_visitor)
    Button btn_visitor;

    String startTime;
    String endTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_visitor);
        initMyView();

    }

    private void initMyView() {
        ButterKnife.bind(this);

        tv_title.setText(R.string.visitor_add_title);
        tv_right.setText("");
    }


    /**
     * 多控件 监听
     *
     * @param view
     */
    @OnClick({R.id.btn_visitor, R.id.layout_start, R.id.layout_end, R.id.pic_img, R.id.layout_back})
    public void multClick(View view) {
        switch (view.getId()) {
            case R.id.btn_visitor://提交
                break;
            case R.id.layout_start://开始时间
                startTime();
                break;
            case R.id.layout_end://结束时间
                endTime();
                break;
            case R.id.pic_img://拍照

                break;
            case R.id.layout_back://退出
                this.finish();
                break;

        }

    }

    //开始时间
    private void startTime() {
        DateChooseWheelViewDialog endDateChooseDialog = new DateChooseWheelViewDialog(VisitorAddActivity.this,
                new DateChooseWheelViewDialog.DateChooseInterface() {
                    @Override
                    public void getDateTime(String time, boolean longTimeChecked) {
                        endTime = time;
                        tv_startTime.setText(time);
                    }
                });
        //        endDateChooseDialog.setTimePickerGone(true);
        endDateChooseDialog.setDateDialogTitle("开始时间");
        endDateChooseDialog.showDateChooseDialog();
    }

    //结束时间
    private void endTime() {
        DateChooseWheelViewDialog endDateChooseDialog = new DateChooseWheelViewDialog(VisitorAddActivity.this,
                new DateChooseWheelViewDialog.DateChooseInterface() {
                    @Override
                    public void getDateTime(String time, boolean longTimeChecked) {
                        startTime = time;
                        tv_endTime.setText(time);
                    }
                });
        //        endDateChooseDialog.setTimePickerGone(true);
        endDateChooseDialog.setDateDialogTitle("结束时间");
        endDateChooseDialog.showDateChooseDialog();
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
    public void postSuccessShow(String str) {
        DebugUtil.ToastShort(VisitorAddActivity.this, str);

    }

    @Override
    public void postFaild(String msg, Exception e) {
        DebugUtil.ToastShort(VisitorAddActivity.this, msg);
    }
}
