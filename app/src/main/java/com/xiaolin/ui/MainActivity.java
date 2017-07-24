package com.xiaolin.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.xiaolin.R;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private NavigationView navigationView;
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置透明标题栏
//        transparentStatusBar();
        //允许窗口扩展到屏幕之外
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(R.layout.act_main);
        initView();

    }

    private void initView() {
        navigationView = (NavigationView) findViewById(R.id.navigaitnoView);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //设置按钮开关(低于5.0版本不可用)
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
}
