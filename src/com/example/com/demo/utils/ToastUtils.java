package com.example.com.demo.utils;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;

public class ToastUtils {

	private static Toast textToast;
	private static String toastText;
	private static long textShowTime;

	public static synchronized void showShortToast(Context context, int resId) {
		showToast(context, context.getString(resId), Toast.LENGTH_SHORT);
	}

	public static synchronized void showShortToast(Context context, String text) {
		showToast(context, text, Toast.LENGTH_SHORT);
	}
	
	public static synchronized void showLongToast(Context context, int resId) {
		showToast(context, context.getString(resId), Toast.LENGTH_LONG);
	}
	
	
	public static synchronized void showLongToast(Context context, String text) {
		showToast(context, text, Toast.LENGTH_LONG);
	}

	public synchronized static void showToast(Context context, String text, int duration) {
		if(TextUtils.isEmpty(text)) return;
		if (textToast != null) {
			if (toastText.equals(text)) {
				long interval = System.currentTimeMillis() - textShowTime;
				if (interval < 1000 * 2) {
					return;
				}
			}
			textToast.setText(text);
		} else {
			textToast = Toast.makeText(context.getApplicationContext(), text, duration);
		}
		toastText = text;
		textToast.show();
		textShowTime = System.currentTimeMillis();
	}
	
	public static void popupToast(Handler handler, Context context, int resId, int duration) {
		popupToast(handler, context, context.getString(resId), duration, 0);
	}
	
	public static void popupToast(Handler handler, Context context, String text, int duration) {
		popupToast(handler, context, text, duration, 0);
	}

	public static void popupToast(Handler handler, Context context, int resId, int duration, long delayMillis) {
		popupToast(handler, context, context.getString(resId), duration, delayMillis);
	}
	
	public static void popupToast(Handler handler,final Context context, final String text, final int duration, long delayMillis) {
		HandlerUtils.postDelayed(handler, new Runnable() {

			@Override
			public void run() {
				showToast(context, text, duration);
			}
		}, delayMillis);
	}

}
