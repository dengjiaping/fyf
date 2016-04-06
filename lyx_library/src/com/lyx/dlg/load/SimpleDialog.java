package com.lyx.dlg.load;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.view.LayoutInflater;
import android.view.View;

import com.example.framework_lyx.R;

public class SimpleDialog implements LoadingDialog {
	
	private Context context ;
	
	private AlertDialog loadingDlg ;
	private AlertDialog failDlg ;
	
	private RetryListener retryListener ;
	private CancelListener cancleListener ;

	public SimpleDialog(Context context) {
		this.context = context ;
	}

	@Override
	public void showLoading() {
		
		closeFail()  ;
		
		if(context==null) return ;
		
		LayoutInflater inflater=LayoutInflater.from(context);
		final View viewProcess=inflater.inflate(R.layout.d_load_simple, null);
		if(loadingDlg == null){
			loadingDlg = new AlertDialog.Builder(context).setTitle("加载中").setCancelable(false).setView(viewProcess).create();
			loadingDlg.setOnCancelListener(new OnCancelListener(){
				public void onCancel(DialogInterface dialog) {
					dismiss();
				}
			});
		}
		loadingDlg.show()  ;
	}

	@Override
	public void retry() {
		dismiss() ;
		showLoading() ;
		 backRetry();
	}

	@Override
	public void showFail(String failMsg) {
		
		closeLoading()  ;
		
		if(context==null) return ;
		
		if(failDlg == null){
			Builder builder = new AlertDialog.Builder(context).setCancelable(false);
			builder.setIcon(R.drawable.ic_d_alert);
			builder.setTitle("提示");
			builder.setMessage(failMsg);
			builder.setPositiveButton("重试", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					retry() ;
				}
			});
			builder.setNegativeButton("取消",  new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dismiss() ;
					backCancel() ;
				}
			});
			failDlg =builder.create()  ;
		}
		failDlg.show();
	}

	@Override
	public void dismiss() {
		closeLoading() ;
		closeFail() ;
	}
	
	public void closeLoading(){
		if(loadingDlg!=null)
			loadingDlg.dismiss();
	}

	@Override
	public void closeFail() {
		if(failDlg!=null)
			failDlg.dismiss();
	}

	public void setRetryListener(RetryListener retryListener) {
		this.retryListener = retryListener;
	}
	
	private void backRetry(){
		if(this.retryListener!=null){
			retryListener.retry();
		}
	}
	
	private void backCancel(){
		if(this.cancleListener!=null){
			cancleListener.onCancel() ;
		}
	}

	@Override
	public void setCancelListener(CancelListener listener) {
		// TODO Auto-generated method stub
		this.cancleListener = listener ;
	}
}
