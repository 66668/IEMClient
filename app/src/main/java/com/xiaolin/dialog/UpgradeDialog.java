package com.xiaolin.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.xiaolin.R;
import com.xiaolin.utils.Utils;


/**
 * 版本升级 弹窗
 */

public class UpgradeDialog extends Dialog implements View.OnClickListener{

	private Context context;
	private UpdateAppDialogCallBack callBack;
	private boolean ifForce;

	public UpgradeDialog(Context context, String content, boolean ifForce, UpdateAppDialogCallBack callBack) {
		super(context, R.style.LoadingDialog);
		this.context = context;
		this.callBack = callBack;
		this.ifForce = ifForce;
		init(content);
	}

	public interface UpdateAppDialogCallBack{

		public void confirm();
		public void cancel();

	}
	private void init(String content) {
		String title = context.getString(R.string.update_tips);;
		String confirmText = context.getResources().getString(R.string.update_now);
		String cancelText = context.getResources().getString(R.string.no_update_this_time);
		if(ifForce){
			initForce(title,content);
		}else{
			init(title,content,confirmText,cancelText);
		}
	}
	
	private void init(String title, String content, String confirmText, String cancelText) {
		
		View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_upgrade, null);
		
		TextView titleView = (TextView) dialogView.findViewById(R.id.dialog_title);
		TextView contentView = (TextView) dialogView.findViewById(R.id.dialog_content);
		TextView confirmTextView = (TextView) dialogView.findViewById(R.id.confirm_btn);
		TextView cancelTextView = (TextView) dialogView.findViewById(R.id.cancel_btn);
		
		
		if(TextUtils.isEmpty(title)){
			titleView.setVisibility(View.GONE);
		}else{
			titleView.setText(title);
		}
		contentView.setText(content);
		confirmTextView.setText(confirmText);
		cancelTextView.setText(cancelText);
		
		confirmTextView.setOnClickListener(this);
		cancelTextView.setOnClickListener(this);
		
		setContentView(dialogView);
		
	}
	
	private void initForce(String title, String content){
		
		View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_force_update_app, null);//立即升级
		
		TextView titleView = (TextView) dialogView.findViewById(R.id.dialog_title);
		TextView contentView = (TextView) dialogView.findViewById(R.id.dialog_content);
		TextView confirmTextView = (TextView) dialogView.findViewById(R.id.confirm_btn);
		
		
		if(TextUtils.isEmpty(title)){
			titleView.setVisibility(View.GONE);
		}else{
			titleView.setText(title);
		}
		contentView.setText(content);
		
		confirmTextView.setOnClickListener(this);
		setCancelable(!ifForce);
		setContentView(dialogView);
		
	}
	
	
	
	@Override
	public void show() {
		super.show();
		WindowManager.LayoutParams lp = this.getWindow().getAttributes();
		int screenWidth = (int)(Utils.getScreenWidth((Activity)context));
		lp.width = screenWidth - screenWidth * 60 / 640;
		lp.gravity = Gravity.CENTER;
		this.getWindow().setAttributes(lp);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.confirm_btn:
			if(!ifForce){
				dismiss();
			}
			callBack.confirm();
			break;
		case R.id.cancel_btn:
			dismiss();
			break;
		default:
			break;
		}
	}
	
}
