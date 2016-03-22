package com.example.com.demo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

public class ResourceIcon extends RatioColorFilterImageView{
	
	private static final int INIT_PADDING = 10;

	public ResourceIcon(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		if(getBackground() != null){
			getBackground().setState(getDrawableState());
			getBackground().draw(canvas);
		}
		if(getDrawable() != null){
			getDrawable().setBounds(INIT_PADDING, INIT_PADDING, getWidth() - INIT_PADDING, getHeight() - INIT_PADDING);
			getDrawable().draw(canvas);
		}
	}

}
