package com.xiaolin.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kelvin on 16/4/20.
 */
public class CameraGalleryUtils {
    public static String PhotoDir = Environment.getExternalStorageDirectory() + "/PhotoDemo/image/";
    public static final String TAG = "a";
    public static final int REQUEST_CODE_FROM_CAMERA = 5001;
    public static final int REQUEST_CODE_FROM_ALBUM = 5002;
    public static final int REQUEST_CODE_CROP = 5003;

    /**
     * 存放拍照图片的uri地址
     */
    private static Uri imageUriFromALBUM;
    private static Uri imageUriFromCamera;

    /**
     * 记录是处于什么状态：拍照or相册
     */
    private static int state = 0;

    /**
     * 显示获取照片不同方式对话框
     */
    public static void showImagePickDialog(final Activity activity) {

        String title = "选择获取图片方式";
        String[] items = new String[]{"拍照", "相册"};

        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        switch (which) {
                            case 0:
                                state = 1;
                                pickImageFromCamera(activity);
                                break;
                            case 1:
                                state = 2;
                                pickImageFromAlbum(activity);
                                break;
                            default:
                                break;
                        }
                    }
                })
                .show();
    }


    /**
     * 打开本地相册选取图片
     */
    public static void pickImageFromAlbum(final Activity activity) {
        Log.d(TAG, "打开相册--操作后回调onActivityResult");

        //隐式调用，可能出现多种选择
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        activity.startActivityForResult(intent, REQUEST_CODE_FROM_ALBUM);

        /**
         Intent intent = new Intent();
         intent.setAction(Intent.ACTION_PICK);
         intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
         activity.startActivityForResult(intent,REQUEST_CODE_FROM_ALBUM);
         */
    }


    /**
     * 打开相机拍照获取图片
     */
    public static void pickImageFromCamera(final Activity activity) {

        imageUriFromCamera = getImageUri();

        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriFromCamera);    //指定拍照的图片存在imageUriFromCamera下,如果直接在返回时使用getData()获取的是压缩过的Bitmap数据
        activity.startActivityForResult(intent, REQUEST_CODE_FROM_CAMERA);
    }


    /**
     * 根据指定目录产生一条图片Uri
     */
    private static Uri getImageUri() {
        String imageName = new SimpleDateFormat("yyMMddHHmmss").format(new Date()) + ".jpg";
        String path = PhotoDir + imageName;
        return getUriFromFilePath(path);
    }

    /**
     * 复制一条Uri，避免因为操作而对原图片产生影响
     */
    public static void copyImageUri(Activity activity, Uri uri) {

        String imageName = new SimpleDateFormat("yyMMddHHmmss").format(new Date()) + ".jpg";
        String copyPath = PhotoDir + imageName;
        copyfile(getRealFilePath(activity, uri), copyPath, true);
        imageUriFromALBUM = getUriFromFilePath(copyPath);
    }

    /**
     * 删除一条图片Uri
     */
    public static void deleteImageUri(Context context, Uri uri) {
        context.getContentResolver().delete(uri, null, null);
    }


    /**
     * 裁剪图片返回
     */
    public static void cropImageUri(Activity activity, Uri uri, int outputX, int outputY) {

        Intent intent = new Intent("com.android.camera.action.CROP");

        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection

        activity.startActivityForResult(intent, REQUEST_CODE_CROP);

    }


    /**
     * 根据状态返回图片Uri
     */
    public static Uri getCurrentUri() {

        if (state == 1) {
            return imageUriFromCamera;
        } else if (state == 2) {
            return imageUriFromALBUM;
        } else
            return null;
    }

    /**
     * 将Uri转换成Bitmap
     */
    public static Bitmap decodeUriAsBitmap(Context context, Uri uri) {

        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        return bitmap;
    }


    /**
     * 将Uri转为路径地址
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri)
            return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }


    /**
     * 将路径地址转换为Uri
     */
    public static Uri getUriFromFilePath(final String path) {

        return Uri.fromFile(new File(path));
    }

    /**
     * @param fromPath 被复制的文件路径
     * @param toPath   复制的目录文件路径
     * @param rewrite  是否重新创建文件
     *                 <p>
     *                 <p>文件的复制操作方法
     */
    public static void copyfile(String fromPath, String toPath, Boolean rewrite) {

        File fromFile = new File(fromPath);
        File toFile = new File(toPath);

        if (!fromFile.exists()) {
            return;
        }
        if (!fromFile.isFile()) {
            return;
        }
        if (!fromFile.canRead()) {
            return;
        }
        if (!toFile.getParentFile().exists()) {
            toFile.getParentFile().mkdirs();
        }
        if (toFile.exists() && rewrite) {
            toFile.delete();
        }

        try {
            FileInputStream fosfrom = new FileInputStream(fromFile);
            FileOutputStream fosto = new FileOutputStream(toFile);

            byte[] bt = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c);
            }
            //关闭输入、输出流
            fosfrom.close();
            fosto.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
