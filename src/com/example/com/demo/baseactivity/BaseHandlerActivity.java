package com.example.com.demo.baseactivity;

import android.os.Handler;
import android.os.Message;

import com.example.com.demo.utils.HandlerUtils;
import com.example.com.demo.utils.MyHandler;

public abstract class BaseHandlerActivity extends BaseListenerActivity {

	private Handler mHandler;

	@Override
	protected void initConfig() {
		super.initConfig();
		mHandler = new MyHandler(getClass().getName());
	}

	public final void sendEmptyMessage(int what) {
		sendEmptyMessageDelayed(what, 0);
	}

	public final void sendEmptyMessageDelayed(int what, long delayMillis) {
		HandlerUtils.sendEmptyMessageDelayed(mHandler, what, delayMillis);
	}

	public final void sendMessage(Message msg) {
		sendMessageDelayed(msg, 0);
	}
	
	public final Message obtainMessage(int what, int arg1, int arg2){
		return HandlerUtils.obtainMessage(what, arg1, arg2);
	}

	public final void sendMessageDelayed(Message msg, long delayMillis) {
		HandlerUtils.sendMessageDelayed(mHandler, msg, delayMillis);
	}

	public final void post(Runnable r) {
		postDelayed(r, 0);
	}

	public final void postDelayed(Runnable r, long delayMillis) {
		HandlerUtils.postDelayed(mHandler, r, delayMillis);
	}

	public final void removeMessages(int what) {
		HandlerUtils.removeMessages(mHandler, what);
	}

	public final void removeCallbacks(Runnable r) {
		HandlerUtils.removeCallbacks(mHandler, r);
	}

	/**
	 * 
	 * @param msg
	 */
	protected void handleMessage(Message msg) {

	}

	private final void removeCallbacksAndMessages() {
		HandlerUtils.removeCallbacksAndMessages(mHandler);
		mHandler = null;
	}
	
	public Handler getHandler() {
		return mHandler;
	}
	
	@Override
	protected final void release_BaseActivity() {
		removeCallbacksAndMessages();
		release_BaseHandlerActivity();
	}

	/**
	 * 释放资源 widget = 2
	 */
	protected abstract void release_BaseHandlerActivity();

}
