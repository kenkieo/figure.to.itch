package com.example.com.demo.baseactivity;


import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public abstract class BaseListenerActivity extends BaseActivity implements OnClickListener, OnScrollListener{

	public void onClick(View v) {
		
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

	}
	
}
