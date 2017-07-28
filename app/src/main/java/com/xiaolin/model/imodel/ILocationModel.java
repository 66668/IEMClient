package com.xiaolin.model.imodel;

import com.xiaolin.model.listener.OnCommonListener;

/**
 * Created by sjy on 2017/7/28.
 */

public interface ILocationModel {
    void mpostData(String currentTime, String adress, String lat, String lon, OnCommonListener listener);
}
