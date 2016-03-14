package com.example.com.demo.widget.mode;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

public class ModeFrameLayout extends ViewGroup{
	
	private static final int MAX_DEGRESS 	= 360;
	private static final int INIT_NUM		= 6;
	
	private int mCenterX;
	private int mCenterY;
	
	public ModeFrameLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int count = getChildCount();
		float mLastDegrees = MAX_DEGRESS;
		if(mCenterX == 0){
			mCenterX = getWidth() / 2;
			mCenterY = getHeight() / 2;
		}
		for (int i = 0; i < count; i++) {
			IModeFrameAction modeAction = (IModeFrameAction) getChildAt(i);
//			modeAction.layout();
		}
	}
	
}
