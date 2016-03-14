package com.example.com.demo.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.com.demo.R;

public class SystemBarTintManagerUtils {
	
	public static void attachActivity(Activity activity){
		if(Build.VERSION.SDK_INT >= 21){
			setTranslucentStatus(activity);
		}else if (Build.VERSION.SDK_INT >= 19) {
			setTranslucentStatus(activity, true);
		}
//		Window window = activity.getWindow();
//		window.setBackgroundDrawable(new ColorDrawable(0));
//		window.getDecorView().setBackgroundDrawable(null);
	}
	
	@TargetApi(21) 
	public static void setTranslucentStatus(Activity activity) {
		Window window = activity.getWindow();
		try {
			int FLAG_TRANSLUCENT_STATUS = ReflectionUtils.getStaticFieldInt(WindowManager.LayoutParams.class.getName(), "FLAG_TRANSLUCENT_STATUS");
			window.clearFlags(FLAG_TRANSLUCENT_STATUS);
			window.addFlags(FLAG_TRANSLUCENT_STATUS);
			int SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN = ReflectionUtils.getStaticFieldInt(View.class.getName(), "SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN");
			int SYSTEM_UI_FLAG_LAYOUT_STABLE = ReflectionUtils.getStaticFieldInt(View.class.getName(), "SYSTEM_UI_FLAG_LAYOUT_STABLE");
			window.getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | SYSTEM_UI_FLAG_LAYOUT_STABLE);
			int flag = ReflectionUtils.getStaticFieldInt(WindowManager.LayoutParams.class.getName(), "FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS");
			window.addFlags(flag);
			Method method = Window.class.getDeclaredMethod("setStatusBarColor", int.class);
			method.invoke(window, activity.getResources().getColor(R.color.common_transparent));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@TargetApi(19) 
	public static void setTranslucentStatus(Activity activity, boolean on) {
		Window window = activity.getWindow();
		WindowManager.LayoutParams winParams = window.getAttributes();
		int bits;
		try {
			bits = ReflectionUtils.getStaticFieldInt(WindowManager.LayoutParams.class.getName(), "FLAG_TRANSLUCENT_STATUS");
			if (on) {
				winParams.flags |= bits;
			} else {
				winParams.flags &= ~bits;
			}
			window.setAttributes(winParams);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object o = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = (Integer) field.get(o);
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
            if(context instanceof Activity){
            	 Rect frame = new Rect();
                 ((Activity)context).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
                 statusBarHeight = frame.top;
            }
        }
        return statusBarHeight;
	}
}
