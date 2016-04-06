package com.lyx.widget.selText;

public interface SelDlg<T extends SelectText> {

	public void show();

	public void dismiss();

	public void setSelectListener(SelectListener<T> listener);

}
