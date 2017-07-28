package com.xiaolin.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaolin.ui.base.BaseActivity;
import com.yvision.R;
import com.yvision.common.MyException;
import com.yvision.dialog.Loading;
import com.yvision.helper.UserHelper;
import com.yvision.inject.ViewInject;
import com.yvision.utils.PageUtil;

public class ChangePassWordActivity extends BaseActivity {

    //back
    @ViewInject(id = R.id.layout_back, click = "forBack")
    RelativeLayout layout_back;

    //
    @ViewInject(id = R.id.tv_title)
    TextView tv_title;

    //
    @ViewInject(id = R.id.tv_right, click = "forCommit")
    TextView tv_right;

    // 修改按钮
    @ViewInject(id = R.id.btn_commit, click = "commit")
    Button btn_commit;

    // 旧密码
    @ViewInject(id = R.id.et_oldpsd)
    EditText et_oldpsd;

    // 新密码
    @ViewInject(id = R.id.et_newpsd)
    EditText et_newpsd;

    @ViewInject(id = R.id.et_newpsd_again)
    EditText et_newpsd_again;

    // 变量
    public String oldUserPassword;
    public String newUserPassword;
    public String psdStr_newAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.act_changepassword);
        tv_title.setText("修改密码");
        tv_right.setText("");
    }

    public void commit(View view) {
        //获取界面信息
        oldUserPassword = et_oldpsd.getText().toString().trim();
        newUserPassword = et_newpsd.getText().toString().trim();
        psdStr_newAgain = et_newpsd_again.getText().toString().trim();
        // 非空验证
        if (!checkInput()) {
            return;
        }
        // 线程处理修改密码
        Loading.run(this, new Runnable() {
            @Override
            public void run() {

                try {
                    String msg = UserHelper.changePassword(ChangePassWordActivity.this,
                            oldUserPassword,
                            newUserPassword);
                    // 修改密码后的处理
                    ChangePassWordActivity.this.sendToastMessage(msg);
                    ChangePassWordActivity.this.finish();// 消除页面

                } catch (MyException e) {
                    e.printStackTrace();
                    // 修改判断
                    if (TextUtils.isEmpty(e.getMessage())) {
                        PageUtil.DisplayToast(R.string.ChangePassWordActivity_oldpsd_error);// 旧密码错误
                    } else {
                        // --BaseActivity的ui处理
                        sendToastMessage(e.getMessage());// false=非空
                    }
                }
            }
        });
    }

    // 非空验证
    public boolean checkInput() {
        // 非空
        if (TextUtils.isEmpty(oldUserPassword)) {
            PageUtil.DisplayToast(R.string.ChangePassWordActivity_oldpsd);
            return false;
        }

        if (TextUtils.isEmpty(et_newpsd.getText().toString().trim())) {
            PageUtil.DisplayToast(R.string.ChangePassWordActivity_newpsd);
            return false;
        }

        if (!(newUserPassword.equals(psdStr_newAgain))) {
            PageUtil.DisplayToast(R.string.ChangePassWordActivity_newpsd_error);
            return false;
        }
        // 密码长度问题
        if (!(newUserPassword.length() >= 6 && newUserPassword.length() <= 20)) {
            PageUtil.DisplayToast(R.string.ChangePassWordActivity_newpsd_error_lenght);
            return false;
        }
        //内容要求
        for (int i = 0; i < newUserPassword.length() - 1; i++) {
            char c = newUserPassword.charAt(i);
            if (!(c >= '0' && c <= '9' || c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c == '_')) {
                PageUtil.DisplayToast(R.string.requestForPsd_error);
                return false;
            }
        }
        //判断是不是原密码
        return true;
    }

    // back
    public void forBack(View view) {
        this.finish();
    }
}
