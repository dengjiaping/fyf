package com.company.fyf.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.company.fyf.R;

public class F01DiffRecoveryDetailActivity extends B01BaseActivity {
	
	public static final String PARAM_INT_POSITION = "param_integer_position";
	
	private ViewPager viewPager ;
	
	private RadioGroup radioGroup ;
	private RadioGroup radioGroupLine ;
	
	private List<View> mViews = new ArrayList<View>();
	
	private int position = 0 ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_f01_layout) ;
		onGetIntentData();
		initView() ;
	}

	private void initView() {
		// TODO Auto-generated method stub
		viewPager = (ViewPager) findViewById(R.id.viewPager) ;
		for (int i = 0; i < 3; i++) {
			View v = LayoutInflater.from(this).inflate(R.layout.i_f01_viewpager, null) ;
			ImageView imageView = (ImageView) v.findViewById(R.id.imageView) ;
			TextView textView = (TextView) v.findViewById(R.id.textView) ;
			if(i == 0){
				imageView.setImageResource(R.drawable.ic_f01_iv_kitchen);
				imageView.setBackgroundColor(getResources().getColor(R.color.theme_kitchen));
				textView.setText(getString(R.string.kitchen_description)) ;
			}else if(i == 1){
				imageView.setImageResource(R.drawable.ic_f01_iv_other);
				imageView.setBackgroundColor(getResources().getColor(R.color.theme_other));
				textView.setText(getString(R.string.other_description)) ;
			}else if(i == 2){
				imageView.setImageResource(R.drawable.ic_f01_iv_recyclable);
				imageView.setBackgroundColor(getResources().getColor(R.color.theme_recyclable));
				textView.setText(getString(R.string.recoverable_description)) ;
			}
			mViews.add(v) ;
		}
		viewPager.setAdapter(new PagerAdapter() {
			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager) container).removeView(mViews.get(position));
			}
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return mViews.size();
			}
			@Override
			public Object instantiateItem(View container, int position) {
				// TODO Auto-generated method stub
				((ViewPager) container).addView(mViews.get(position));
				return mViews.get(position);
			}
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0 == arg1;
			}
		});
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int item) {
				setRadioGroupId(idFromItem(item)) ;
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		}) ;
		
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup) ;
		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				setViewPagerCurrentItem(itemFromId(checkedId)) ;
				radioGroupLine.check(lineIdFromId(checkedId)) ;
			}
		}) ;
		radioGroupLine = (RadioGroup) findViewById(R.id.radioGroupLine) ;
		radioGroup.check(idFromItem(position));
	}
	
	private int itemFromId(int checkedId){
		switch (checkedId) {
		case R.id.rb_kitchen:
			
			return 0;
		case R.id.rb_other:
					
			return 1;
		case R.id.rb_recoverable:
			
			return 2;
		default:
			throw new IllegalArgumentException("error checkid");
		}
	}
	
	private int idFromItem(int item){
		switch (item) {
		case 0:
			
			return R.id.rb_kitchen;
		case 1:
					
			return R.id.rb_other;
		case 2:
			
			return R.id.rb_recoverable;
		default:
			throw new IllegalArgumentException("error item");
		}
	}
	
	private int lineIdFromId(int checkedId){
		switch (checkedId) {
		case R.id.rb_kitchen:
			
			return R.id.rb_kitchen_line ;
		case R.id.rb_other:
					
			return  R.id.rb_other_line;
		case R.id.rb_recoverable:
			
			return  R.id.rb_recoverable_line;
		default:
			throw new IllegalArgumentException("error checkid") ;
		}
	}
	
	private void setViewPagerCurrentItem(int item){
		
		if(item == viewPager.getCurrentItem()){
			return ;
		}
		
		viewPager.setCurrentItem(item) ;
	}
	
	private void setRadioGroupId(int id){
		
		if(id == radioGroup.getCheckedRadioButtonId()){
			return ;
		} 
		radioGroupLine.check(lineIdFromId(id)) ;
		radioGroup.check(id) ;
	}
}
