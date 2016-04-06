package com.lyx.widget.dynsear;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.framework_lyx.R;

public class SimSearchBar extends RelativeLayout implements Search {

	private ListDisplay<?> mListView;

	public SimSearchBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	private void initView(Context context) {
		View root = LayoutInflater.from(getContext()).inflate(
				R.layout.w_dynsear_simsearchbar, null);
		addView(root);
		final EditText eSearch = (EditText) findViewById(R.id.etSearch);
		final ImageView ivDeleteText = (ImageView) findViewById(R.id.ivDeleteText);
		ivDeleteText.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				eSearch.setText("");
			}
		});
		eSearch.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
			}

			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			public void afterTextChanged(Editable s) {
				if (s.length() == 0) {
					ivDeleteText.setVisibility(View.GONE);// ���ı���Ϊ��ʱ��������ʧ
				} else {
					ivDeleteText.setVisibility(View.VISIBLE);// ���ı���Ϊ��ʱ�����ֲ��
				}
				notifyChange(s.toString()) ;
			}
		});
	}

	@Override
	public <T> void registeListView(ListDisplay<T> dataList) {
		this.mListView = dataList;
	}
	private void notifyChange(String text){
		if(mListView!=null){
			mListView.refresh(text);
		}
	}
}
