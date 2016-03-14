package com.example.com.demo.widget.actionbar;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.example.com.demo.R;
import com.example.com.demo.utils.SystemBarTintManagerUtils;

public class ActionbarTitleLayout extends LinearLayout{

	public ActionbarTitleLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		setBackgroundResource(R.color.common_yellow);
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		if (Build.VERSION.SDK_INT >= 19) {
			int statusBarHeight = SystemBarTintManagerUtils.getStatusBarHeight(getContext());
			setPadding(getPaddingLeft(), statusBarHeight, getPaddingRight(), getPaddingBottom());
		}
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		if(MeasureSpec.EXACTLY == heightMode){
			int heightSize = MeasureSpec.getSize(heightMeasureSpec);
			if (Build.VERSION.SDK_INT >= 19) {
				int statusBarHeight = SystemBarTintManagerUtils.getStatusBarHeight(getContext());
				heightSize += statusBarHeight;
				heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
			}
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

}
