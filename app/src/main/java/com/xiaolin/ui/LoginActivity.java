package com.xiaolin.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xiaolin.R;
import com.xiaolin.presenter.LoginPresenterImpl;
import com.xiaolin.presenter.ipresenter.ILoginPresenter;
import com.xiaolin.ui.base.BaseActivity;
import com.xiaolin.ui.iview.ILoginView;
import com.xiaolin.utils.DebugUtil;
import com.xiaolin.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
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
        initMyView();
    }

    private void initMyView() {
        loginPresenter = new LoginPresenterImpl(LoginActivity.this, this);
    }

    /**
     * 登录按钮
     */
    @OnClick(R.id.btn_login)
    void login(View view) {
        getInfo();
        isEmpty();  //非空判断

        //交给p层处理，接口回调处理
        loginPresenter.pLogin(storeName, userName, password, IP);
    }

    private void getInfo() {
        storeName = et_store.getText().toString();
        userName = et_user.getText().toString();
        password = et_ps.getText().toString();
        IP = Utils.getIPAddress(getApplicationContext());
    }


    //判断输入是否是空
    private void isEmpty() {

        if (TextUtils.isEmpty(storeName)) {
            DebugUtil.ToastShort(LoginActivity.this, getResources().getString(R.string.login_storeName_input));
            return;
        }
        if (TextUtils.isEmpty(userName)) {
            DebugUtil.ToastShort(LoginActivity.this, getResources().getString(R.string.login_userName_input));
            return;
        }
        if (TextUtils.isEmpty(password)) {
            DebugUtil.ToastShort(LoginActivity.this, getResources().getString(R.string.login_password_input));
            return;
        }

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
    public void showDialogFailed(String msg, Exception e) {
        DebugUtil.ToastShort(LoginActivity.this, msg);
        DebugUtil.d(TAG, e.toString());
    }
}
