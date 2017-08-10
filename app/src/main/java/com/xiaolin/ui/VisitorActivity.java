package com.xiaolin.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaolin.R;
import com.xiaolin.adpter.VisitorFragmentPaperAdapter;
import com.xiaolin.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 访客记录
 * 使用tablayout+viewPaper的fragment绑定，控制布局切换
 * <p>
 * Created by sjy on 2017/7/27.
 */

public class VisitorActivity extends BaseActivity {

    @BindView(R.id.visitor_paper)
    ViewPager viewPager;

    @BindView(R.id.visitor_tab)
    TabLayout tabLayout;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    //添加
    @BindView(R.id.layout_right)
    RelativeLayout layout_Right;

    //后退
    @BindView(R.id.layout_back)
    RelativeLayout layout_back;


    Fragment undoVisitorFragment;
    Fragment doneVisitorFragment;
    List<Fragment> fragemntList;
    List<String> titleList;
    private VisitorFragmentPaperAdapter adapter;
    int currentTab = 0;//默认显示第一个


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_visitor);
        ButterKnife.bind(this);
        initMyView();
    }

    private void initMyView() {
        tvTitle.setText("访客记录");

        titleList = new ArrayList<>();
        titleList.add("未接待");//0
        titleList.add("已接待");//1

        fragemntList = new ArrayList<>();
        fragemntList.add(VisitorFragment.newInstance("0"));
        fragemntList.add(VisitorFragment.newInstance("1"));

        viewPager.setOffscreenPageLimit(2);

        //初始化apater
        adapter = new VisitorFragmentPaperAdapter(getSupportFragmentManager(), titleList, fragemntList);

        //tablayout设置
        tabLayout.setTabMode(TabLayout.MODE_FIXED);//充满屏幕显示
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(1)));

        //最后关联
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(currentTab);
    }

    @OnClick(R.id.layout_back)
    public void forBack(View view) {
        this.finish();
    }

    /**
     * 添加新访客
     *
     * @param view
     */
    @OnClick(R.id.layout_right)
    public void addOne(View view) {
        startActivity(VisitorAddActivity.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        initMyView();
    }
}
