package com.example.com.demo.fragment.mode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.com.demo.R;
import com.example.com.demo.bean.Frame;
import com.example.com.demo.fragment.BaseHandlerFragment;
import com.example.com.demo.interfaces.OnParameterChangeListener;
import com.example.com.demo.interfaces.OnResourceSelectAction;
import com.example.com.demo.widget.mode.ModeFrame;
import com.example.com.demo.widget.mode.ModeFrame.OnModeFrameAction;

public abstract class StyleContentFrament extends BaseHandlerFragment implements OnResourceSelectAction, OnParameterChangeListener, OnModeFrameAction{

	private OnModeFrameAction mOnModeFrameAction;
	private ProgressDialog    mProgressDialog;
	protected boolean 		  mInitLayout;
	protected ModeFrame 	mModeFrame;
	protected Thread 		mThread;
	protected Drawable 		mDefaultDrawable;
	protected List<Frame> 	mFrames;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mFrames = new ArrayList<Frame>();
		mDefaultDrawable = getResources().getDrawable(R.drawable.icon_default_source_1);
	}
	
	public void setOnModeFrameAction(OnModeFrameAction action) {
		this.mOnModeFrameAction = action;
	}
	
	@Override
	public void showStyleResourceFragment() {
		if(mOnModeFrameAction != null){
			mOnModeFrameAction.showStyleResourceFragment();
		}
	}
	
	public abstract void setIsLock(boolean isLock);
	
	public abstract int getProgress();
	
	public abstract int getMax();
	
	public void cutScreenShot(){
		if(mModeFrame != null){
			mModeFrame.hideSelectFrame();
			mModeFrame.buildDrawingCache(true);
			mModeFrame.buildDrawingCache();
			final Bitmap bitmap = mModeFrame.getDrawingCache();
			showProgressDialog();
			mThread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
						File directory = new File(mParent.getExternalCacheDir(), "bitmaps");
						directory.mkdirs();
						File file = new File(directory, System.currentTimeMillis() + ".jpg");
						FileOutputStream fos = new FileOutputStream(file);
						fos.write(baos.toByteArray());
						fos.close();
						baos.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					post(new Runnable() {
						
						@Override
						public void run() {
							dismissProgressDialog();
						}
					});
				}
			});
			mThread.start();
		}
	}
	
	public void showProgressDialog(){
		dismissProgressDialog();
		mProgressDialog = new ProgressDialog(mParent);
		mProgressDialog.setMessage("正在保存中,请稍后...");
		mProgressDialog.setCanceledOnTouchOutside(false);
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
	}
	
	public void dismissProgressDialog(){
		if(mProgressDialog != null){
			mProgressDialog.dismiss();
			mProgressDialog = null;
		}
	}
	
	@Override
	protected void releaseHandlerFragment() {
		mOnModeFrameAction = null;
		dismissProgressDialog();
		if(mModeFrame != null){
			mModeFrame.setOnModeFrameAction(null);
			mModeFrame = null;
		}
	}
	
	
}
