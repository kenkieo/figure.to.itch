package com.example.com.demo.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.com.demo.utils.LayoutInflaterUtils;

public abstract class BaseFragment extends Fragment{
	
	protected Activity mParent;
	private boolean  mHasCreateView;
	private boolean  mHasShow;
	protected boolean  mResume;
	private OnRequestAction mAction;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initConfig();
	}
	
	protected void initConfig(){
		mParent = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		int resId = getLayoutRes();
		View convertView = LayoutInflaterUtils.inflateView(mParent, resId);
		initBaseFragment(convertView);
		ViewParent viewParent = convertView.getParent();
		if(viewParent != null){
			convertView = (View) viewParent;
		}
		return convertView;
	}
	
	/**
	 * 添加附加视图
	 * @param view
	 */
	protected void addViewForConvertView(View view){
		
	}
	
	/**
	 * 获取资源ID
	 * 
	 * @return
	 */
	protected abstract int getLayoutRes();
	
	private void initBaseFragment(View convertView){
		initViews(convertView);
		addViewForConvertView(convertView);
		initData();
		if(!mHasCreateView){
			mHasCreateView = true;
			if(loadDataAble()){
				loadData(mParent);
			}
		}
	}
	
	public void lazyLoadData(Context context){
		if(!mHasShow){
			mHasShow = true;
			if(loadDataAble()){
				loadData(context);
			}
		}
	}
	
	public boolean isHasCreateView() {
		return mHasCreateView;
	}
	
	protected boolean loadDataAble(){
		return mHasShow && mHasCreateView;
	}
	
	public void setHasShow(boolean hasShow) {
		this.mHasShow = hasShow;
	}

	protected abstract void initViews(View convertView);
	
	protected void initData(){}

	protected void loadData(Context context){}

	
	public void setOnRequestAction(OnRequestAction action) {
		this.mAction = action;
	}
	
	protected void onRequestEnd(){
		if(mAction != null){
			mAction.onRequestEnd();
		}
	}
	
	public interface OnRequestAction{
		void onRequestEnd();
	}
	
	/**
	 * activity是否关闭了
	 * @return
	 */
	protected boolean isFinishing(){
		return mParent == null || mParent.isFinishing();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mResume = true;
		setUserVisibleHint(true);
		onHiddenChanged(false);
	}
	
	@Override
	public void onStop() {
		super.onStop();
		setUserVisibleHint(false);
		onHiddenChanged(true);
	}
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		onFragmentShow(isVisibleToUser);
	}
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		onFragmentShow(!hidden);
	}
	
	public void onFragmentShow(boolean show){
		if(show && isHasCreateView()){
			cancelNotice();
		}
	}
	
	public void cancelNotice(){
		
	}
	
	@Override
	public void onPause() {
		super.onPause();
		mResume = false;
	}
	
	@Override
	public final void onDestroy() {
		super.onDestroy();
		releaseRes();
		mParent = null;
	}
	
	protected abstract void releaseRes();
}
