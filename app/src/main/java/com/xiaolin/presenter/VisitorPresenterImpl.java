package com.xiaolin.presenter;

import android.content.Context;

import com.xiaolin.app.Constants;
import com.xiaolin.bean.VisitorBean;
import com.xiaolin.model.VisitorModelImpl;
import com.xiaolin.model.imodel.IVisitorModel;
import com.xiaolin.model.listener.OnCommonListener;
import com.xiaolin.model.listener.OnVisitorListener;
import com.xiaolin.presenter.ipresenter.IVisitorPresenter;
import com.xiaolin.ui.iview.ICommonView;
import com.xiaolin.ui.iview.IVisitorView;
import com.xiaolin.utils.SPUtils;

import java.io.File;
import java.util.List;

/**
 * p层数据处理
 */

public class VisitorPresenterImpl implements IVisitorPresenter {
    private static final String TAG = "visitor";
    Context context;
    //访客记录使用
    private IVisitorModel visitorModel;
    private IVisitorView visitorView;

    //添加访客使用
    private ICommonView commonView;


    public VisitorPresenterImpl(IVisitorView iVisitorView) {
        this.visitorView = iVisitorView;
        this.visitorModel = new VisitorModelImpl();
    }

    public VisitorPresenterImpl(Context context, ICommonView iCommonView) {
        this.context = context;
        this.commonView = iCommonView;
        this.visitorModel = new VisitorModelImpl();

    }

    //IVisitorPresenter接口实现
    @Override
    public void pGetData(int pageSize, String isReceived, String maxTime, String minTime) {
        //获取缓存数据
        String storeID = SPUtils.getString(Constants.STORE_ID);//5596c761-493a-48f4-8f18-1e6191537405      SPUtils.getString(Constants.STORE_ID)
        String employeeID = SPUtils.getString(Constants.EMPLOYEE_ID);//bf4f737b-0a7a-4946-b44f-7e3c408dc623   SPUtils.getString(Constants.EMPLOYEE_ID)
        //只有第一页的或者刷新的时候才显示刷新进度条
        if (pageSize == 0) {
            visitorView.showProgress();
        }

        visitorModel.mLoadData(storeID
                , employeeID
                , isReceived
                , maxTime
                , minTime
                , new OnVisitorListener() {
                    @Override
                    public void onVisitorSuccess(List<VisitorBean> listbean) {
                        visitorView.hideProgress();
                        visitorView.addList(listbean);
                    }

                    @Override
                    public void onVisitorFailed(String msg, Exception e) {
                        visitorView.hideProgress();
                        visitorView.showFailedMsg(msg, e);
                    }
                });
    }

    /**
     * 添加访客
     *
     * @param jsonStr
     */
    @Override
    public void addVisitor(String jsonStr, File file) {

        commonView.showProgress();
        /**
         * model层提交数据
         */
        visitorModel.maddVisitor(jsonStr
                , file
                , new OnCommonListener() {
                    @Override
                    public void onSuccess(String str) {
                        commonView.hideProgress();
                        commonView.postSuccessShow(str);
                    }

                    @Override
                    public void onFailed(String msg, Exception e) {
                        commonView.hideProgress();
                        commonView.postFaild(msg, e);
                    }
                });
    }

    /**
     * 修改已接待未接待状态
     */

    @Override
    public void pIsReceived(String visitorID, String storeID) {
        commonView.showProgress();
        visitorModel.mIsArreived(visitorID
                , storeID
                , new OnCommonListener() {
                    @Override
                    public void onSuccess(String str) {
                        commonView.hideProgress();
                        commonView.postSuccessShow(str);
                    }

                    @Override
                    public void onFailed(String msg, Exception e) {
                        commonView.hideProgress();
                        commonView.postFaild(msg, e);
                    }
                });

    }


}
