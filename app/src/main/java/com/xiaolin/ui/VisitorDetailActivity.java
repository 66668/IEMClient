package com.xiaolin.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xiaolin.R;
import com.xiaolin.bean.VisitorBean;
import com.xiaolin.presenter.VisitorPresenterImpl;
import com.xiaolin.presenter.ipresenter.IVisitorPresenter;
import com.xiaolin.ui.base.BaseActivity;
import com.xiaolin.ui.iview.ICommonView;
import com.xiaolin.utils.DebugUtil;
import com.xiaolin.utils.GlideCircleTransform;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 访客详情接口
 * 修改状态
 */

public class VisitorDetailActivity extends BaseActivity implements ICommonView {
    private static final String TAG = "visitor";


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
    TextView tv_name;

    @BindView(R.id.tv_phone)
    TextView tv_phone;

    @BindView(R.id.tv_startTime)
    TextView tv_startTime;

    @BindView(R.id.tv_endTime)
    TextView tv_endTime;

    @BindView(R.id.tv_visitorTo)
    TextView tv_visitorTo;

    @BindView(R.id.tv_purpose)
    TextView tv_purpose;

    @BindView(R.id.tv_company)
    TextView tv_company;

    @BindView(R.id.tv_remark)
    TextView tv_remark;

    @BindView(R.id.btn_visitor)
    Button btn_visitor;


    //变量
    VisitorBean bean;
    IVisitorPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_visitor_detail);
        initMyView();
        showView();
    }

    private void initMyView() {
        ButterKnife.bind(this);
        tv_title.setText(R.string.visitor_detail_title);
        tv_right.setText("");
        bean = (VisitorBean) getIntent().getSerializableExtra("VisitorBean");
        if (bean.getIsReceived().contains("true")) {
            btn_visitor.setVisibility(View.INVISIBLE);
        } else {
            btn_visitor.setVisibility(View.VISIBLE);
        }
        presenter = new VisitorPresenterImpl(VisitorDetailActivity.this, this);

    }

    private void showView() {
        //图片加载
        Glide.with(this)
                .load(bean.getImagePath())
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .placeholder(ContextCompat.getDrawable(this, R.mipmap.default_photo))
                .error(ContextCompat.getDrawable(this, R.mipmap.default_photo))
                .crossFade()//动画效果显示
                .transform(new GlideCircleTransform(this))//自定义圆形图片
                .into(pic_img);

        tv_name.setText(bean.getVisitorName());
        tv_phone.setText(bean.getPhoneNumber());
        tv_startTime.setText(bean.getArrivalTimePlan());
        tv_endTime.setText(bean.getLeaveTimePlan());
        tv_visitorTo.setText(bean.getRespondentName());
        tv_purpose.setText(bean.getAim());
        tv_company.setText(bean.getAffilication());
        tv_remark.setText(bean.getRemark());

    }

    @OnClick(R.id.btn_visitor)
    public void setIsReceived(View view) {
        presenter.pIsReceived(bean.getVisitorID(), bean.getStoreID());
    }

    /**
     * 后退
     */
    @OnClick(R.id.layout_back)
    public void forBack(View view) {

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

    @Override
    public void postSuccessShow(String str) {
        DebugUtil.ToastShort(VisitorDetailActivity.this, "修改成功！");
    }


    @Override
    public void postFaild(String msg, Exception e) {
        DebugUtil.ToastShort(VisitorDetailActivity.this, msg);
        DebugUtil.e(TAG, e.toString());
    }
}
