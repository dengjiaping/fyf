package com.company.fyf.ui;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.Toast;

import com.company.fyf.R;
import com.company.fyf.notify.IMsg;
import com.company.fyf.notify.INotifyClient;
import com.company.fyf.notify.NotifyCenter;
import com.company.fyf.utils.FyfUtils;
import com.company.fyf.utils.Logger;
import com.company.fyf.widget.DatePickerDialog;
import com.company.fyf.widget.DatePickerDialog.OnDateSetListener;
import com.umeng.analytics.MobclickAgent;

public class B01BaseActivity extends Activity implements INotifyClient {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
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
		overridePendingTransition(R.anim.slide_in_from_right,
				R.anim.slide_out_to_left);
	}

	protected void showActivityForResult(Class<?> cls, int requestCode) {
		showActivityForResult(cls, null, 0, requestCode);
	}

	protected void showActivityForResult(Class<?> cls, Bundle param,
			int requestCode) {
		showActivityForResult(cls, param, 0, requestCode);
	}

	protected void showActivityForResult(Class<?> cls, Bundle param, int flags,
			int requestCode) {
		Intent intent = new Intent();
		if (param != null)
			intent.putExtras(param);
		intent.setClass(getApplicationContext(), cls);
		if (flags != 0)
			intent.setFlags(flags);

		startActivityForResult(intent, requestCode);
		overridePendingTransition(R.anim.slide_in_from_right,
				R.anim.slide_out_to_left);
	}

	public void finish(boolean isAni) {
		super.finish();
		if (isAni) {
			overridePendingTransition(R.anim.slide_in_from_left,
					R.anim.slide_out_to_right);
		}
	}

	@Override
	public void finish() {
		this.finish(true);
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

	protected void showNormalDlg(String msg, String positiveText,
			DialogInterface.OnClickListener positiveClickListener) {
		new AlertDialog.Builder(this).setTitle("提示").setMessage(msg)
				.setPositiveButton(positiveText, positiveClickListener)
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dlg, int arg1) {
						dlg.dismiss();
					}
				}).show();
	}
	
	protected void showNormalDlg2(String msg, String positiveText,
			DialogInterface.OnClickListener positiveClickListener) {
		new AlertDialog.Builder(this).setTitle("提示").setMessage(msg)
				.setPositiveButton(positiveText, positiveClickListener)
				.setCancelable(false)
				.show();
	}

	@SuppressLint("DefaultLocale")
	protected void onGetIntentData() {
		Intent intent = getIntent();
		Class<?> cls = this.getClass();
		Field[] fs = cls.getDeclaredFields();
		for (Field field : fs) {
			String name = field.getName();
			String ss[] = name.split("_");
			if (ss.length != 3) {
				continue;
			}
			if (!"PARAM".equals(ss[0])) {
				continue;
			}
			Field f_value = null;
			try {
				f_value = cls.getDeclaredField(ss[2].toLowerCase());
			} catch (NoSuchFieldException e) {
				continue;
			}
			if (f_value == null) {
				continue;
			}

			Object obj_value = null;
			try {
				if ("STRING".equals(ss[1])) {
					obj_value = intent.getStringExtra((String) field.get(this));
				} else if ("LONG".equals(ss[1])) {
					obj_value = intent
							.getLongExtra((String) field.get(this), 0);
				} else if ("INT".equals(ss[1])) {
					obj_value = intent.getIntExtra((String) field.get(this), 0);
				} else if ("SERIALIZABLE".equals(ss[1])) {
					obj_value = intent.getSerializableExtra((String) field
							.get(this));
				}
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (obj_value == null) {
				continue;
			}
			try {
				f_value.setAccessible(true);
				f_value.set(this, obj_value);
				Logger.d("B01BaseActivity", "param = " + name + "-- value = "
						+ obj_value);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public <T> void onRefresh(IMsg<T> msg) {
	}

	protected void registerNotity(String key) {
		NotifyCenter.register(key, this);
	}

	protected void unRegisterNotity(String key) {
		NotifyCenter.unRegister(key, this);
	}

	protected <T> void showRadioDlg(List<T> list, int checkedItem,
			DialogInterface.OnClickListener listener) {

		String[] ss = new String[list.size()];
		for (int i = 0; i < ss.length; i++) {
			ss[i] = list.get(i).toString();
		}

		new AlertDialog.Builder(this).setSingleChoiceItems(ss, checkedItem,
				listener).show();
	}

	protected <T> void showRadioDlg(CharSequence[] array, int checkedItem,
			DialogInterface.OnClickListener listener) {
		new AlertDialog.Builder(this).setSingleChoiceItems(array, checkedItem,
				listener).show();
	}

	protected void showSoftInput() {
		View view = this.getCurrentFocus();
		if (view == null)
			return;
		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
	}

	protected void hideSoftInput() {
		View view = this.getCurrentFocus();
		if (view == null)
			return;
		((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(view.getWindowToken(),0);
	}
	
	@SuppressLint("NewApi")
	protected void showTimeSelDlg(OnDateSetListener dateSetListener){
		Calendar c =  Calendar.getInstance();
		DatePickerDialog dlg = new DatePickerDialog(this, dateSetListener, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
		if(android.os.Build.VERSION.SDK_INT >= 11){
			dlg.getDatePicker().setMaxDate(c.getTimeInMillis()) ;
		}
		dlg.show() ;
	}
	

}
