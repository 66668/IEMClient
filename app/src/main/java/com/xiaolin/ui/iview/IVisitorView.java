package com.xiaolin.ui.iview;

import com.xiaolin.bean.VisitorBean;

import java.util.List;

/**
 * Created by sjy on 2017/7/27.
 */

public interface IVisitorView {
    void showProgress();

    void hideProgress();

    void addList(List<VisitorBean> list);

    void showFailedMsg(String msg, Exception e);

}
