package com.lyx.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

public class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)  ;
	}

	protected boolean showActivity(String clsName) {
		try {
			showActivity(Class.forName(clsName));
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	public void showActivity(Class<?> cls) {
		showActivity(cls, new Bundle());
	}

	protected void showActivity(Class<?> cls, Bundle param) {
		showActivity(cls, param, 0);
	}

	protected void showActivity(Class<?> cls, Bundle param, int flags) {
		Intent intent = new Intent();
		if (param != null)
			intent.putExtras(param);
		intent.setClass(getApplicationContext(), cls);
		if (flags != 0)
			intent.setFlags(flags);

		startActivity(intent);
	}

	protected void showToast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	protected void showToast(String msg, int duration) {
		Toast.makeText(this, msg, duration).show();
	}

	protected void showWarnDlg(String msg) {
		new AlertDialog.Builder(this).setTitle("提示").setMessage(msg)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dlg, int arg1) {
						dlg.dismiss();
					}
				}).show();
	}
}
