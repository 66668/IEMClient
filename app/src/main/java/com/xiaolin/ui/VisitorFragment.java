package com.xiaolin.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaolin.R;
import com.xiaolin.adpter.VisitorListAdapter;
import com.xiaolin.bean.VisitorBean;
import com.xiaolin.presenter.VisitorPresenterImpl;
import com.xiaolin.presenter.ipresenter.IVisitorPresenter;
import com.xiaolin.ui.iview.IVisitorView;
import com.xiaolin.utils.DebugUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * fragment(已接待，未接待)
 */

public class VisitorFragment extends Fragment implements IVisitorView, SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = "visitor";

    static Context context;
    private List<VisitorBean> listBean;
    private IVisitorPresenter visitorPresenter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private VisitorListAdapter adapter;
    private LinearLayoutManager layoutManager;//recyclerView的管理类

    private int pageSize = 0;//记录item的个数；

    //获取数据所需参数
    String isReceived;//0-未接待/1-已接待
    String maxTime = "";
    String minTime = "";//

    public static VisitorFragment newInstance(String isReceived) {
        Bundle bundle = new Bundle();
        VisitorFragment fragment = new VisitorFragment();
        bundle.putString("isReceived", isReceived);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化presenter
        visitorPresenter = new VisitorPresenterImpl(this);
        //获取传值
        isReceived = getArguments().getString("isReceived");
        DebugUtil.d(TAG, "已接待未接待参数：" + isReceived);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycle, null);

        initMyView(view);//初始化控件

        //设置progressbar的颜色
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.common_topbar_bgcolor));
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(onItemClickListener);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(mOnscrollListener);
        onRefresh();//界面首先加载最新数据
        return view;
    }

    //初始化操作
    private void initMyView(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.layout_swipeRefresh);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        adapter = new VisitorListAdapter(getActivity());
        layoutManager = new LinearLayoutManager(getActivity());
    }

    //swipeRefreshLayout加载
    private RecyclerView.OnScrollListener mOnscrollListener = new RecyclerView.OnScrollListener() {
        int lastItem = -1;

        /**
         * 上拉加载
         */

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            adapter.isShowFooter(true);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastItem + 1 == adapter.getItemCount() && adapter.isShowFooter()) {
                DebugUtil.d(TAG, "VisitorFragment---OnScrollListener--加载更多");
                DebugUtil.d(TAG, "adapter.getItemCount()=" + adapter.getItemCount() + "---adapter.isShowFooter()=" + adapter.isShowFooter());

                //p层加载方法调用
                visitorPresenter.pGetData(pageSize, isReceived, "", minTime);//加载 max和minTime不能同时有值
            }

        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastItem = layoutManager.findLastVisibleItemPosition();
            DebugUtil.d(TAG, "VisitorFragment---OnScrollListener--lastItem=" + lastItem);
        }
    };

    //详情跳转
    private VisitorListAdapter.VisitorOnItemClickListener onItemClickListener = new VisitorListAdapter.VisitorOnItemClickListener() {
        @Override
        public void VisitorOnItemClick(View view, int position) {
            if (listBean.size() <= 0) {
                return;
            }
            //跳转至详情
            VisitorBean bean = adapter.getItem(position);
            Intent intent = new Intent(getActivity(), VisitorDetailActivity.class);
            intent.putExtra("VisitorBean", bean);

            //添加动画效果
            View transitionView = view.findViewById(R.id.img);
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(getActivity()
                            , transitionView
                            , getString(R.string.transition_visitor_img));

            //动画效果的跳转
            ActivityCompat.startActivity(getActivity(), intent, optionsCompat.toBundle());

        }
    };

    //IVisitorView接口实现
    @Override
    public void showProgress() {
        swipeRefreshLayout.setRefreshing(true);
    }

    //IVisitorView接口实现
    @Override
    public void hideProgress() {
        swipeRefreshLayout.setRefreshing(false);

    }

    //IVisitorView接口实现
    @Override
    public void addList(List<VisitorBean> list) {
        DebugUtil.d(TAG, "获取数据size=" + list.size());
        splitTime(list);//拆选出
        adapter.isShowFooter(true);
        if (listBean == null) {
            listBean = new ArrayList<>();
        }
        listBean.addAll(list);//拼接数据
        if (pageSize == 0) {
            //            adapter.setListBean();
            adapter.setListBean(listBean);
            DebugUtil.d(TAG, "刷新数据显示:" + listBean.size());
        } else {
            DebugUtil.d(TAG, "--------------pageSize>0-------------");
            //如果没有跟多数据，隐藏footer布局
            if (list == null || list.size() == 0) {
                DebugUtil.d(TAG, "--------------没有跟多数据，隐藏footer布局-------------");
                adapter.isShowFooter(false);
            }
            adapter.notifyDataSetChanged();//刷新adapter
        }
        adapter.isShowFooter(false);
        pageSize += list.size();//如果获取的数据不足20条
        DebugUtil.d(TAG, "addList:pageSize=" + pageSize);
    }

    private void splitTime(List<VisitorBean> list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        if (isReceived.equals("0")) {//未接待用参数iLastUpdateTime
            minTime = list.get(list.size() - 1).getiLastUpdateTime();
            Log.d(TAG, "0--splitTime:minTime= " + minTime);
        } else {
            minTime = list.get(list.size() - 1).getArrivalTimePlan();
            Log.d(TAG, "1--splitTime:minTime= " + minTime);

        }

    }

    //IVisitorView接口实现
    @Override
    public void showFailedMsg(String msg, Exception e) {
        //隐藏加载
        if (pageSize >= 0) {
            adapter.isShowFooter(false);
            adapter.notifyDataSetChanged();
        }
        View view = getActivity() == null ? recyclerView.getRootView() : getActivity().findViewById(R.id.layout_visitor);
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();//底部提示
        DebugUtil.e(TAG, "VisitorFragment--异常：" + e.toString());
    }


    //SwipeRefreshLayout刷新接口
    @Override
    public void onRefresh() {
        //清空设置
        pageSize = 0;
        if (listBean != null) {
            listBean.clear();
        }
        maxTime = "";
        minTime = "";
        DebugUtil.d(TAG, "清空--onRefresh");

        //p层获取数据
        visitorPresenter.pGetData(pageSize, isReceived, maxTime, minTime);
    }
}
