package com.example.com.demo.baseactivity;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ViewGroup;

import com.example.com.demo.utils.SystemBarTintManagerUtils;

/**
 * widget = 1 初始化从widget高到低开始初始化
 * 
 * @author 蔡国辉
 * 
 */
public abstract class BaseActivity extends Activity {

	protected Context mContext;
	protected boolean mResume;

	protected final void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initConfig();
		int resId = getLayoutRes();
		if (resId > 0) {
			setContentView(resId);
		}
		initViews_BaseActivity();
		addOtherViews();
		initData();
		loadData(mContext);
	}
	
	protected void addOtherViews(){

	}

	/**
	 * 初始化配置
	 * 
	 * @return
	 */
	protected void initConfig() {
		mContext = this;
		SystemBarTintManagerUtils.attachActivity(this);
	}

	/**
	 * 获取资源ID
	 * 
	 * @return
	 */
	protected abstract int getLayoutRes();

	/**
	 * 初始化控件
	 */
	protected abstract void initViews_BaseActivity();

	/**
	 * 初始化数据
	 */
	protected void initData(){}

	/**
	 * 初始化网络请求
	 */
	protected void loadData(Context context) {

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		release_BaseActivity();
		ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
		if (viewGroup != null) {
			viewGroup.removeAllViews();
			viewGroup = null;
		}
		mContext = null;
		System.gc();
	}
	
	/**
	 * 资源释放 按widget从大到小释放 widget = 1
	 */
	protected abstract void release_BaseActivity();

}
