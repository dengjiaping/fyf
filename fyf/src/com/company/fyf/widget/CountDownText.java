package com.company.fyf.widget;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.company.fyf.utils.Logger;

public class CountDownText extends TextView implements OnClickListener{
	
	private final int MAX_SECOND = 60 ;
	
	private final String TEXT_DEFAULT = "发送验证码" ;
	
	private OnClickListener onClickListener ; 
	
	public CountDownText(Context context) {
		super(context);
		init(context,null) ;
	}

	public CountDownText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context,attrs) ;
	}

	public CountDownText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context,attrs) ;
	}

	private void init(Context context, AttributeSet attrs) {
		// TODO Auto-generated method stub
		setOnClickListener(this) ;
		setText(TEXT_DEFAULT) ;
	}

	@Override
	public void onClick(View v) {
		setEnabled(false) ;
		timer.start() ;
		if(onClickListener != null) onClickListener.onClick();
	} 
	
	private CountDownTimer timer = new CountDownTimer(MAX_SECOND * 1000,1000) {
		
		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
			Logger.d("CountDownText", "onTick millisUntilFinished = " + millisUntilFinished) ;
			setText(String.valueOf(millisUntilFinished / 1000)) ;
		}
		
		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			Logger.d("CountDownText", "onFinish") ;
			setEnabled(true) ;
			setText(TEXT_DEFAULT) ;
		}
	};
	
	public void setOnClickListener(OnClickListener onClickListener) {
		this.onClickListener = onClickListener;
	}

	public interface OnClickListener{
		public void onClick() ;
	}


	
}
