package com.xiaolin.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * fragment记录ID自定义基类
 * Created by sjy on 2017/7/27.
 */

public class BaseFragment extends Fragment {
    private View view;
    private int mLayout_id;//子类layout

    public BaseFragment(int mLayout_id) {
        this.mLayout_id = mLayout_id;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) {
            ButterKnife.bind(this, view);
            return view;
        }
        view = inflater.inflate(mLayout_id, container, false);
        ButterKnife.bind(this, view);
        //如果有自定义加载,添加监听
        return view;

    }


}
