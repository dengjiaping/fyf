package com.company.fyf.widget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextClock;

import com.company.fyf.R;

public class ClearInputView extends ClearLinearLayout {
	
	private EditText editText ;
	private String hint ;
	private int icon ;
	private int type ; // 0 = 普通的；1 = 手机号 ；2 = 密码
	
	public ClearInputView(Context context) {
		super(context);
		initView(context,null) ;
	}
	
	public ClearInputView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context,attrs) ;
	}


	@SuppressLint("NewApi")
	public ClearInputView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context,attrs) ;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void initView(Context context, AttributeSet attrs) {
		
		LayoutInflater.from(context).inflate(R.layout.w_clear_input_view, this, true) ;
		
		if (attrs != null) {
			TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.ClearInputView); 
			hint = a.getString(R.styleable.ClearInputView_hint); 
			icon = a.getResourceId(R.styleable.ClearInputView_icon, 0);
			type = a.getInt(R.styleable.ClearInputView_type,0) ;
			a.recycle();
		}
		
		/*
		 * android:layout_width="match_parent"
		    android:layout_height="@dimen/space_px_138"
		    android:layout_marginLeft="@dimen/space_px_60"
		    android:layout_marginRight="@dimen/space_px_60"
		    android:background="@drawable/bg_input_simple_01"
		    android:gravity="center_vertical"
		    android:orientation="horizontal"
		 */
		setGravity(Gravity.CENTER_VERTICAL) ;
		setBackgroundResource(R.drawable.bg_input_simple_01) ;
		setOrientation(HORIZONTAL) ;
		
		editText = (EditText) findViewById(R.id.editText) ;
		editText.setHint(hint) ;
		
		if(1 == type){ //手机号
			editText.setInputType(EditorInfo.TYPE_CLASS_PHONE) ;
			editText.setFilters(new  InputFilter[]{ new  InputFilter.LengthFilter(11)}); 
		}else if(2 == type){//密码
			if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
				editText.setInputType(EditorInfo.TYPE_CLASS_NUMBER | EditorInfo.TYPE_NUMBER_VARIATION_PASSWORD) ;
			}else{
				editText.setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD) ;
			}
			editText.setFilters(new  InputFilter[]{ new  InputFilter.LengthFilter(20)}); 
		}
		
		ImageView imageView = (ImageView) findViewById(R.id.icon) ;
		imageView.setImageResource(icon) ;
	}
	
	public String getText(){
		if(editText == null){
			return "" ;
		}
		return editText.getText().toString() ;
	}
	public void setText(String text){
		if(editText == null || TextUtils.isEmpty(text)){
			return ;
		}
		editText.setText(text) ;
	}
	public void addTextChangedListener(TextWatcher watcher){
		if(editText == null){
			return  ;
		}
		editText.addTextChangedListener(watcher) ;
	}

}
