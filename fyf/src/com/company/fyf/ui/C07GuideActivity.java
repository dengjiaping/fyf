package com.company.fyf.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.company.fyf.R;
import com.company.fyf.db.CommPreference;

public class C07GuideActivity extends B01BaseActivity {

	private List<View> views = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_c07_layout);
		initViews();
		ViewPager viewpager = (ViewPager) findViewById(R.id.viewPager);
		viewpager.setAdapter(new PagerAdapter() {

			@Override
			public int getCount() {
				return views.size();
			}

			@Override
			public boolean isViewFromObject(View view, Object object) {
				return (view == object);
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				View view = views.get(position);
				container.addView(view);
				return view;

			}

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				container.removeView( (View) object);
			}

			@Override
			public int getItemPosition(Object object) {
				return POSITION_NONE;
			}
		});
	}

	private void initViews() {
		// TODO Auto-generated method stub
		View page01 = LayoutInflater.from(this).inflate(R.layout.i_c07_viewpager_page01, null) ;
		View page02 = LayoutInflater.from(this).inflate(R.layout.i_c07_viewpager_page02, null) ;
		View page03 = LayoutInflater.from(this).inflate(R.layout.i_c07_viewpager_page03, null) ;
		View experience = page03.findViewById(R.id.experience) ;
		experience.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showActivity(M01MainActivity.class) ;
				CommPreference.INSTANCE.setGuideNoShow() ;
				finish() ;
			}
		}) ;
		views.add(page01);
		views.add(page02);
		views.add(page03);
	}

}
