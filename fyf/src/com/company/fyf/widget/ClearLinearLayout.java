package com.company.fyf.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ClearLinearLayout extends LinearLayout {

	private Context context;

	public ClearLinearLayout(Context context) {
		super(context);
		init(context, null);
	}

	public ClearLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	@SuppressLint("NewApi")
	public ClearLinearLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		int childNum = getChildCount();

		if (childNum != 3) {
			throw new IllegalAccessError(
					"the childnum of ClearLinearLayout must be 3");
		}

		final View secondView = getChildAt(1);

		if (!(secondView instanceof EditText)) {
			throw new IllegalAccessError(
					"the second view of ClearLinearLayout must extends EditText");
		}

		View thirdView = getChildAt(2);
		thirdView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				((EditText) secondView).setText("");
			}
		});
	}

	private void init(Context context, AttributeSet attrs) {
		this.context = context;
	}

}
