package com.xiaolin.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 相机相册工具类
 * 为了避免图片覆盖出现问题，保存的图片路径区分了相机相册
 */
public class EditPictureUtil {

    private static final String TAG = "GGG";

    private static String albumPath = "/DCIM/camera";//内部sd卡的根目录
    private static String c_originalIMG = "/coriginal_img.jpg";// 相机原始图片
    private static String c_cropIMG = "/ccrop_img.jpg";//相机剪裁图片

    private static String g_originalIMG = "/goriginal_img.jpg";// 相册原始图片
    private static String g_cropIMG = "/gcrop_img.jpg";//相册剪裁图片

    /**
     * 判断Sd卡是否可用
     *
     * @param context
     * @return
     */
    private static boolean isSdCardUsed(Context context) {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 相机 原始图片路径
     *
     * @param context
     * @return
     */
    public static File createCameraOrgFile(Context context) {
        File file = new File(getAlbumFile(context) + c_originalIMG);
        DebugUtil.d(TAG, "相机-原始图片路径：" + getAlbumFile(context) + c_originalIMG);
        //如果父目录没有存在，则创建父目录
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        //如果文件没有存在，则创建
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

        }
        return file;
    }

    /**
     * 相册 原始图片路径
     *
     * @param context
     * @return
     */
    public static File createGalleryOrgFile(Context context) {
        File file = new File(getAlbumFile(context) + g_originalIMG);
        DebugUtil.d(TAG, "相册-原始图片路径：" + getAlbumFile(context) + g_originalIMG);
        //如果父目录没有存在，则创建父目录
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        //如果文件没有存在，则创建
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

        }
        return file;
    }

    /**
     * 相机 剪裁图片路径
     *
     * @param context
     * @return
     */
    public static File createCameraCropImageFile(Context context) {
        File file = new File(getAlbumFile(context) + c_cropIMG);
        DebugUtil.d(TAG, "相机-剪裁图片路径：" + getAlbumFile(context) + c_cropIMG);
        //如果父目录没有存在，则创建父目录
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        //如果文件没有存在，则创建
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 相册 剪裁图片路径
     *
     * @param context
     * @return
     */
    public static File createGallerayCropImageFile(Context context) {
        File file = new File(getAlbumFile(context) + g_cropIMG);
        DebugUtil.d(TAG, "相册-剪裁图片路径：" + getAlbumFile(context) + g_cropIMG);
        //如果父目录没有存在，则创建父目录
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        //如果文件没有存在，则创建
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 获取存储根路径
     *
     * @param context
     * @return
     */
    private static File getAlbumFile(Context context) {
        String path;
        if (isSdCardUsed(context)) {
            path = Environment.getExternalStorageDirectory() + albumPath;
        } else {
            path = Environment.getRootDirectory() + albumPath;
        }

        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }
    //*********************************************图片intent设置************************************************

    /**
     * * 相机 剪裁并保存
     *
     * @param photoUri   图像的uri
     * @param width      宽
     * @param height     高
     * @param outputFile 剪切后的图片的保存文件
     * @return
     */
    public static Intent getCameraCropIntent(Uri photoUri, int width, int height, File outputFile) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(photoUri, "image/*");
        intent.putExtra("output", Uri.fromFile(outputFile));
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("return-data", true);//返回大数据，高配手机可这样设置
        intent.putExtra("scale", true);//黑边
        intent.putExtra("scaleUpIfNeeded", true);//黑边
        return intent;
    }

    /**
     * * 相册 剪裁并保存
     *
     * @param photoUri   图像的uri
     * @param width      宽
     * @param height     高
     * @param outputFile 剪切后的图片的保存文件
     * @return
     */
    public static Intent getGalleryCropIntent(Uri photoUri, int width, int height, File outputFile) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(photoUri, "image/*");
        intent.putExtra("output", Uri.fromFile(outputFile));
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("return-data", true);//返回大数据，高配手机可这样设置
        intent.putExtra("scale", true);//黑边
        intent.putExtra("scaleUpIfNeeded", true);//黑边
        return intent;
    }

    /**
     * 相机 intent设置
     *
     * @param context
     * @return
     */
    public static Intent getCameraIntent(Context context) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(createCameraOrgFile(context)));//保存原始图片
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);//返回大数据，高配手机可这样设置
        intent.putExtra("scale", true);//黑边
        intent.putExtra("scaleUpIfNeeded", true);//黑边
        return intent;
    }

    /**
     * 相册 intent设置
     *
     * @param width
     * @param height
     * @return
     */
    public static Intent getGalleryIntent(int width, int height, Context context) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");//存储在手机内部sd卡上
        intent.putExtra("output", Uri.fromFile(createGalleryOrgFile(context)));//保存原始图片
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);// 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", width);// 输出图片大小
        intent.putExtra("outputY", height);
        intent.putExtra("return-data", true);//返回大数据，高配手机可这样设置
        intent.putExtra("scale", true);//黑边
        intent.putExtra("scaleUpIfNeeded", true);//黑边
        return intent;

    }
    //*********************************************图片Uri/file返回************************************************

    /**
     * 返回 相机 原始图片Uri 01
     *
     * @param context
     * @return
     */
    public static Uri getCamreraOrgFileUri(Context context) {
        return Uri.fromFile(createCameraOrgFile(context));

    }

    /**
     * 返回 相册 原始图片Uri 02
     *
     * @param context
     * @return
     */
    public static Uri getGalleryOrgFileUri(Context context) {
        return Uri.fromFile(createGalleryOrgFile(context));

    }

    /**
     * 返回 相机 剪裁图片Uri
     *
     * @param context
     * @return
     */
    public static Uri getCameraCropImageFileUri(Context context) {
        return Uri.fromFile(createCameraCropImageFile(context));

    }

    /**
     * 返回 相机 剪裁图片Uri
     *
     * @param context
     * @return
     */
    public static Uri getGalleryCropImageFileUri(Context context) {
        return Uri.fromFile(createGallerayCropImageFile(context));

    }

    /**
     * 返回 相机 剪裁图片file
     *
     * @param context
     * @return
     */
    public static File getCameraCropImageFile(Context context) {
        return createCameraCropImageFile(context);

    }

    /**
     * 返回 相册 剪裁图片file
     *
     * @param context
     * @return
     */
    public static File getGalleryCropImageFile(Context context) {
        return createGallerayCropImageFile(context);

    }


    /**
     * Uir转bitmap
     *
     * @param context
     * @param uri
     * @return
     */
    public static Bitmap getBitmapFromUri(Context context, Uri uri) {
        try {
            return MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

}
