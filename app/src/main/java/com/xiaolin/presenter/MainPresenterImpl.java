package com.xiaolin.presenter;

import android.content.Context;

import com.xiaolin.R;
import com.xiaolin.presenter.ipresenter.IMainPresenter;
import com.xiaolin.ui.iview.IMainView;

/**
 * prenter层，负责与act对接
 * 负责数据传递
 */

public class MainPresenterImpl implements IMainPresenter {
    private static final String TAG = "login";

    private Context context;
    private IMainView iMainView;


    public MainPresenterImpl(Context context, IMainView iMainView) {
        this.context = context;
        this.iMainView = iMainView;
    }


    @Override
    public void switchNavigation(int id) {
        switch (id) {
            case R.id.nav_menu_update://更新
                iMainView.turnToupdate();
                break;
            case R.id.nav_menu_psd://修改密码
                iMainView.turnToChangePs();
                break;
            case R.id.nav_menu_quit://退出
                iMainView.quitApp();
                break;
        }

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
}
