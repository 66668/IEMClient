package com.xiaolin.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.xiaolin.R;


/**
 * 弹出上传图片选择对话框 相机或者照相
 */
public class CameraChooseDialog extends Dialog implements View.OnClickListener {

    private LinearLayout layout_photo;
    private LinearLayout layout_gallery;

    private ClickCallback callback;

    public CameraChooseDialog(Context context, ClickCallback callback) {
        super(context, R.style.camera_select_dialog);
        setContentView(R.layout.dialog_cemera_choose);
        layout_photo = this.findViewById(R.id.ll_photo);
        layout_photo.setOnClickListener(this);
        layout_gallery = this.findViewById(R.id.ll_gallery);
        layout_gallery.setOnClickListener(this);
        this.callback = callback;

        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setGravity(Gravity.CENTER);

    }

    public interface ClickCallback {
        /**
         * 点击拍照条目的回调
         */
        void PhotoCallback();

        /**
         * 点击从相册选择的回调
         */
        void galleryCallback();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_photo:
                callback.PhotoCallback();
                break;
            case R.id.ll_gallery:
                callback.galleryCallback();
                break;

            default:
                break;
        }
    }
}
