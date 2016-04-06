package com.lyx.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lyx.utils.CommUtil;

public abstract class TitleBar extends RelativeLayout{
	
	public static final String TAG = "TitleBar";
	
	public static final int ID = 888;
	
	/**dp值*/
	private final int Default_LAYOUT_PADDING = 5 ;
	
	private final int Default_CENTER_TEXT_SIZE = 23 ;
	private final int Default_RIGHT_TEXT_SIZE = 18 ;
	private final int Default_LEFT_TEXT_SIZE = 18 ;
	
	private final int Default_LAYOUT_BG_COLOR = Color.WHITE ;
	
	protected int layout_padding_left = Default_LAYOUT_PADDING; 
	protected int layout_padding_right = Default_LAYOUT_PADDING; 
	protected int layout_padding_bottom = 0; 
	protected int layout_padding_top = 0; 
	
	protected int center_text_size = Default_CENTER_TEXT_SIZE ;
	protected int left_text_size = Default_RIGHT_TEXT_SIZE ;
	protected int right_text_size = Default_LEFT_TEXT_SIZE ;
	
	protected int center_text_color = Color.WHITE ;
	protected int left_text_color = Color.WHITE ;
	protected int right_text_color = Color.WHITE ;
	
	protected int layout_bg_color = Default_LAYOUT_BG_COLOR  ;
	
	private Context context ;
	
	protected View left ;
	protected View center ;
	protected View right ;
	
	public TitleBar(Context context) {
		super(context);
		init(context, null) ;
	}
	
	public TitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs) ;
	}
	private void init(final Context context, AttributeSet attrs) {
		this.context = context ;
		this.setId(ID) ; 
		configLayoutPadding();
		int left = (int) CommUtil.dp2px(context,layout_padding_left) ;
		int top = (int) CommUtil.dp2px(context,layout_padding_top) ;
		int right = (int) CommUtil.dp2px(context,layout_padding_right) ;
		int bottom = (int) CommUtil.dp2px(context,layout_padding_bottom) ;
		this.setPadding(left, top, right, bottom);
		addDefaultLeftView() ;
		setLeftOnClick(new OnClickListener() {
			public void onClick(View v) {
				if(context instanceof Activity){
					((Activity)context).finish();
				}
			}
		});
		if (attrs != null) {
			setLeft(attrs.getAttributeValue(null, "left"));
			setRight(attrs.getAttributeValue(null, "right"));
			setCenter(attrs.getAttributeValue(null, "center"));
		}
		configTextSizeAndColor();
		
		configBg() ;
	}
	
	protected void configBg() {}
	protected void configTextSizeAndColor() {} ;
	protected abstract void configLayoutPadding()  ;
	protected abstract void addDefaultLeftView() ;
	
	public void setLeft(String left){
		if(TextUtils.isEmpty(left))
			return ;
		TextView textView = new TextView(context) ;
		textView.setTextColor(left_text_color);
		textView.setTextSize(left_text_size);
		textView.setText(left); 
		addLeft(textView);
	}
	public void setLeft(View view){
		addLeft(view);
	}
	/**需要配置好bitmap的尺寸传入*/
	public void setLeft(Bitmap bit,int bitW,int bitH){
		ImageView imageView = new ImageView(context) ;
		imageView.setImageBitmap(bit);
		addLeft(imageView,bitW , bitH);
	}
	/**需要配置好bitmap的尺寸传入*/
	public void setLeft(Bitmap bit,String left,int space,int bitW,int bitH){
		LinearLayout ll = new LinearLayout(context) ;
		
		ImageView imageView = new ImageView(context) ;
		LinearLayout.LayoutParams lp01= new LinearLayout.LayoutParams(
				bitW , bitH) ;
		imageView.setImageBitmap(bit);
		
		TextView textView = new TextView(context) ;
		LinearLayout.LayoutParams lp02= new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT) ;
		lp02.leftMargin = (int) CommUtil.dp2px(context,space) ;
		textView.setTextColor(left_text_color);
		textView.setTextSize(left_text_size);
		textView.setText(left); 
		
		ll.addView(imageView,lp01);
		ll.addView(textView,lp02);
		
		ll.setGravity(Gravity.CENTER_VERTICAL)  ;
		addLeft(ll);
	}
	
	/**需要配置好bitmap的尺寸传入*/
	public void setRight(String right){
		
		if(TextUtils.isEmpty(right))
			return ;
		
		TextView textView = new TextView(context) ;
		textView.setTextColor(right_text_color);
		textView.setTextSize(right_text_size);
		textView.setText(right); 
		addRight(textView);
	}
	public void setRight(View view){
		addRight(view);
	}
	public void setRight(Bitmap bit,int w,int h){
		ImageView imageView = new ImageView(context) ;
		imageView.setImageBitmap(bit);
		addRight(imageView, w, h);
	}
	public void setCenter(String center){
		
		if(TextUtils.isEmpty(center))
			return ;
		
		TextView textView = new TextView(context) ;
		textView.setTextColor(center_text_color);
		textView.setTextSize(center_text_size);
		textView.setText(center); 
		textView.setGravity(Gravity.CENTER)  ;
//		textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
		addCenter(textView);
	}
	public void setCenter(View view){
		addCenter(view);
	}
	
	private void addLeft(View v){
		addLeft(v,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT); 
	}
	
	private void addLeft(View v,int w,int h){
		
		if(left != null)
			this.removeViewInLayout(left);
		
		left = v ;
		RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams (w,h) ;
		rllp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		rllp.addRule(RelativeLayout.CENTER_VERTICAL);
		addView(v,rllp);
	}
	private void addCenter(View v){
		
		if(center != null)
			this.removeViewInLayout(center);
		
		center = v ;
		RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams (
				LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT) ;
		rllp.addRule(RelativeLayout.CENTER_IN_PARENT);
		addView(v,rllp);
	}
	private void addRight(View v){
		addRight(v,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT); 
	}
	private void addRight(View v,int w,int h){
		
		if(right != null)
			this.removeViewInLayout(right);
		
		right = v ;
		RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams (w,h) ;
		rllp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		rllp.addRule(RelativeLayout.CENTER_VERTICAL);
		addView(v,rllp);
	}
	
	public void setLeftOnClick(View.OnClickListener listener){
		if(left != null)
			left.setOnClickListener(listener);
	}
	public void setRightOnClick(View.OnClickListener listener){
		if(right != null)
			right.setOnClickListener(listener);
	}
	public void setCenterOnClick(View.OnClickListener listener){
		if(center != null)
			center.setOnClickListener(listener);
	}
	
	public void setLeftHide(boolean boo){
		if(left != null && boo){
			left.setVisibility(View.GONE);
		}
	}
	public void setRightHide(boolean boo){
		if(right != null && boo){
			right.setVisibility(View.GONE);
		}
	}
	public void setCenterHide(boolean boo){
		if(center != null && boo){
			center.setVisibility(View.GONE);
		}
	}

}
