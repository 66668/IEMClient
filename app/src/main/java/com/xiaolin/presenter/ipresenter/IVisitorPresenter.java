package com.xiaolin.presenter.ipresenter;

import java.io.File;

/**
 * Created by sjy on 2017/7/27.
 */

public interface IVisitorPresenter {
    /**
     *
     */
    void pGetData(int pageSize, String isReceived, String maxTime, String minTime);

    void addVisitor(String str, File file);

    void pIsReceived(String visitorID, String storeID);
}
