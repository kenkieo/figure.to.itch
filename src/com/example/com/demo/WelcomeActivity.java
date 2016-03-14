package com.example.com.demo;

import com.example.com.demo.baseactivity.BaseHandlerActivity;
import com.example.com.demo.utils.ActivityUtils;

public class WelcomeActivity extends BaseHandlerActivity {

	private static final long DELAY_TIME = 2500;
	
	@Override
	protected int getLayoutRes() {
		return R.layout.activity_welcome;
	}

	@Override
	protected void initViews_BaseActivity() {
		postDelayed(new Runnable() {
			
			@Override
			public void run() {
				ActivityUtils.startMainActivity(mContext);
				finish();				
				overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
			}
		}, DELAY_TIME);
	}
	
	@Override
	protected void release_BaseHandlerActivity() {
		
	}

}
