package com.company.fyf.widget;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.company.fyf.R;
import com.lyx.utils.CommUtil;

public class TitleBar extends RelativeLayout{
	
	private final float PADDING_TOP_BUTTOM_DP =  0f ;
	private final float PADDING_LEFT_RIGHT_DP =  13.33f;
	
	private TextView tv_title = null ;
	private View iv_leftBtn = null ;
	private View tv_rightBtn = null ;
	
	public TitleBar(Context context) {
		super(context);
		init(context,null) ;
	}

	public TitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs) ;
	}
	
	public TitleBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

	private void init(final Context context, AttributeSet attrs) {
		LayoutInflater.from(context).inflate(R.layout.w_titlebar, this) ;
		setId(R.id.titlebar) ;
		setBackgroundColor(context.getResources().getColor(R.color.theme_blue)) ;
		setPadding(0,
				   0, 
				   (int)CommUtil.dp2px(context,PADDING_LEFT_RIGHT_DP), 
				   0) ;
//		ViewGroup.LayoutParams params = getLayoutParams() ;
//		params.height = (int) CommUtil.dp2px(context, 44.67f) ;
//		setLayoutParams(getLayoutParams()) ;
		
		tv_title = (TextView) findViewById(R.id.tv_title) ;
		iv_leftBtn = (ImageView) findViewById(R.id.iv_leftBtn) ;
		tv_rightBtn = (TextView) findViewById(R.id.tv_rightBtn) ;
		
		if (attrs != null) {
			setTitle(attrs.getAttributeValue(null, "title"));
			setMenuBtn(attrs.getAttributeValue(null, "menu"));
			setBackBtnVisiable(attrs.getAttributeBooleanValue(null, "backvisiable", true)) ;
		}
		
		iv_leftBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(context instanceof Activity) ((Activity)context).finish() ; 
			}
		}) ;
	}
	
	public void setTitle(String title){
		if(tv_title != null && !TextUtils.isEmpty(title)){
			tv_title.setText(title) ;
		}
	}
	
	public void setMenuBtn(String menuTxt,View.OnClickListener listener){
		setMenuBtn(menuTxt) ;
		setMenuBtnOnClickListener(listener) ;
	}
	
	public void setMenuBtn(String menuTxt){
		if(TextUtils.isEmpty(menuTxt)){
			return ;
		}
		
		if(tv_rightBtn != null){
			((TextView)tv_rightBtn).setText(menuTxt) ;
		}
		showMenuBtn() ;
	}
	
	public void setMenuBtn(int resId,View.OnClickListener listener){
		
		ImageView imageView = new ImageView(getContext()) ;
		imageView.setImageResource(resId) ;
		setMenuBtn(imageView,(RelativeLayout.LayoutParams)tv_rightBtn.getLayoutParams()) ;
		
		setMenuBtnOnClickListener(listener) ;
	}
	
	public void setMenuBtn(View menuView,RelativeLayout.LayoutParams params){
		removeView(tv_rightBtn) ;
		
		params.addRule(RelativeLayout.CENTER_VERTICAL);
		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		addView(menuView, params) ;
		
		tv_rightBtn = menuView ;
		showMenuBtn() ;
	}
	
	public void setMenuBtn(View menuView,float width,float height){
		
		RelativeLayout.LayoutParams params = 
				new RelativeLayout.LayoutParams(
						(int)CommUtil.dp2px(getContext(), width),
						(int)CommUtil.dp2px(getContext(), height)) ;
		setMenuBtn(menuView,params);
	}
	
	public void showMenuBtn(){
		if(tv_rightBtn != null){
			tv_rightBtn.setVisibility(View.VISIBLE) ;
		}
	}
	
	public void hideMenuBtn(){
		if(tv_rightBtn != null){
			tv_rightBtn.setVisibility(View.GONE) ;
		}
	}
	
	public void setBackBtnVisiable(boolean visiable){
		if(visiable){
			tv_title.setVisibility(View.VISIBLE) ;
		}else{
			tv_title.setVisibility(View.GONE) ;
		}
	}
	
	public void setMenuBtnOnClickListener(View.OnClickListener listener){
		if(tv_rightBtn != null){
			tv_rightBtn.setOnClickListener(listener) ;
		}
	}
	
	public void replaceBackBtn(int resId,View.OnClickListener listener){
		
		((ImageView)iv_leftBtn).setImageResource(resId) ;
		
		iv_leftBtn.setOnClickListener(listener) ;
		
	}
	
	public void replaceBackBtn(String title,View.OnClickListener listener){
		
		TextView textView = new TextView(getContext()) ;
		textView.setText(title) ;
		replaceBackBtn(textView) ;
		
		textView.setOnClickListener(listener) ;
		
	}
	
	public void replaceBackBtn(View view){
		
		RelativeLayout.LayoutParams params = (LayoutParams) iv_leftBtn.getLayoutParams() ;
		
		replaceBackBtn(view, params) ;
	}
	
	public void replaceBackBtn(View view,RelativeLayout.LayoutParams params){
		
		removeView(iv_leftBtn) ;
		
		addView(view, params) ;
		
		iv_leftBtn = view ;
	}
	
	public View getRightView(){
		return tv_rightBtn ;
	}

	

}
