package com.lyx.widget.mansear;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.example.framework_lyx.R;

public abstract class BaseManSearchBar<T> extends RelativeLayout implements Search  <T>{

	protected ListDisplay<T> mListView;

	public BaseManSearchBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	private void initView(Context context) {
		View root = LayoutInflater.from(getContext()).inflate(
				R.layout.w_dynsear_mansearchbar, null);
		addView(root);
		final EditText eSearch = (EditText) findViewById(R.id.etSearch);
		final ImageView ivDeleteText = (ImageView) findViewById(R.id.ivDeleteText);
		ivDeleteText.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				eSearch.setText("");
			}
		});
		eSearch.setEms(EditorInfo.IME_ACTION_SEARCH) ;
		eSearch.setInputType(InputType.TYPE_CLASS_TEXT);
		eSearch.setOnEditorActionListener(new OnEditorActionListener() {
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH
						|| (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
					// do something;
					notifyChange(eSearch.getText().toString());
					return true;
				}
				return false;
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
					ivDeleteText.setVisibility(View.GONE);//
				} else {
					ivDeleteText.setVisibility(View.VISIBLE);//
				}
			}
		});
	}

	@Override
	public  void registeListView(ListDisplay<T> dataList) {
		this.mListView = dataList;
	}

	protected abstract void notifyChange(String text)  ;
}