package com.example.com.demo.utils;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;

import android.os.Handler;
import android.os.Message;

public class MyHandler extends Handler {

	private WeakReference<Object> wr;

	public MyHandler(Object t) {
		wr = new WeakReference<Object>(t);
	}

	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		if (wr != null && wr.get() != null) {
			try {
				Class<?> cls = wr.get().getClass();
				Method method = cls.getDeclaredMethod("handleMessage", Message.class);
				method.setAccessible(true);
				method.invoke(wr.get(), msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}