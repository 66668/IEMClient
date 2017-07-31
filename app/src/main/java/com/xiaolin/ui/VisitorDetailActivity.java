package com.xiaolin.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xiaolin.R;
import com.xiaolin.bean.VisitorBean;
import com.xiaolin.ui.base.BaseActivity;
import com.xiaolin.utils.GlideCircleTransform;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 访客详情接口
 */

public class VisitorDetailActivity extends BaseActivity {

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


    //变量
    VisitorBean bean;

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

    /**
     * 后退
     */
    @OnClick(R.id.layout_back)
    public void forBack(View view) {
        this.finish();
    }
}
