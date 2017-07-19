package com.xiaolin.bean.login;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * 登录 输入监听，获取 输入 用户名 和密码
 *
 * Created by sjy on 2017/4/18.
 */

public class LoginEvent {
    private User user;

    //
    public LoginEvent(User user) {
        this.user = user;

    }

    //对userName监听
    public TextWatcher userNameWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            user.userName = s.toString();

        }
    };

    //对passsword监听
    public TextWatcher passwordWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            user.password = s.toString();
        }
    };
}
