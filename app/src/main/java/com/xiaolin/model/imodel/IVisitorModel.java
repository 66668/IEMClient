package com.xiaolin.model.imodel;

import com.xiaolin.model.listener.OnCommonListener;
import com.xiaolin.model.listener.OnVisitorListener;

import java.io.File;

/**
 * m层，m接口层
 */

public interface IVisitorModel {
    /**
     * pageSize：展示最多记录数量
     * timespan：获取今天（1）或者全部（其他）。
     * storeID：公司ID
     * iMaxTime：最大时间
     * iMinTime：最小时间
     * employeeID：受访者EmployeeID
     * isReceived：是否已接待（1：已接待 0：未接待 空：全部）
     */

    void mLoadData(String stroeID, String employeeID, String isReceived, String maxTime, String minTime, OnVisitorListener listener);

    /**
     *
     */
    void maddVisitor(String jsonstr, File file, OnCommonListener listener);
}
