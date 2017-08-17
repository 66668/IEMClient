package com.xiaolin.ui;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.xiaolin.R;
import com.xiaolin.app.Constants;
import com.xiaolin.app.MyApplication;
import com.xiaolin.bean.UpgradeBean;
import com.xiaolin.dialog.UpgradeDialog;
import com.xiaolin.library.PermissionListener;
import com.xiaolin.library.PermissionsUtil;
import com.xiaolin.presenter.MainPresenterImpl;
import com.xiaolin.presenter.ipresenter.IMainPresenter;
import com.xiaolin.ui.base.BaseActivity;
import com.xiaolin.ui.iview.IMainView;
import com.xiaolin.utils.DebugUtil;
import com.xiaolin.utils.JPushUtil;
import com.xiaolin.utils.SPUtils;
import com.xiaolin.utils.Utils;

import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity implements IMainView {
    private static final String TAG = "MainActivity";

    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.navigaitnoView)
    NavigationView navigationView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    //昨日考勤
    @BindView(R.id.tv_attendYestoday)
    TextView tv_yestoday;

    //今日打卡
    @BindView(R.id.tv_attendToday)
    TextView tv_attendToday;

    //公司名称
    @BindView(R.id.storeName)
    TextView storeName;

    //nav_head
    TextView nav_tv_name;


    private IMainPresenter iMainPresenter;
    private MessageReceiver mMessageReceiver;

    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    /**
     * 在onCreate()执行之后执行的onPostCreate()方法中执行修改toolbar的默认标题
     * Toolbar 必须在onCreate()之后设置标题文本，否则默认标签将覆盖我们的设置
     */
    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toolbar.setTitle("");
    }

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
        initJpush();
    }

    //极光配置
    private void initJpush() {

        JPushInterface.init(getApplicationContext());

        registerMessageReceiver();  // used for receive msg
        JPushInterface.resumePush(getApplicationContext());
        //推送设置别名
        setAlias(Utils.getMacByWifi());
    }

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!JPushUtil.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
                Log.d("JPush", "rid=" + JPushInterface.getRegistrationID(MainActivity.this) + "\n--showMsg" + showMsg);
            }
        }
    }

    private void initView() {
        setSupportActionBar(toolbar);

        //显示必要参数
        showText();

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
        storeName.setText(SPUtils.getString(Constants.COMPANYNAME));
    }

    private void showText() {

        //给NavigationView填充顶部区域，也可在xml中使用app:headerLayout="@layout/header_nav"来设置
        navigationView.inflateHeaderView(R.layout.nav_header_main);
        View headerView = navigationView.getHeaderView(0);
        nav_tv_name = (TextView) headerView.findViewById(R.id.nav_tv_name);

        nav_tv_name.setText(SPUtils.getString(Constants.EMPLOYEENAME));
        tv_yestoday.setText(SPUtils.getString(Constants.YESTODY_DATE));
        tv_attendToday.setText(SPUtils.getString(Constants.FIRSTATTEND));
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

    //设置topbar
    //    @Override
    //    public boolean onCreateOptionsMenu(Menu menu) {
    //        getMenuInflater().inflate(R.menu.menu_main, menu);
    //        return true;
    //    }
    //
    //    @Override
    //    public boolean onOptionsItemSelected(MenuItem item) {
    //
    //        int id = item.getItemId();
    //
    //        if (id == R.id.action_settings) {
    //            return true;
    //        }
    //
    //        return super.onOptionsItemSelected(item);
    //    }

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
     * 多控件 监听
     */
    @OnClick({R.id.layout_attend, R.id.layout_visitor, R.id.loaction})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_attend://考勤记录
                iMainPresenter.toAttendRecord();
                break;
            case R.id.layout_visitor://访客记录记录
                iMainPresenter.toVisitor();
                break;
            case R.id.loaction://地图定位
                iMainPresenter.mapLocation();
                break;
        }
    }

    /**
     * 地图定位
     */
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

    /**
     * 考勤记录
     */
    @Override
    public void turnToAttendRecord() {
        //        startActivity(AttendDayActivity.class);
        startActivity(AttendMonthActivity.class);
    }

    /**
     * 访客记录
     */
    @Override
    public void turnToVisitor() {
        startActivity(VisitorActivity.class);

    }

    /**
     * 退出
     */
    @Override
    public void quit() {
        //关闭自动登录设置
        MyApplication.getInstance().setLogin(false);

        //广播退出
        Intent intent = new Intent();
        intent.setAction(EXIT_APP_ACTION);
        sendBroadcast(intent);//发送退出的广播

    }

    @Override
    public void showProgress() {
        loadingDialog.show();
    }

    @Override
    public void hideProgress() {
        loadingDialog.dismiss();
    }

    /**
     * code=1 的String接口回调
     *
     * @param str
     */
    @Override
    public void postSuccessShow(String str) {
        DebugUtil.ToastShort(MainActivity.this, str);
    }

    /**
     * 检查更新 回调
     * <p>
     * 弹窗提示
     *
     * @param bean
     */
    @Override
    public void showUpgradeDialog(final UpgradeBean bean) {

        if (!bean.getVersion().equals(Utils.getVersionName())) {
            new UpgradeDialog(this, bean.getMessage(), false, new UpgradeDialog.UpdateAppDialogCallBack() {

                @Override
                public void confirm() {
                    //使用浏览器打开连接
                    Utils.openLink(MainActivity.this, bean.getPackageUrl());
                }

                @Override
                public void cancel() {

                }
            }).show();
        } else {
            DebugUtil.toastInMiddle(MainActivity.this, "已是最新版本，不需要更新！");
        }


    }

    @Override
    public void postFaild(String msg, Exception e) {
        DebugUtil.ToastShort(MainActivity.this, msg);
        DebugUtil.e(TAG, e.toString());

    }

    /**
     * jpush 绑定别名
     */
    private void setAlias(String alias) {
        final String newAlias = alias.replace(":", "_");
        DebugUtil.d("JPush", "极光推送别名设置-->" + newAlias);
        JPushInterface.setAliasAndTags(getApplicationContext(), newAlias, null, new TagAliasCallback() {

            @Override
            public void gotResult(int code, String s, Set<String> set) {

                switch (code) {
                    case 0:
                        DebugUtil.d("JPush", "Set tag and alias success极光推送别名设置成功");
                        break;
                    case 6002:
                        DebugUtil.d("JPush", "极光推送别名设置失败，Code = 6002");
                        break;
                    case 6003:
                        DebugUtil.d("JPush", "极光推送别名设置失败，Code = 6003：非法字符串拼接！");
                        break;
                    default:
                        DebugUtil.d("JPush", "极光推送设置失败，Code = " + code);
                        break;
                }
            }
        });
    }

}
