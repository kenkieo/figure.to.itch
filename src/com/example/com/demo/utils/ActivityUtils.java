package com.example.com.demo.utils;

import android.content.Context;
import android.content.Intent;

import com.example.com.demo.app.MainActivity;
import com.example.com.demo.app.ResourceActivity;

public class ActivityUtils {

	public static void startMainActivity(Context context){
		Intent intent = new Intent(context, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
	
	public static void startResourceActivity(Context context){
		Intent intent = new Intent(context, ResourceActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
}
