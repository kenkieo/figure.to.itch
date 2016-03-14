package com.example.com.demo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class StyleFrameLayout extends FrameLayout{

	public StyleFrameLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY));
	}

}
