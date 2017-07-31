package com.xiaolin.model.imodel;

import com.xiaolin.model.listener.OnCommonListener;

/**
 * Created by sjy on 2017/7/31.
 */

public interface IChangePsModel {
    void postChangePs(String old, String newps, OnCommonListener listener);
}
