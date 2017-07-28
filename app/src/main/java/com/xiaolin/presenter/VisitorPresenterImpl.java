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
    public void pGetData(int pageSize, String isReceived, String maxTime, String minTime) {
        //获取缓存数据
        String storeID = "5596c761-493a-48f4-8f18-1e6191537405";//5596c761-493a-48f4-8f18-1e6191537405      SPUtils.getString(Constants.STORE_ID)
        String employeeID = "bf4f737b-0a7a-4946-b44f-7e3c408dc623";//bf4f737b-0a7a-4946-b44f-7e3c408dc623   SPUtils.getString(Constants.EMPLOYEE_ID)
        //只有第一页的或者刷新的时候才显示刷新进度条
        if (pageSize == 0) {
            visitorView.showProgress();
        }
        visitorModel.mLoadData(storeID, employeeID, isReceived, maxTime, minTime, this);
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
