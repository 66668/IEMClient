package com.xiaolin.model.imodel;

import com.xiaolin.model.listener.OnAttendMontStateListener;
import com.xiaolin.model.listener.OnCommonListener;

import java.io.File;

/**
 * m层，m接口层
 */

public interface IAttendModel {

    /**
     * 获取月考勤状态
     */

    void getAttendStatusByMonth(String year, String month, final OnAttendMontStateListener listener);


    /**
     *
     */
    void maddVisitor(String jsonstr, File file, OnCommonListener listener);
}
