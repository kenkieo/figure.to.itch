package com.example.com.demo.widget.actionbar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class ActionbarNormalTitleContentLayout extends RelativeLayout{

	public ActionbarNormalTitleContentLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		int itemWidth = 0;
		for (int i = 0; i < getChildCount(); i++) {
			if(i != 0){
				int temp = getChildAt(i).getMeasuredWidth();
				itemWidth = itemWidth > temp ? itemWidth : temp;
			}
		}
		
		if(getChildCount() > 0){
			LayoutParams layoutParams = (LayoutParams) getChildAt(0).getLayoutParams();
			layoutParams.leftMargin  = itemWidth;
			layoutParams.rightMargin = itemWidth;
		}
	}
	
}
