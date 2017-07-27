package com.xiaolin.presenter.ipresenter;

/**
 * Created by sjy on 2017/7/27.
 */

public interface IVisitorPresenter {
    /**
     * pageSize：展示最多记录数量
     * timespan：获取今天（1）或者全部（其他）。
     * storeID：公司ID
     * iMaxTime：最大时间
     * iMinTime：最小时间
     * employeeID：受访者EmployeeID
     * isReceived：是否已接待（1：已接待 0：未接待 空：全部）
     */
    void pGetData(String stroeID, String employeeID, String isReceived, String maxTime, String minTime);
}
