package com.xiaolin.model.listener;

import com.xiaolin.bean.UpgradeBean;

/**
 * 更新监听
 *
 * Created by sjy on 2017/7/28.
 */

public interface OnUpgradeListener {
    void onSuccess(UpgradeBean bean);

    void onFailed(String msg, Exception e);
}
