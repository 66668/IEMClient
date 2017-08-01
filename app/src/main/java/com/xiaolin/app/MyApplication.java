package com.xiaolin.app;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.xiaolin.http.HttpUtils;
import com.xiaolin.utils.DebugUtil;
import com.xiaolin.utils.Utils;

import java.io.File;

/**
 * Created by sjy on 2017/4/18.
 */

public class MyApplication extends Application {
    private final static String sdcardDirName = "ISS";//内存根目录名
    private static MyApplication MyApplication;
    private boolean isLogin= false;

    public static MyApplication getInstance() {
        return MyApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication = this;
        //正式打包，将DebugUtil.DEBUG值设为 false
        HttpUtils.getInstance().init(this, DebugUtil.DEBUG);
    }

    // 处理图片路径
    public static String getHandledUserPhotoPath(Context context) {
        //图片路径：1）YUEVISION/tempPics/uploadTemp/handled.jpg
        return getUploadPicPath(context) + File.separator + "handled.jpg";
    }

    // 未处理图片路径
    // CreateUserActivity--UpdateAvatar--MyApplication该方法：拍照图片选择
    public static String getUnhandledUserPhotoPath(Context context) {
        // （1）YUEVISION/tempPics/uploadTemp/unhandled.jpg
        String path = getUploadPicPath(context) + File.separator + "unhandled.jpg";
        return path;
    }

    // 图片上传目录
    public static String getUploadPicPath(Context context) {
        // (2)YUEVISION/tempPics/uploadTemp
        String uploadPath = getPicCachePath(context) + File.separator + "uploadTemp";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        return uploadPath;
    }
    // 获取图片缓存目录
    // LoginActivity--MyApplication该方法
    public static String getPicCachePath(Context context) {
        // (3)YUEVISION/tempPics
        String cachePicPath = getBaseDir(context) + File.separator + "tempPics";
        File cachePath = new File(cachePicPath);
        if (!cachePath.exists()) {
            cachePath.mkdir();
        }
        return cachePicPath;
    }

    // 图片上传目录
    public static String getBaseDir(Context context) {
        // (4)获取sd卡根路径
        String sdcard_base_path = null;
        long availableSDCardSpace = Utils.getExternalStorageSpace();// 获取SD卡可用空间
        if (availableSDCardSpace != -1L) {// 如果存在SD卡/-1L:没有SD卡
            // sd/YUEVISION
            sdcard_base_path = Environment.getExternalStorageDirectory() + File.separator + sdcardDirName;//YUEVISION
        } else if (Utils.getInternalStorageSpace() != -1L) {//有内存空间
            // YUEVISION
            sdcard_base_path = context.getFilesDir().getPath() + File.separator + sdcardDirName;
        } else {// sd卡不存在
            // 没有可写入位置
        }
        if (sdcard_base_path != null) {
            // 初始化根目录
            File basePath = new File(sdcard_base_path);
            if (!basePath.exists()) {
                basePath.mkdir();
            }
        }
        return sdcard_base_path;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}


