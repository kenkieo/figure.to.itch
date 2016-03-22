package com.example.com.demo.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap.Config;
import android.widget.ImageView;

import com.example.com.demo.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

@SuppressLint("NewApi")
public class DisplayImageOptionsUtils {

	public static DisplayImageOptions getInstance(int resId) {
		return new DisplayImageOptions.Builder()
				.showImageForEmptyUri(resId)
				.showImageOnFail(resId)
				.imageScaleType(ImageScaleType.EXACTLY)
				.cacheInMemory(true) // 加载图片时会在内存中加载缓存
				.cacheOnDisk(true) // 加载图片时会在磁盘中加载缓存
				.bitmapConfig(Config.RGB_565)
				.build();
	}
	
	public static DisplayImageOptions getInstance(int resId, boolean cacheOnDisc) {
		return new DisplayImageOptions.Builder()
				.showImageForEmptyUri(resId)
				.showImageOnFail(resId)
				.imageScaleType(ImageScaleType.EXACTLY)
				.cacheInMemory(true) // 加载图片时会在内存中加载缓存
				.cacheOnDisk(cacheOnDisc) // 加载图片时会在磁盘中加载缓存
				.bitmapConfig(Config.RGB_565)
				.build();
	}
	
	public static DisplayImageOptions getDefault(){
		int resId = R.drawable.common_gray;
		return new DisplayImageOptions.Builder()
				.showImageForEmptyUri(resId)
				.showImageOnFail(resId)
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				.cacheInMemory(true) // 加载图片时会在内存中加载缓存
				.cacheOnDisk(true) // 加载图片时会在磁盘中加载缓存
				.bitmapConfig(Config.RGB_565)
				.build();
	}
	
	public static final void displayImage(String uri, ImageView imageView, DisplayImageOptions options){
		ImageLoader.getInstance().displayImage(uri, imageView, options);
	}
	
	public static final void displayImage(String uri, ImageView imageView, DisplayImageOptions options, ImageLoadingListener imageLoadingListener){
		ImageLoader.getInstance().displayImage(uri, imageView, options, imageLoadingListener);
	}

	public static final void loadImage(Context context, String iconUrl, ImageLoadingListener listener){
		ImageLoader.getInstance().loadImage(iconUrl, listener);
	}
	
	public static final void loadImage(Context context, String iconUrl, DisplayImageOptions options, ImageLoadingListener listener){
		ImageLoader.getInstance().loadImage(iconUrl, options, listener);
	}
	
	public static final void loadImageSelector(Context context, String iconUrl, ImageLoadingListener listener){
		int outWidth = context.getResources().getDisplayMetrics().widthPixels / 3;
		int outHeight = outWidth * 180 / 220;
		ImageSize imageSize = new ImageSize(outWidth, outHeight);
		ImageLoader.getInstance().loadImage(iconUrl, imageSize, getDefault(), listener);
	}
	
}
