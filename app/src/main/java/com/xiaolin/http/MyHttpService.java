package com.xiaolin.http;


import com.xiaolin.bean.BaseBean;
import com.xiaolin.bean.CommonBean;
import com.xiaolin.bean.LoginBean;
import com.xiaolin.bean.UpgradeBean;
import com.xiaolin.bean.VisitorListBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by jingbin on 16/11/21.
 * 网络请求类（一个接口一个方法）
 * 注:使用Observable<BaseBean<AddnewReturnBean>> AddnewEmployee(@Field("Obj") String bean);形式时出现gson解析bug,没发用泛型，后续解决
 */
public interface MyHttpService {
    class Builder {

        /**
         * @return
         */
        public static MyHttpService getHttpServer() {
            return HttpUtils.getInstance().getServer(MyHttpService.class);
        }
    }

    /**
     * 01 登录
     *
     * @return
     */
    @FormUrlEncoded
    @POST(URLUtils.LOGIN)
    //post
    Observable<CommonBean<LoginBean>> login(
            @Field("adminUserName") String storeName
            , @Field("userName") String username
            , @Field("password") String password

            , @Field("deviceType") String deviceType
            , @Field("deviceName") String deviceName
            , @Field("MAC") String MAC

            , @Field("DeviceSN") String DeviceSN
            , @Field("Remark") String Remark
            , @Field("DeviceInfo") String DeviceInfo
            , @Field("IP") String IP);

    /**
     * 获取访客记录
     * <p>
     * pageSize：展示最多记录数量
     * timespan：获取今天（1）或者全部（其他）。
     * storeID：公司ID
     * iMaxTime：最大时间
     * iMinTime：最小时间
     * employeeID：受访者EmployeeID
     * isReceived：是否已接待（1：已接待 0：未接待 空：全部）
     *
     * @return
     */
    @FormUrlEncoded
    @POST(URLUtils.VISITOR)
    //post
    Observable<VisitorListBean> loadVisitor(
            @Field("storeID") String storeID
            , @Field("employeeID") String employeeID
            , @Field("isReceived") String isReceived

            , @Field("iMaxTime") String maxTime
            , @Field("iMinTime") String minTime

            , @Field("timespan") String timespan
            , @Field("pageSize") String DeviceSN);

    /**
     * 地图签到 上传obj参数
     */
    @FormUrlEncoded
    @POST(URLUtils.LOCATION)
    //post
    Observable<BaseBean> locationAttend(@Field("obj") String jsonStr);

    /**
     * 修改密码
     * post
     */
    @FormUrlEncoded
    @POST(URLUtils.CHANGEPS)
    Observable<BaseBean> changeps(@Field("storeUserID") String storeUserID
            , @Field("oldUserPassword") String oldUserPassword
            , @Field("newUserPassword") String newUserPassword);

    /**
     * 检查更新
     * get方式
     */
    @GET(URLUtils.CHECK_UPDATE)
    Observable<CommonBean<UpgradeBean>> checkUpdate();

    /**
     * 退出app
     * post
     */
    @FormUrlEncoded
    @POST(URLUtils.QUIT)
    Observable<BaseBean> quit(@Field("storeId") String storeId
            , @Field("userName") String userName
            , @Field("deviceId") String deviceId);

    //    /**
    //     * 03-01-01 添加新人员
    //     */
    //    @FormUrlEncoded
    //    @POST("AddEmployee/AddEmployeePost")
    //    Observable<BaseBean<String>> AddnewEmployee(@Field("Obj") String bean);
    //
    //
    //    /**
    //     * 03-02 获取签到列表, 也包括查询
    //     */
    //    @FormUrlEncoded
    //    @POST("SignEmployeeSearch/GetSignEmployeeSearch")//EmployeeSearch/GetEmployeeSearch
    //    Observable<SigninBeans> GetSearchSigninList(@Field("CompanyID") String CompanyID
    //            , @Field("ConferenceID") String ConferenceID
    //            , @Field("NameorPhoneorStore") String Telephone);
    //
    //
    //    /**
    //     * 03-01 获取注册列表, 也包括查询
    //     */
    //    @FormUrlEncoded
    //    @POST("RgEmployeeSearch/GetRgEmployeeSearch")
    //    Observable<RegistBean> GetSearchRegistList(@Field("CompanyID") String CompanyID
    //            , @Field("ConferenceID") String ConferenceID
    //            , @Field("NameorPhoneorStore") String Telephone);
    //
    //    /**
    //     * 02 获取会议列表
    //     *
    //     * @param storeId
    //     * @return
    //     */
    //    @FormUrlEncoded
    //    @POST("ConferenceInfo/GetConferenceInfo")
    //    Observable<ConferenceBean> getConferenceList(@Field("storeID") String storeId);
    //

    //
    //    /**
    //     * 二维码/验证码 签到
    //     *
    //     * @param EmployeeID
    //     * @return
    //     */
    //    @FormUrlEncoded
    //    @POST("WeChat/WeChatSign")
    //    //post
    //    Observable<BaseBean> getQecode(@Field("EmployeeID") String EmployeeID);
    //
    //
    //    /**
    //     * 01 发短信
    //     * 传递 参数和图片
    //     *
    //     * @return
    //     */
    //    @Multipart
    //    @POST("VFaceMember/SendAuthCode")
    //    //post
    //    Observable<BaseBean> sendMsg(@Part("Telephone") RequestBody Telephone
    //            , @Part("EmployeeID") RequestBody EmployeeID
    //            , @Part MultipartBody.Part file);


}