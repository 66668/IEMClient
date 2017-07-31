package com.xiaolin.model.imodel;

import com.xiaolin.model.listener.OnUpgradeListener;

/**
 * Created by sjy on 2017/7/31.
 */

public interface IMainModel {
    void checkUpdate(OnUpgradeListener listener);

}
