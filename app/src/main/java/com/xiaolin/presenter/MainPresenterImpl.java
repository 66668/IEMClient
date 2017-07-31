package com.xiaolin.presenter;

import android.content.Context;

import com.xiaolin.R;
import com.xiaolin.bean.UpgradeBean;
import com.xiaolin.model.MainModelImpl;
import com.xiaolin.model.imodel.IMainModel;
import com.xiaolin.model.listener.OnUpgradeListener;
import com.xiaolin.presenter.ipresenter.IMainPresenter;
import com.xiaolin.ui.iview.IMainView;

/**
 * prenter层，负责与act对接
 * 负责数据传递
 */

public class MainPresenterImpl implements IMainPresenter, OnUpgradeListener {
    private static final String TAG = "MainActivity";

    private Context context;
    private IMainView iMainView;
    private IMainModel mainModel;

    public MainPresenterImpl(Context context, IMainView iMainView) {
        this.context = context;
        this.iMainView = iMainView;
        mainModel = new MainModelImpl();
    }


    @Override
    public void switchNavigation(int id) {
        switch (id) {
            case R.id.nav_menu_update://更新

                checkUpdate();
                break;

            case R.id.nav_menu_psd://修改密码

                iMainView.turnToChangePs();
                break;

            case R.id.nav_menu_quit://退出

                iMainView.quitApp();
                break;

        }

    }

    /**
     * 检查更新
     */
    private void checkUpdate() {
        iMainView.showProgress();
        mainModel.checkUpdate(this);
    }

    @Override
    public void mapLocation() {
        iMainView.turnToMapLocation();
    }

    @Override
    public void toVisitor() {
        iMainView.turnToVisitor();
    }

    @Override
    public void toAttendRecord() {
        iMainView.turnToChangePs();
    }

    @Override
    public void onSuccess(UpgradeBean bean) {
        iMainView.hideProgress();
        iMainView.showUpgradeDialog(bean);

    }

    @Override
    public void onFailed(String msg, Exception e) {
        iMainView.hideProgress();
        iMainView.postFaild(msg, e);
    }
}
