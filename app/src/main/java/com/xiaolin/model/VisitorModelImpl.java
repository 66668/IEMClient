package com.xiaolin.model;

import com.xiaolin.bean.BaseBean;
import com.xiaolin.bean.VisitorListBean;
import com.xiaolin.http.MyHttpService;
import com.xiaolin.model.imodel.IVisitorModel;
import com.xiaolin.model.listener.OnCommonListener;
import com.xiaolin.model.listener.OnVisitorListener;
import com.xiaolin.utils.DebugUtil;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

    /**
     * 获取访客记录
     *
     * @param storeID
     * @param employeeID
     * @param isReceived
     * @param maxTime
     * @param minTime
     * @param listener
     */
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
                    public void onNext(VisitorListBean baseBean) {
                        DebugUtil.d(TAG, "VisitorModelImpl--onNext");
                        DebugUtil.d(TAG, baseBean.toString());

                        //处理返回结果
                        if (baseBean.getCode().equals("1")) {
                            //code = 1
                            listener.onVisitorSuccess(baseBean.getResult());
                        } else if (baseBean.getCode().equals("0")) {
                            //code = 0处理
                            listener.onVisitorFailed(baseBean.getMessage(), new Exception("没有获取到数据！"));
                        } else {

                        }
                    }
                });
    }

    /**
     * 添加访客
     *
     * @param jsonstr
     * @param picFile
     * @param listener
     */

    @Override
    public void maddVisitor(String jsonstr, File picFile, final OnCommonListener listener) {
        //需要对file进行封装
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), picFile);
        MultipartBody.Part part = MultipartBody.Part.createFormData("picture", picFile.getName(), requestBody);

        DebugUtil.d(TAG, "picPath=" + picFile + "--picFile.getName():" + picFile.getName() + "--jsonStr=" + jsonstr);

        MyHttpService.Builder.getHttpServer().addVisitor(jsonstr, part)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean>() {
                    @Override
                    public void onCompleted() {
                        DebugUtil.d(TAG, "maddVisitor--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        DebugUtil.e(TAG, "maddVisitor--onError:" + e.toString());
                        listener.onFailed("添加访客提交数据异常!", (Exception) e);
                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        DebugUtil.d(TAG, "maddVisitor--onNext:" + baseBean.toString());
                        //处理返回结果
                        if (baseBean.getCode().equals("1")) {
                            //code = 1
                            listener.onSuccess(baseBean.getResult());
                        } else if (baseBean.getCode().equals("0")) {
                            //code = 0处理
                            listener.onFailed(baseBean.getMessage(), new Exception("提交数据失败！"));
                        } else {

                        }
                    }
                });

    }


    @Override
    public void mIsArreived(String visitorID, String storeID, final OnCommonListener listener) {

        MyHttpService.Builder.getHttpServer().isReceived(visitorID, storeID)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean>() {
                    @Override
                    public void onCompleted() {
                        DebugUtil.d(TAG, "pIsArreived--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        DebugUtil.e(TAG, "pIsArreived--onError:" + e.toString());
                        listener.onFailed("修改接待状态数据异常!", (Exception) e);
                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        DebugUtil.d(TAG, "pIsArreived--onNext:" + baseBean.toString());
                        //处理返回结果
                        if (baseBean.getCode().equals("1")) {
                            //code = 1
                            listener.onSuccess(baseBean.getResult());
                        } else if (baseBean.getCode().equals("0")) {
                            //code = 0处理
                            listener.onFailed(baseBean.getMessage(), new Exception("接待状态提交失败！"));
                        } else {

                        }
                    }
                });
    }
}
