package com.xiaolin.presenter;

import android.content.Context;

import com.xiaolin.model.ChangePsModelImpl;
import com.xiaolin.model.imodel.IChangePsModel;
import com.xiaolin.model.listener.OnCommonListener;
import com.xiaolin.ui.iview.ICommonView;

/**
 * 修改密码 p层
 */

public class ChangePsPresenterImpl implements OnCommonListener {
    private Context context;
    private ICommonView iChangePsView;
    private IChangePsModel iChangePsModel;


    public ChangePsPresenterImpl(Context context, ICommonView iChangePsView) {
        this.context = context;
        this.iChangePsView = iChangePsView;
        iChangePsModel = new ChangePsModelImpl();

    }

    public void postChangePs(String old, String newps) {
        iChangePsView.showProgress();
        iChangePsModel.postChangePs(old, newps, this);
    }

    @Override
    public void onSuccess(String str) {
        iChangePsView.hideProgress();
        iChangePsView.postSuccessShow(str);

    }

    @Override
    public void onFailed(String msg, Exception e) {
        iChangePsView.hideProgress();
        iChangePsView.postFaild(msg,e);
    }
}
