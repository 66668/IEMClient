package com.xiaolin.ui.iview;

/**
 * Created by sjy on 2017/7/31.
 */

public interface ICommonView {
    void showProgress();

    void hideProgress();

    void postSuccessShow(String str);

    void postFaild(String msg, Exception e);
}
