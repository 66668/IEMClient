package com.xiaolin.model.listener;

import com.xiaolin.bean.VisitorBean;

import java.util.List;

/**
 * visitorBean监听接口
 */

public interface OnVisitorListener {

    void onVisitorSuccess(List<VisitorBean> listbean);

    void onVisitorFailed(String msg, Exception e);

}
