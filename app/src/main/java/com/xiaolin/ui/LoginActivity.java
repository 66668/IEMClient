package com.xiaolin.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xiaolin.R;
import com.xiaolin.app.Constants;
import com.xiaolin.app.MyApplication;
import com.xiaolin.bean.LoginBean;
import com.xiaolin.presenter.LoginPresenterImpl;
import com.xiaolin.presenter.ipresenter.ILoginPresenter;
import com.xiaolin.ui.base.BaseActivity;
import com.xiaolin.ui.iview.ILoginView;
import com.xiaolin.utils.CheckNetwork;
import com.xiaolin.utils.DebugUtil;
import com.xiaolin.utils.SPUtils;
import com.xiaolin.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 有空修改成 checkbox记住密码,使用（状态模式+中介者模式） --07-28书
 */

public class LoginActivity extends BaseActivity implements ILoginView {
    static final String TAG = "login";

    //公司名
    @BindView(R.id.et_store)
    EditText et_store;

    //用户名
    @BindView(R.id.et_user)
    EditText et_user;

    // 密码
    @BindView(R.id.et_ps)
    EditText et_ps;

    //登录按钮
    @BindView(R.id.btn_login)
    Button btn_login;

    ILoginPresenter loginPresenter;

    String storeName;
    String userName;
    String password;
    String IP;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        ButterKnife.bind(this);
        //判断自动登录
        if (MyApplication.getInstance().isLogin()) {
            startActivity(MainActivity.class);
            this.finish();
        }
        initMyView();
        saveContent();
    }

    /**
     * 自动保存设置
     */
    private void saveContent() {
        if (!TextUtils.isEmpty(SPUtils.getString(Constants.STORENAME))) {
            et_store.setText(SPUtils.getString(Constants.STORENAME));
        }
        if (!TextUtils.isEmpty(SPUtils.getString(Constants.USRENAME))) {
            et_user.setText(SPUtils.getString(Constants.USRENAME));
        }
        if (!TextUtils.isEmpty(SPUtils.getString(Constants.PASSWORD))) {
            et_ps.setText(SPUtils.getString(Constants.PASSWORD));
        }
    }

    private void initMyView() {
        loginPresenter = new LoginPresenterImpl(LoginActivity.this, this);
    }

    /**
     * 登录按钮
     */
    @OnClick(R.id.btn_login)
    void login(View view) {
        if(!CheckNetwork.isNetworkConnected(this)&&!CheckNetwork.isWifiConnected(this)){
            DebugUtil.ToastShort(this,"请检查网络连接！");
            return;
        }
        getInfo();
        if (!isEmpty()) { //非空判断
            //交给p层处理，接口回调处理
            loginPresenter.pLogin(storeName, userName, password, IP);
        }
        //        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        //        startActivity(intent);
        //        this.finish();
    }

    private void getInfo() {
        storeName = et_store.getText().toString();
        userName = et_user.getText().toString();
        password = et_ps.getText().toString();
        IP = Utils.getIPAddress(getApplicationContext());
    }


    //判断输入是否是空
    private boolean isEmpty() {

        if (TextUtils.isEmpty(storeName)) {
            DebugUtil.ToastShort(LoginActivity.this, getResources().getString(R.string.login_storeName_input));
            return true;
        }

        if (TextUtils.isEmpty(userName)) {
            DebugUtil.ToastShort(LoginActivity.this, getResources().getString(R.string.login_userName_input));
            return true;
        }

        if (TextUtils.isEmpty(password)) {
            DebugUtil.ToastShort(LoginActivity.this, getResources().getString(R.string.login_password_input));
            return true;
        }
        return false;

    }

    @Override
    public void showProgress() {
        loadingDialog.show();
    }

    @Override
    public void hideProgress() {
        //运行完弹窗消失
        loadingDialog.dismiss();
    }

    @Override
    public void onSuccess(LoginBean bean) {
        //将返回数据保存
        SPUtils.putString(Constants.STORE_ID, bean.getStoreId());
        SPUtils.putString(Constants.STORE_USER_ID, bean.getStoreUserId());
        SPUtils.putString(Constants.EMPLOYEE_ID, bean.getEmployeeId());

        SPUtils.putString(Constants.TODAY_DATE, bean.getTodayDate());
        SPUtils.putString(Constants.DEVICEID, bean.getDeviceId());
        SPUtils.putString(Constants.EMPLOYEENAME, bean.getEmployeeName());

        SPUtils.putString(Constants.FIRSTATTEND, bean.getFirstAttend());
        SPUtils.putString(Constants.LASTATTEND, bean.getLastAttend());
        SPUtils.putString(Constants.YESTODY_DATE, bean.getYesterdayState());
        DebugUtil.d(TAG, "LoginPresenterImpl--缓存数据");

        //推送设置别名
//        setAlias(Utils.getMacByWifi());

        //添加自动登录
        MyApplication.getInstance().setLogin(true);
        //界面跳转
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        LoginActivity.this.finish();
    }

    @Override
    public void showDialogFailed(String msg, Exception e) {
        DebugUtil.ToastShort(LoginActivity.this, msg);
        DebugUtil.d(TAG, e.toString());
    }

    /**
     * jpush 绑定别名
     */
//    private void setAlias(String alias) {
//        //设置别名，替换非法字符
//        final String newAlias = alias.replace(":", "_");
//        DebugUtil.d("JPush", "newAlias=" + newAlias);
//        JPushInterface.setAliasAndTags(getApplicationContext(), newAlias, null, new TagAliasCallback() {
//
//            @Override
//            public void gotResult(int code, String s, Set<String> set) {
//                DebugUtil.d("JPush", "极光推送别名设置-->");
//                switch (code) {
//                    case 0:
//                        DebugUtil.d("JPush", "newAlias=" + newAlias + "Set tag and alias success极光推送别名设置成功");
//                        break;
//                    case 6002:
//                        DebugUtil.d("JPush", "newAlias=" + newAlias + "极光推送别名设置失败，Code = 6002");
//                        break;
//                    default:
//                        DebugUtil.d("JPush", "newAlias=" + newAlias + "极光推送设置失败，Code = " + code);
//                        break;
//                }
//            }
//        });
//    }
}
