package com.company.fyf.widget;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.company.fyf.R;
import com.company.fyf.dao.BannerVo;
import com.company.fyf.ui.C01CommWebActivity;
import com.company.fyf.utils.FinalUtils;
import com.lyx.utils.CommUtil;

public class CrouselImage extends FrameLayout {

	private Context context;
	
	private final int PERIOD = 2500 ;

	private ViewPager viewPager;
	private LinearLayout indexLayout;

	private List<BannerVo> dataList = new ArrayList<BannerVo>();
	private List<View> mViews = new ArrayList<View>();
	private final ViewGroup.LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT) ;

	private PagerAdapter adapter;
	
	private Timer timer = null ;

	public CrouselImage(Context context) {
		super(context);
		init(context, null);
	}

	public CrouselImage(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public CrouselImage(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		// TODO Auto-generated method stub
		this.context = context;
		LayoutInflater.from(context).inflate(R.layout.w_crouselimage, this);

		viewPager = (ViewPager) findViewById(R.id.viewPager);
		viewPager.setAdapter(adapter = new PagerAdapter() {
			public void destroyItem(View container, int position, Object object) {
				((ViewPager) container).removeView(mViews.get(position));
			}
			public int getCount() {
				return mViews.size();
			}
			public Object instantiateItem(View container, int position) {
				((ViewPager) container).addView(mViews.get(position),layoutParams);
				return mViews.get(position);
			}
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}
		});
		indexLayout = (LinearLayout) findViewById(R.id.indexLayout);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			public void onPageSelected(int arg0) {
				updateIndexLayout() ;
			}
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			public void onPageScrollStateChanged(int arg0) {
			}
		}) ;
	}

	public void add(BannerVo vo) {
		dataList.add(vo);
		addImageView(vo);
		updateIndexLayout() ;
		checkIsNeedRun() ;
		adapter.notifyDataSetChanged();
	}

	public void add(List<BannerVo> voList) {
		dataList.addAll(voList);
		for (BannerVo dataVo : voList) {
			addImageView(dataVo);
		}
		updateIndexLayout() ;
		checkIsNeedRun() ;
		adapter.notifyDataSetChanged();
	}

	private void updateIndexLayout() {
		// TODO Auto-generated method stub
		if(dataList == null || dataList.size() <= 1){
			indexLayout.setVisibility(View.GONE) ;
		}
		indexLayout.removeAllViewsInLayout() ;
		for(int i = 0 ;i < dataList.size();i++){
			
			ImageView imageView = new ImageView(getContext()) ;
			int curItem = viewPager.getCurrentItem() ;
			
			if(curItem == i){
				imageView.setImageResource(R.drawable.ic_crousel_image_index_checked) ;
			}else{
				imageView.setImageResource(R.drawable.ic_crousel_image_index_uncheck) ;
			}
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT) ;
			params.leftMargin = (int) CommUtil.px2dp(getContext(), 20) ;
			indexLayout.addView(imageView,params) ;
		}
	}
	
	private Handler handler = new Handler(Looper.getMainLooper()){
		public void handleMessage(android.os.Message msg) {
			setPageItemToNext() ;
		};
	} ;
	
	private void checkIsNeedRun(){
		
		if(dataList.size() == 0){
			return ;
		}
		
		if(timer != null){
			stopRun() ;
		}
		
		timer = new Timer() ;
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				handler.sendEmptyMessage(0) ;
			}
		}, PERIOD, PERIOD) ;
	}
	
	private void stopRun(){
		if(timer != null){
			timer.cancel() ;
			timer = null ;
		}
	}
	
	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		checkIsNeedRun() ;
	}
	
	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		stopRun() ;
	}
	
	private void setPageItemToNext(){
		
		int curItem = viewPager.getCurrentItem() ;
		
		if(curItem == dataList.size() - 1){
			curItem = 0 ;
		}else{
			curItem++ ;
		}
		viewPager.setCurrentItem(curItem, true) ;
	}
	
	

	private void addImageView(final BannerVo vo) {
		ImageView view = new ImageView(context);
		view.setScaleType(ScaleType.FIT_XY);
		FinalUtils.getDisplayer().display(view, vo.getPicurl());
		view.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context,C01CommWebActivity.class) ;
				intent.putExtra(C01CommWebActivity.PARAM_URL, vo.getLinkurl()) ;
				intent.putExtra(C01CommWebActivity.PARAM_TITLE, vo.getText()) ;
				context.startActivity(intent) ;
			}
		});
		mViews.add(view) ;
	}
	
	public boolean hasData(){
		if(mViews == null || mViews.size() == 0) return false ;
		return true ;
	}


}
