package com.xiaolin.presenter;

import android.content.Context;

import com.xiaolin.model.LocationModelImpl;
import com.xiaolin.model.imodel.ILocationModel;
import com.xiaolin.model.listener.OnCommonListener;
import com.xiaolin.presenter.ipresenter.ILoactionPreseter;
import com.xiaolin.ui.iview.ILocationView;

/**
 * Created by sjy on 2017/7/28.
 */

public class LoactionPresenterImpl implements ILoactionPreseter, OnCommonListener {
    Context context;
    ILocationView locationView;
    ILocationModel model;

    public LoactionPresenterImpl(Context context, ILocationView iLocationView) {
        this.context = context;
        this.locationView = iLocationView;
        this.model = new LocationModelImpl();
    }


    @Override
    public void pPostAttend(String currentTime, String adress, String lat, String lon) {
        locationView.showProgress();
        model.mpostData(currentTime, adress, lat, lon, this);
    }

    @Override
    public void onSuccess(String str) {
        locationView.hideProgress();
        locationView.postSuccessShow(str);
    }

    @Override
    public void onFailed(String msg, Exception e) {
        locationView.hideProgress();
        locationView.postFaild(msg, e);

    }
}
