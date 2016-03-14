package com.example.com.demo.basefragmentactivity;

import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public abstract class BaseListenerFragmentActivity extends BaseFragmentActivity implements OnClickListener, OnScrollListener, OnPageChangeListener{

	@Override
	public void onClick(View v) {
		
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		
	}
	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		
	}
	
	@Override
	public void onPageSelected(int position) {
		
	}
	
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}
}
