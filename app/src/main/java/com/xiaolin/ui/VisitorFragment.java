package com.xiaolin.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.List;

/**
 * fragment
 */

public class VisitorFragment extends Fragment implements IVisitorView, SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = "visitor";

    private List<VisitorBean> Visitorlist;
    static Context context;
    private boolean isCache = false;
    private List<VisitorBean> listBean;
    private IVisitorPresenter visitorPresenter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private VisitorListAdapter adapter;
    private LinearLayoutManager layoutManager;
    String isReceived;//0-未接待/1-已接待

    public static VisitorFragment newInstance(String Type) {
        Bundle bundle = new Bundle();
        VisitorFragment fragment = new VisitorFragment();
        bundle.putString("isReceived", Type);
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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycle, null);
        initMyView(view);

        //设置progressbar的颜色
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.common_topbar_bgcolor));
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(onItemClickListener);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    //初始化操作
    private void initMyView(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.layout_swipeRefresh);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        adapter = new VisitorListAdapter(getActivity().getApplicationContext());
        layoutManager = new LinearLayoutManager(getActivity());
    }

    //详情跳转
    private VisitorListAdapter.VisitorOnItemClickListener onItemClickListener = new VisitorListAdapter.VisitorOnItemClickListener() {
        @Override
        public void VisitorOnItemClick(View view, int position) {
            if (listBean.size() <= 0) {
                return;
            }
            //跳转至详情
            VisitorBean bean = adapter.getItem(position);
            DebugUtil.ToastShort(getActivity(), "详情跳转操作");
        }
    };

    //IVisitorView接口实现
    @Override
    public void showProgress() {

    }

    //IVisitorView接口实现
    @Override
    public void hideProgress() {

    }

    //IVisitorView接口实现
    @Override
    public void addList(List<VisitorBean> list) {

    }

    //IVisitorView接口实现
    @Override
    public void showFailedMsg(String msg, Exception e) {

    }


    //SwipeRefreshLayout刷新接口
    @Override
    public void onRefresh() {


    }
}
