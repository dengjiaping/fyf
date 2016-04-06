package com.company.fyf.ui;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.company.fyf.R;
import com.company.fyf.utils.Logger;
import com.umeng.analytics.MobclickAgent;

public class M01MainActivity extends FragmentActivity implements
		OnTabChangeListener {

	public static final String PARAM_INT_TABINDEX = "param_int_tabindex";

	public static final int TAB_INDEX_HOME = 0;
	public static final int TAB_INDEX_PERSONAL = TAB_INDEX_HOME + 1;

	public static final String TAG = "MainActivity";

	private FragmentTabHost mTabHost;
	private ButtomTabConfig[] configs;

	private List<OnIntentListener> onIntentListenerList = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_m01_layout);

		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.contents);
		configs = ButtomTabConfig.values();
		for (int i = 0; i < configs.length; i++) {
			TabSpec tabSpec = mTabHost.newTabSpec(configs[i].getTagId())
					.setIndicator(
							getTabItemView(configs[i].getLable(),
									configs[i].getIconRid()));
			mTabHost.addTab(tabSpec, configs[i].getFragment(), null);
		}

		mTabHost.setOnTabChangedListener(this);

		doIntentAction();
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	private void doIntentAction() {
		int tabIndex = getIntent().getIntExtra(PARAM_INT_TABINDEX,
				TAB_INDEX_HOME);
		mTabHost.setCurrentTab(tabIndex);
		for (OnIntentListener listener : onIntentListenerList) {
			listener.onIntent();
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		doIntentAction();
	}

	@SuppressLint("InflateParams")
	private View getTabItemView(String lable, int imgRid) {
		View view = LayoutInflater.from(this).inflate(R.layout.i_m01_buttomtab,
				null);

		ImageView imageView = (ImageView) view.findViewById(R.id.image);
		TextView textView = (TextView) view.findViewById(R.id.lable);

		imageView.setImageResource(imgRid);
		textView.setText(lable);
		return view;
	}

	@Override
	public void onTabChanged(String tabId) {
		Logger.d(TAG, "--- onTabChanged --- tabId = " + tabId);
	}

	private enum ButtomTabConfig {

		HOME("0", R.drawable.ic_m01_iv_home, "首页", M02HomeFragment.class), PERSONAL(
				"1", R.drawable.ic_m01_iv_personal, "我的",
				M03PersonalFragment.class);

		private ButtomTabConfig(String tagId, int iconRid, String lable,
				Class<? extends Fragment> fragment) {
			this.iconRid = iconRid;
			this.lable = lable;
			this.fragment = fragment;
			this.tagId = tagId;
		}

		private String tagId;
		private int iconRid;
		private String lable;
		private Class<? extends Fragment> fragment;

		public int getIconRid() {
			return iconRid;
		}

		public String getLable() {
			return lable;
		}

		public Class<? extends Fragment> getFragment() {
			return fragment;
		}

		public String getTagId() {
			return tagId;
		}
	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		overridePendingTransition(R.anim.slide_in_from_right,
				R.anim.slide_out_to_left);
	}

	@Override
	public void startActivityFromFragment(Fragment fragment, Intent intent,
			int requestCode) {
		super.startActivityFromFragment(fragment, intent, requestCode);
		overridePendingTransition(R.anim.slide_in_from_right,
				R.anim.slide_out_to_left);
	}

	interface OnIntentListener {
		public void onIntent();
	}

	void registerOnIntentListener(OnIntentListener listener) {
		if (listener != null && !onIntentListenerList.contains(listener)) {
			onIntentListenerList.add(listener);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Logger.d("M01MainActivity", "[onActivityResult]");
		/* 在这里，我们通过碎片管理器中的Tag，就是每个碎片的名称，来获取对应的fragment */
		/* 目前只有我的界面才处理这个逻辑,后续有扩张,可获取当前tag */
		Fragment f = getSupportFragmentManager().findFragmentByTag("1");
		/* 然后在碎片中调用重写的onActivityResult方法 */
		f.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 如果是返回键,直接返回到桌面
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 注意
			intent.addCategory(Intent.CATEGORY_HOME);
			startActivity(intent);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}