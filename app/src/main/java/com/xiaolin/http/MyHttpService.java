package com.xiaolin.http;



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
//    /**
//     * 01 登录
//     *
//     * @param username
//     * @param password
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("User/LoginByPassword")
//    //post
//    Observable<LoginBean<UserInfoBean>> login(@Field("username") String username, @Field("password") String password);
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