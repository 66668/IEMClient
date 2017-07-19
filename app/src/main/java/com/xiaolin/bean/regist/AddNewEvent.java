package com.xiaolin.bean.regist;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * 登录 输入监听，获取 输入 用户名 和密码
 * <p>
 * Created by sjy on 2017/4/18.
 */

public class AddNewEvent {
    private AddnewBean bean;

    //
    public AddNewEvent(AddnewBean user) {
        this.bean = user;
    }

    //对Name监听
    public TextWatcher nameWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            bean.EmployeeName = s.toString().trim();

        }
    };

    //对手机号监听
    public TextWatcher telephoneWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            bean.Telephone = s.toString().trim();
        }
    };

    //对微信监听
    public TextWatcher weChatWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            bean.WeChat = s.toString().trim();
        }
    };

    //对邮箱监听
    public TextWatcher emailWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            bean.Email = s.toString().trim();
        }
    };

    //对公司监听
    public TextWatcher storeWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            bean.EmpStore = s.toString().trim();
        }
    };

    //对职位监听
    public TextWatcher positionWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            bean.position = s.toString().trim();
        }
    };

    //对行业监听
    public TextWatcher industryWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            bean.Industry = s.toString().trim();
        }
    };
}
