package com.example.com.demo.widget.actionbar;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.com.demo.observer.ExitActivityObserver;
import com.example.com.demo.observer.ExitActivityObserver.ExitActivityObserverAction;
import com.example.com.demo.utils.HandlerUtils;
import com.example.com.demo.widget.actionbar.interfaces.OnActionBarBackAction;
import com.example.com.demo.widget.actionbar.interfaces.OnActionBarMenuAction;

public abstract class ActionbarBasicLayout extends LinearLayout implements ExitActivityObserverAction{
	
	protected ActionbarBasicAction mAction;
	protected MyHandler mHandler;
	
	public ActionbarBasicLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mHandler = new MyHandler(this);
		ExitActivityObserver.getInst().addExitActivityObserverAction(context, this);
	}

	@Override
	protected final void onFinishInflate() {
		super.onFinishInflate();
		initActionbarBasic(this);
	}
	
	/**
	 * 初始化
	 * @param view
	 */
	protected abstract void initActionbarBasic(View view);
	
	/**
	 * 获取标题
	 * @return
	 */
	protected abstract ViewGroup getTitleLayout();
	
	
	@Override
	public void setVisibility(int visibility) {
		ViewGroup titleLayout = getTitleLayout();
		if(titleLayout != null){
			titleLayout.setVisibility(visibility);
		}
	}
	
	/**
	 * 添加到activity
	 * @param activity
	 */
	public void attachToActivity(Activity activity){
		ViewGroup viewGroup = (ViewGroup) activity.findViewById(android.R.id.content);
		View contentView = viewGroup.getChildAt(0);
		viewGroup.removeView(contentView);
		attachView(contentView);
		viewGroup.addView(this);
	}
	
	public void attachView(View view){
		addView(view, new LayoutParams(-1, -1));
	}
	
	public void setActionbarBasicAction(ActionbarBasicAction action) {
		this.mAction = action;
	}
	
	public interface ActionbarBasicAction extends OnActionBarBackAction, OnActionBarMenuAction{
		
	}
	
	private static class MyHandler extends Handler{
		
		private WeakReference<ActionbarBasicLayout> wr;
		
		public MyHandler(ActionbarBasicLayout r) {
			wr = new WeakReference<ActionbarBasicLayout>(r);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(wr != null && wr.get() != null){
				wr.get().handleMessage(msg);
			}
		}
	}

	public void handleMessage(Message msg) {
		
	}
	
	@Override
	public void onActivityDestory() {
		HandlerUtils.removeCallbacksAndMessages(mHandler);
		mHandler = null;
	}
	
}
