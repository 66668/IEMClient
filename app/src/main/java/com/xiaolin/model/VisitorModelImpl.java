package com.xiaolin.model;

import com.xiaolin.bean.VisitorListBean;
import com.xiaolin.http.MyHttpService;
import com.xiaolin.model.imodel.IVisitorModel;
import com.xiaolin.model.listener.OnVisitorListener;
import com.xiaolin.utils.DebugUtil;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * m层，具体获取数据层
 * <p>
 * Created by sjy on 2017/7/27.
 */

public class VisitorModelImpl implements IVisitorModel {
    private static final String TAG = "visitor";

    @Override
    public void mLoadData(String storeID, String employeeID, String isReceived, String maxTime, String minTime, final OnVisitorListener listener) {
        MyHttpService.Builder.getHttpServer().loadVisitor(storeID
                , employeeID
                , isReceived
                , maxTime
                , minTime
                , "0"//获取全部数据
                , "20")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VisitorListBean>() {
                    @Override
                    public void onCompleted() {
                        DebugUtil.d(TAG, "VisitorModelImpl--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        DebugUtil.d(TAG, "VisitorModelImpl--onError");
                        listener.onVisitorFailed("获取数据异常", (Exception) e);
                    }


                    @Override
                    public void onNext(VisitorListBean visitorBeanCommonBean) {
                        DebugUtil.d(TAG, "VisitorModelImpl--onNext");
                        DebugUtil.d(TAG, visitorBeanCommonBean.toString());

                        //处理返回结果
                        if (visitorBeanCommonBean.getCode().equals("1")) {
                            //code = 1
                            listener.onVisitorSuccess(visitorBeanCommonBean.getResult());
                        } else if (visitorBeanCommonBean.getCode().equals("0")) {
                            //code = 0处理
                            listener.onVisitorFailed(visitorBeanCommonBean.getMessage(), new Exception("没有获取到数据！"));
                        } else {

                        }
                    }
                });
    }

    @Override
    public void mLoadDataDetail() {

    }
}
