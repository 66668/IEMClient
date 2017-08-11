package com.xiaolin.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaolin.R;
import com.xiaolin.presenter.ChangePsPresenterImpl;
import com.xiaolin.ui.base.BaseActivity;
import com.xiaolin.ui.iview.ICommonView;
import com.xiaolin.utils.DebugUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 修改密码
 */
public class ChangePassWordActivity extends BaseActivity implements ICommonView {

    @BindView(R.id.layout_back)
    RelativeLayout layout_back;

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_right)
    TextView tv_right;
    // 修改按钮
    @BindView(R.id.btn_commit)
    Button btn_commit;

    // 旧密码
    @BindView(R.id.et_oldpsd)
    EditText et_oldpsd;

    // 新密码
    @BindView(R.id.et_newpsd)
    EditText et_newpsd;

    //
    @BindView(R.id.et_newpsd_again)
    EditText et_newpsd_again;

    // 变量
    public String oldUserPassword;
    public String newUserPassword;
    public String psdStr_newAgain;
    ChangePsPresenterImpl changePsPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.act_change_ps);
        initMyView();


    }

    private void initMyView() {
        ButterKnife.bind(this);

        tv_title.setText("修改密码");
        tv_right.setText("");
        changePsPresenter = new ChangePsPresenterImpl(ChangePassWordActivity.this, this);
    }

    @OnClick(R.id.btn_commit)
    public void commit(View view) {
        //获取界面信息
        oldUserPassword = et_oldpsd.getText().toString().trim();
        newUserPassword = et_newpsd.getText().toString().trim();
        psdStr_newAgain = et_newpsd_again.getText().toString().trim();
        // 非空验证
        if (!isEmpoty()) {
            return;
        }
        changePsPresenter.postChangePs(oldUserPassword, newUserPassword);
    }

    @OnClick(R.id.layout_back)
    public void forBack(View view) {
        this.finish();
    }

    // 非空验证
    public boolean isEmpoty() {
        // 非空
        if (TextUtils.isEmpty(oldUserPassword)) {
            DebugUtil.ToastShort(ChangePassWordActivity.this, getResources().getString(R.string.ChangePassWordActivity_oldpsd));
            return false;
        }

        if (TextUtils.isEmpty(et_newpsd.getText().toString().trim())) {
            DebugUtil.ToastShort(ChangePassWordActivity.this, getResources().getString(R.string.ChangePassWordActivity_newpsd));
            return false;
        }

        if (!(newUserPassword.equals(psdStr_newAgain))) {
            DebugUtil.ToastShort(ChangePassWordActivity.this, getResources().getString(R.string.ChangePassWordActivity_newpsd_error));
            return false;
        }
        // 密码长度问题
        if (!(newUserPassword.length() >= 6 && newUserPassword.length() <= 20)) {
            DebugUtil.ToastShort(ChangePassWordActivity.this, getResources().getString(R.string.ChangePassWordActivity_newpsd_error_lenght));
            return false;
        }
        //内容要求
        for (int i = 0; i < newUserPassword.length() - 1; i++) {
            char c = newUserPassword.charAt(i);
            if (!(c >= '0' && c <= '9' || c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c == '_')) {
                DebugUtil.ToastShort(ChangePassWordActivity.this, getResources().getString(R.string.requestForPsd_error));
                return false;
            }
        }
        //判断是不是原密码
        return true;
    }

    @Override
    public void showProgress() {
        loadingDialog.show();
    }

    @Override
    public void hideProgress() {
        loadingDialog.dismiss();

    }

    @Override
    public void postSuccessShow(String str) {
        DebugUtil.ToastShort(ChangePassWordActivity.this, str);

    }

    @Override
    public void postFaild(String msg, Exception e) {
        DebugUtil.ToastShort(ChangePassWordActivity.this, msg);
        DebugUtil.e(e.toString());
    }
}
