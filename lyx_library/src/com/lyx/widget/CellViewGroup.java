package com.lyx.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * ViewGroup that arranges child views in a similar way to text, with them laid
 * out one line at a time and "wrapping" to the next line as needed.
 *
 * Code licensed under CC-by-SA
 *
 * @author Henrik Gustafsson
 * @see http://stackoverflow.com/questions/549451/line-breaking-widget-layout-for-android
 * @license http://creativecommons.org/licenses/by-sa/2.5/
 * 
 * 修改  by liyaxing ：
 * 		1）childview的layouparams属性失去作用
 * 		2）建议添加相同高度的childview,即每个childview一行显示
 * 		3）添加xml属性:row_space,col_space,接受dp值,float类型
 *
 */
public class CellViewGroup extends ViewGroup {

    private int line_height;

    private int max_line = 5;
    
    private int row_space = 0;
    
    private int col_space = 0 ;
    
    public CellViewGroup(Context context) {
        super(context);
        init(context, null) ;
    }

    public CellViewGroup(Context context, AttributeSet attrs){
        super(context, attrs);
        init(context, attrs) ;
    }

    private void init(Context context, AttributeSet attrs) {
		// TODO Auto-generated method stub
		if(attrs != null){
			row_space = (int) dp2px(context, attrs.getAttributeFloatValue(null, "row_space", 0f)) ;
			col_space = (int) dp2px(context, attrs.getAttributeFloatValue(null, "col_space", 0f)) ;
		}
	}

	@SuppressLint("Assert")
	@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        assert(MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.UNSPECIFIED);

        final int width = MeasureSpec.getSize(widthMeasureSpec);

        // The next line is WRONG!!! Doesn't take into account requested MeasureSpec mode!
        int height = MeasureSpec.getSize(heightMeasureSpec) ;
        final int count = getChildCount();
        int line_height = 0;

        int xpos = getPaddingLeft();
        int ypos = getPaddingTop();
        int xpos_r = getPaddingRight() ;
        int xpos_b = getPaddingBottom() ;

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                child.measure(
                        MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST),
                        MeasureSpec.makeMeasureSpec(height, MeasureSpec.UNSPECIFIED));

                final int childw = child.getMeasuredWidth();
                line_height = Math.max(line_height, child.getMeasuredHeight());

                if (xpos + childw + xpos_r + col_space > width) {
                    xpos = getPaddingLeft();
                    ypos += line_height + row_space;
                }

                xpos += childw + col_space;
            }
        }
        this.line_height = line_height;

        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.UNSPECIFIED){
            height = ypos + line_height + xpos_b;

        } else if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST){
            if (ypos + line_height + xpos_b< height){
                height = ypos + line_height + xpos_b;
            }
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(1, 1); // default of 1px spacing
    }

    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return (p instanceof LayoutParams);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        final int width = r - l;
        int xpos = getPaddingLeft();
        int ypos = getPaddingTop();
        int xpos_r = getPaddingRight() ;
        int line = 1;

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (line > max_line) {
                child.setVisibility(GONE);
            } else if (child.getVisibility() != GONE) {
                final int childw = child.getMeasuredWidth();
                final int childh = child.getMeasuredHeight();
                if (xpos + childw + col_space + xpos_r > width) {
                    xpos = getPaddingLeft();
                    ypos += line_height + row_space;
                    line++;
                }
                if (line > max_line) {
                    child.setVisibility(GONE);
                } else {
                    child.layout(xpos, ypos, xpos + childw, ypos + childh);
                    xpos += childw + col_space;
                }
            }
        }
    }

    public void setMaxLine(int max) {
        this.max_line = max;
    }
    
    public static float dp2px(Context context,float dp) {
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float px = dp * (metrics.densityDpi / 160f);
		return px;
	}
}
