package com.xiaolin.model;

import com.xiaolin.app.Constants;
import com.xiaolin.bean.BaseBean;
import com.xiaolin.http.MyHttpService;
import com.xiaolin.model.imodel.ILocationModel;
import com.xiaolin.model.listener.OnCommonListener;
import com.xiaolin.utils.DebugUtil;
import com.xiaolin.utils.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sjy on 2017/7/28.
 */

public class LocationModelImpl implements ILocationModel {
    private static final String TAG = "loaction";

    @Override
    public void mpostData(String currentTime, String adress, String lat, String lon, final OnCommonListener listener) {

        String companyID = SPUtils.getString(Constants.STORE_ID);
        String employeeID = SPUtils.getString(Constants.EMPLOYEE_ID);
        String employeeName = SPUtils.getString(Constants.EMPLOYEENAME);
        String departmentID = "";
        String workID = "";

        JSONObject js = new JSONObject();

        try {
            js.put("CapTime", currentTime);
            js.put("Location", adress);
            js.put("Latitude", lat);
            js.put("Longitude", lon);

            js.put("EmployeeID", employeeID);
            js.put("CompanyID", companyID);
            js.put("DepartmentID", departmentID);
            js.put("EmployeeName", employeeName);
            js.put("WrokId", workID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        DebugUtil.d(TAG, js.toString());

        MyHttpService.Builder.getHttpServer().locationAttend(js.toString())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean>() {
                    @Override
                    public void onCompleted() {
                        DebugUtil.d(TAG, "LocationModelImpl--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        DebugUtil.e(TAG, "LocationModelImpl--onError:" + e.toString());
                        listener.onFailed("签到异常", (Exception) e);
                    }

                    @Override
                    public void onNext(BaseBean bean) {
                        DebugUtil.d(TAG, "LocationModelImpl-onNext");
                        DebugUtil.d(TAG, bean.toString());

                        //处理返回结果
                        if (bean.getCode().equals("1")) {
                            //code = 1
                            listener.onSuccess(bean.getMessage());
                        } else if (bean.getCode().equals("0")) {
                            //code = 0处理
                            listener.onFailed(bean.getMessage(), new Exception("签到失败！"));
                        }

                    }
                });


    }
}
