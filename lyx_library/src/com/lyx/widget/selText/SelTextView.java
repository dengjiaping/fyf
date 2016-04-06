package com.lyx.widget.selText;

import java.util.Map;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class SelTextView extends TextView {
	
	private SelectText selText ;

	public SelTextView(Context context) {
		super(context);
	}

	public SelTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SelTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public SelectText getSelText(){
		return selText;
	}
	public Map<String,String> getMap(){
		return selText.getKeyValue();
	}
	public void setSelText(SelectText selText){
		this.selText = selText;
		if(selText != null)
			this.setText(selText.getViewText());
		else
			this.setText("请选择") ;
	}

}
