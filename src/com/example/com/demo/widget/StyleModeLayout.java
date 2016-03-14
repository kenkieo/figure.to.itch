package com.example.com.demo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;

public class StyleModeLayout extends LinearLayout{
	
	private LayoutAnimationController mController;
	
	public StyleModeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		Animation animation = new AlphaAnimation(0, 1);
		animation.setDuration(300);
		animation.setStartTime(AnimationUtils.currentAnimationTimeMillis() + 1000);
		mController = new LayoutAnimationController(animation);
		mController.setOrder(LayoutAnimationController.ORDER_NORMAL);
		mController.setDelay(0.3f);
		setLayoutAnimation(mController);
	}
	
}
