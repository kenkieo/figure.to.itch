package com.example.com.demo.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class Adapter<T> extends BaseAdapter {

	protected Context mContext;
	protected List<T> mBeans;
	protected boolean mScrolling;// 是否滑动,滑动不加载

	public Adapter(Context context, List<T> beans) {
		this.mBeans = beans;
		this.mContext = context;
	}

	@Override
	public int getCount() {
		return mBeans.size();
	}

	@Override
	public Object getItem(int position) {
		return mBeans.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = newView(mContext, position, convertView);
		}
		bindView(mContext, position, convertView);
		return convertView;
	}

	public void releaseAdapter() {
		mContext = null;
		if(mBeans != null){
			mBeans.clear();
			mBeans = null;
		}
	}

	public abstract View newView(Context context, int position, View convertView);

	public abstract void bindView(Context context, int position, View convertView);

	public Context getContext() {
		return mContext;
	}

	public List<T> getBeans() {
		return mBeans;
	}

	/**
	 * 是否滑动,滑动不加载
	 * 
	 * @param mScrolling
	 */
	public void setScrolling(boolean scrolling) {
		this.mScrolling = scrolling;
	}

	public boolean onBackPressed() {
		return false;
	}

}
