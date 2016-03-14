package com.example.com.demo.view;

import android.content.Context;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;

public class RatioColorFilterImageView extends RatioImageView {
	
	public RatioColorFilterImageView(Context context) {
		super(context);
	}

	public RatioColorFilterImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void refreshDrawableState() {
		super.refreshDrawableState();
		if (getDrawable() == null)
			return;
		if (isPressed()) {
			getDrawable().setColorFilter(0x80000000, Mode.SRC_ATOP);
		} else {
			getDrawable().clearColorFilter();
		}
	}
	
}
