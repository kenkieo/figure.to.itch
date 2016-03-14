package com.example.com.demo.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class DisplayUtils {

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static float sp2px(Context context, float pxValue) {
		float scale = context.getResources().getDisplayMetrics().scaledDensity;
		return pxValue * scale;
	}

	public static float px2sp(Context context, float pxValue) {
		float scale = context.getResources().getDisplayMetrics().scaledDensity;
		return pxValue / scale;
	}
	
	public static float getDensity(Context context){
		return context.getResources().getDisplayMetrics().density;
	}

	/**
	 * 获取屏幕像素
	 * 
	 * @param context
	 * @return
	 */
	public static final DisplayMetrics getDisplayMetrics(Context context) {
		DisplayMetrics displayMetrics = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(displayMetrics);
		return displayMetrics;
	}
}
