package com.xiaolin.model;

import com.xiaolin.app.Constants;
import com.xiaolin.bean.CommonBean;
import com.xiaolin.bean.LoginBean;
import com.xiaolin.http.MyHttpService;
import com.xiaolin.model.imodel.ILoginModel;
import com.xiaolin.model.listener.OnLoginListener;
import com.xiaolin.utils.DebugUtil;
import com.xiaolin.utils.SPUtils;
import com.xiaolin.utils.Utils;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 登录
 * 具获取数据层
 */

public class LoginModelImpl implements ILoginModel {
    static final String TAG = "login";

    @Override
    public void mLogin(final String storeName, final String userName, final String passWord, String IP, final OnLoginListener listener) {
        //除了输入的三个参数，还有几个默认的参数
        String deviceType = Utils.getPhoneModel();//设备类型
        String deviceName = userName;//设备名称（使用用户名当设备名称）(必须)
        String MAC = Utils.getMacByWifi();//MAC地址
        String DeviceSN = "";//系列号
        String Remark = "android";
        String DeviceInfo = "1@1001";//是否接收推送(0:不接收;1:接收) @手机类型(1001:Android;1002:IOS) (必须)


        MyHttpService.Builder.getHttpServer().login(
                storeName
                , userName
                , passWord
                , deviceType
                , deviceName
                , MAC
                , DeviceSN
                , Remark
                , DeviceInfo
                , IP)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonBean<LoginBean>>() {

                    @Override
                    public void onCompleted() {
                        DebugUtil.d(TAG, "LoginModelImpl--onCompleted");
                        //登录成功，保存登录信息
                        SPUtils.putString(Constants.STORENAME, storeName);
                        SPUtils.putString(Constants.USRENAME, userName);
                        SPUtils.putString(Constants.PASSWORD, passWord);
                    }

                    @Override
                    public void onError(Throwable e) {
                        DebugUtil.e(TAG, "login--onError: " + e.toString());
                        listener.onLoginFailed("获取数据异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonBean<LoginBean> bean) {
                        DebugUtil.d(TAG, "LoginModelImpl-onNext");
                        DebugUtil.d(TAG, bean.toString());

                        //处理返回结果
                        if (bean.getCode().equals("1")) {
                            //code = 1
                            listener.onLoginSuccess(bean.getResult());
                        } else if (bean.getCode().equals("0")) {
                            //code = 0处理
                            listener.onLoginFailed(bean.getMessage(), new Exception("登陆失败"));
                        } else {

                        }
                    }
                });

    }
}
