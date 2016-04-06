package com.company.fyf.widget.empty;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListAdapter;

import com.company.fyf.R;
import com.lyx.utils.CommUtil;

public final class EmptyViewLayout extends FrameLayout {

	private final int TYPE_LISTVIEW = 0;
	private final int TYPE_GRIDVIEW = 1;
	private final int TYPE_REFRESH_LISTVIEW = 2;
	private final int TYPE_REFRESH_GTIDVIEW = 3;
	private final int TYPE_ERROR = -1;

	private EmptyViewBehaviorImpl behaviorImpl = null;
	
	/*-------------gridview配置参数-------------*/
	private final int COL_NUM_DEFAULT = 2 ;
	
	private int col_num = COL_NUM_DEFAULT ;
	private float colSpace = 0f ;
	private float rowSpace = 0f ;

	public EmptyViewLayout(Context context) {
		super(context);
		init(context, null);
	}

	public EmptyViewLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public EmptyViewLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {

		int type = TYPE_ERROR;

		if (attrs != null) {
			type = attrs.getAttributeIntValue(null, "empty_view", TYPE_ERROR);
			col_num = attrs.getAttributeIntValue(null, "col_num", COL_NUM_DEFAULT) ;
			colSpace = attrs.getAttributeFloatValue(null, "col_space", 0f) ;
			rowSpace = attrs.getAttributeFloatValue(null, "row_space", 0f) ;
		}

		if (type == TYPE_ERROR) {
			throw new IllegalArgumentException(
					"Please config the empty_view type in xml");
		}

		behaviorImpl = createDateShowView(context,type);
		View emptyView = LayoutInflater.from(context).inflate(R.layout.v_empty,
				null);

		addView((View) behaviorImpl);
		addView(emptyView);
		behaviorImpl.setEmptyView(emptyView);
	}

	private EmptyViewBehaviorImpl createDateShowView(Context context, int type) {
		switch (type) {
		case TYPE_LISTVIEW:
			return new EmptyViewListView(context);
		case TYPE_GRIDVIEW:
			EmptyViewGridView emptyViewGridView = new EmptyViewGridView(context);
			initGridView(emptyViewGridView) ;
			return emptyViewGridView;
		case TYPE_REFRESH_LISTVIEW:

			return new EmptyViewRefreshListView(context);
		case TYPE_REFRESH_GTIDVIEW:
			EmptyViewRefreshGridView emptyViewRefreshGridView = new EmptyViewRefreshGridView(context);
			initGridView(emptyViewRefreshGridView.getRefreshableView()) ;
			return emptyViewRefreshGridView;

		default:
			return null;
		}
	}
	
	private void initGridView(GridView gridView) {
		// TODO Auto-generated method stub
		gridView.setNumColumns(col_num) ;
		gridView.setVerticalSpacing((int)CommUtil.dp2px(getContext(), rowSpace));
		gridView.setHorizontalSpacing((int)CommUtil.dp2px(getContext(), colSpace));
	}

	@SuppressWarnings("unchecked")
	public <T extends EmptyViewBehaviorImpl> T getDateShowView(){
		return (T) behaviorImpl ;
	}
	
	public void setAdapter(ListAdapter adapter){
		behaviorImpl.setAdapter(adapter) ;
	}
	
	public void setOnItemClickListener(OnItemClickListener listener){
		behaviorImpl.setOnItemClickListener(listener) ;
	}
}
