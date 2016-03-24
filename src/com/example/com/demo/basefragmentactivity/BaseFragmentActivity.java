package com.example.com.demo.basefragmentactivity;

import java.lang.reflect.Field;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;

import com.example.com.demo.utils.SystemBarTintManagerUtils;

public abstract class BaseFragmentActivity extends FragmentActivity{

	protected FragmentManager 	mFragmentManager;
	protected Context 			mContext;
	protected int mIdx = -1;
	
	public final void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		initConfig();
		init();
		checkIntent();
	}
	
	private void init(){
		mFragmentManager = getSupportFragmentManager();
		addFragments();
		setContentView(getLayoutRes());
		initViews_BaseFragmentActivity();
		addPanelViews();
		initData();
		loadData(mContext);
	}
	
	protected void addPanelViews(){};
	
	protected void initConfig(){
		mContext = this;
		SystemBarTintManagerUtils.attachActivity(this);
	}
	
	protected abstract void addFragments();
	
	protected abstract void initViews_BaseFragmentActivity();
	
	protected void setCurrentFragment(int fragmentIdx) {
		if(mIdx != fragmentIdx && mIdx >= 0){
			setSelection(mIdx, false);
		}
		mIdx = fragmentIdx;
		setSelection(mIdx, true);
	}
	
	protected int getCurrentIdx(){
		return mIdx;
	}
	
	protected abstract void setSelection(int idx, boolean show);
	
	protected abstract void initData();
	
	protected void loadData(Context context){}
	
	protected abstract int getLayoutRes();
	
	//该方法是为了避免重建fragment导致重叠
	@Override
	public void onSaveInstanceState(Bundle outState){
//		super.onSaveInstanceState(outState);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		checkIntent();
	}
	
	private void checkIntent(){
		if(getIntent() != null){
			processExtraData();
			setIntent(null);
		}
	}
	
	protected void processExtraData(){}
	
	public void onCloseAction() {
		onBackAction();
	}
	
	protected void onBackAction(){
		finish();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		release_BaseFragmentActivity();
		ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
		if(viewGroup != null){
			viewGroup.removeAllViews();
			viewGroup = null;
		}
		try {
			Field field = mFragmentManager.getClass().getDeclaredField("mAdded");
			field.setAccessible(true);
			Object object = field.get(mFragmentManager);
			if(object != null){
				object.getClass().getMethod("clear").invoke(object);
				object = null;
			}
			field = null;
		} catch (Exception e) {
		}
		mFragmentManager = null;
		mContext         = null;
		System.gc();
	}
	
	protected abstract void release_BaseFragmentActivity();
}
