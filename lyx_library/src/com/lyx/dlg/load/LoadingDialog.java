package com.lyx.dlg.load;


public interface LoadingDialog {
	
	public void showLoading() ;
	public void closeLoading() ;
	public void retry();
	public void showFail(String failMsg);
	public void closeFail() ;
	public void dismiss();
	public void setRetryListener(RetryListener listener) ;
	public void setCancelListener(CancelListener listener) ;

}
