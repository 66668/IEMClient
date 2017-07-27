package com.xiaolin.ui;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.xiaolin.R;
import com.xiaolin.library.PermissionListener;
import com.xiaolin.library.PermissionsUtil;
import com.xiaolin.presenter.ipresenter.IMainPresenter;
import com.xiaolin.presenter.MainPresenterImpl;
import com.xiaolin.ui.base.BaseActivity;
import com.xiaolin.ui.iview.IMainView;
import com.xiaolin.utils.DebugUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements IMainView {
    private static final String TAG = "MainActivity";

    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.navigaitnoView)
    NavigationView navigationView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private IMainPresenter iMainPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置透明标题栏
        //        transparentStatusBar();
        //允许窗口扩展到屏幕之外
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(R.layout.act_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setSupportActionBar(toolbar);

        //设置抽屉布局开关(低于5.0版本不可用)
        if (Build.VERSION.SDK_INT >= 21) {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close);
            toggle.syncState();
            mDrawerLayout.addDrawerListener(toggle);
        }
        int resId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = getResources().getDimensionPixelSize(resId);
        AppBarLayout.LayoutParams lp1 = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        lp1.topMargin = statusBarHeight;
        toolbar.setLayoutParams(lp1);

        //初始化p层
        iMainPresenter = new MainPresenterImpl(MainActivity.this, this);

        //navigation点击事件
        setDrawerContent(navigationView);
    }

    /**
     * 检测系统版本并使状态栏全透明
     */
    //    protected void transparentStatusBar() {
    //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
    //            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    //        }
    //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
    //            Window window = getWindow();
    //            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS |
    //                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    //            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
    //                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    //            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    //            window.setStatusBarColor(Color.TRANSPARENT);
    //        }
    //    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //NavigationView 监听事件
    private void setDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //交给p层处理
                iMainPresenter.switchNavigation(item.getItemId());
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    /**
     * 多控件监听
     */
    @OnClick({R.id.layout_attend, R.id.layout_visitor, R.id.loaction})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_attend://考勤记录
                break;
            case R.id.layout_visitor://访客记录记录
                break;
            case R.id.loaction://地图定位
                iMainPresenter.mapLocation();
                break;
        }
    }

    /**
     * IMainView 个人信息跳转
     */
    @Override
    public void turnToInfo() {

    }

    /**
     * IMainView 修改密码跳转
     */
    @Override
    public void turnToChangePs() {

    }

    /**
     * IMainView 退出
     */
    @Override
    public void quitApp() {

    }

    @Override
    public void turnToMapLocation() {
        //地图定位+6.0权限设置
        if (PermissionsUtil.hasPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)) {//ACCESS_FINE_LOCATION ACCESS_COARSE_LOCATION这两个是一组，用一个判断就够了
            startActivity(MapLocationActivity.class);
        } else {
            //第一次使用该权限调用
            PermissionsUtil.requestPermission(this
                    , new PermissionListener() {
                        @Override
                        public void permissionGranted(@NonNull String[] permissions) {
                            //弹窗提示 允许使用就跳转界面
                            startActivity(MapLocationActivity.class);
                        }

                        @Override
                        public void permissionDenied(@NonNull String[] permissions) {
                            DebugUtil.toastLong(MainActivity.this, "该功能不可用");
                        }
                    }
                    , Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void turnToAttendRecord() {

    }

    @Override
    public void turnToVisitor() {

    }

}
