package com.lyx.utils.legal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class LegalCheck {

	public static boolean doCheck(Context context, Legal legal, String lable,
			String value) {
		if (legal.check(value)) {
			return true;
		}
		showInlegalDlg(context, legal.warnMsg(lable));
		return false;
	}

	public static boolean doCheck(Context context, Legal legal, String... array) {
		
		if(array.length%2 != 0){
			throw new RuntimeException(">>>>>>>>LegalCheck doCheck array长度需要为偶数") ;
		}
		for(int i =0 ;i<array.length ; i++){
			String lable = array[i++] ;
			if (!legal.check(array[i])) {
				showInlegalDlg(context,legal.warnMsg(lable)) ;
				return false;
			}
		}
		return true;
	}

	public static boolean doCheck(Context context, Legal legal, String lable,
			String value, String warnMsg) {
		if (legal.check(value)) {
			return true;
		}
		showInlegalDlg(context, warnMsg);
		return false;
	}

	public static void showInlegalDlg(Context context, String warnMsg) {
		new AlertDialog.Builder(context).setTitle("提示").setMessage(warnMsg)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int arg1) {
						dialog.dismiss();
					}
				}).show();

	}

}
