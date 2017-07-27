package com.xiaolin.presenter;

import com.xiaolin.bean.VisitorBean;
import com.xiaolin.model.VisitorModelImpl;
import com.xiaolin.model.imodel.IVisitorModel;
import com.xiaolin.model.listener.OnVisitorListener;
import com.xiaolin.presenter.ipresenter.IVisitorPresenter;
import com.xiaolin.ui.iview.IVisitorView;

import java.util.List;

/**
 * p层数据处理
 */

public class VisitorPresenterImpl implements IVisitorPresenter, OnVisitorListener {
    private static final String TAG = "visitor";
    private IVisitorView visitorView;
    private IVisitorModel visitorModel;

    public VisitorPresenterImpl(IVisitorView iVisitorView) {
        this.visitorView = iVisitorView;
        this.visitorModel = new VisitorModelImpl();
    }

    //IVisitorPresenter接口实现
    @Override
    public void pGetData(String stroeID, String employeeID, String isReceived, String maxTime, String minTime) {
        visitorModel.mLoadData(stroeID, employeeID, isReceived, maxTime, minTime, this);
    }

    //OnVisitorListener接口实现
    @Override
    public void onVisitorSuccess(List<VisitorBean> listBean) {
        visitorView.hideProgress();
        visitorView.addList(listBean);

    }

    //OnVisitorListener接口实现
    @Override
    public void onVisitorFailed(String msg, Exception e) {
        visitorView.hideProgress();
        visitorView.showFailedMsg(msg, e);
    }
}
