package com.example.com.demo.fragment.mode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.example.com.demo.R;
import com.example.com.demo.bean.Frame;
import com.example.com.demo.utils.Constants;
import com.example.com.demo.widget.mode.ModeFrame;

public class StyleFirstFragment extends StyleContentFrament{
	
	public Drawable 	mDefaultDrawable;
	private List<Frame> mFrames;
	private ModeFrame 	mModeFrame;
	private Thread 		mThread;
	
	@Override
	protected int getLayoutRes() {
		return R.layout.layout_frame_mode_1;
	}

	@Override
	protected void initViews(View convertView) {
		mDefaultDrawable = getResources().getDrawable(R.drawable.icon_default_source_1);
		mModeFrame = (ModeFrame) convertView.findViewById(R.id.layout_frame_mode_1);
	}
	
	@Override
	protected void initData() {
		super.initData();
		mFrames = new ArrayList<Frame>();
		mModeFrame.setFrames(mFrames);
		mModeFrame.setDefaultDrawable(mDefaultDrawable);
		mModeFrame.setOnModeFrameAction(this);
	}
	
	@Override
	public void onLayoutChange() {
		float degrees = 360.0f / Constants.INIT_NUM_MODE_1;
		for (int i = 0; i < Constants.INIT_NUM_MODE_1; i++) {
			Frame frame = new Frame();
			frame.mAlpha 			= 255;
			frame.mCurrentDegrees 	= 0;
			frame.mLastDegrees 		= degrees * i;
			frame.mDrawable 		= mDefaultDrawable;
			frame.mRectC.set(mModeFrame.getRectF());
			frame.mRectL.set(mModeFrame.getRectF());
			mFrames.add(frame);
		}
		mModeFrame.startAnimation();
	}
	
	@Override
	public void setIsLock(boolean isLock){
		if(mModeFrame != null){
			mModeFrame.setIsLock(isLock);
		}
	}
	
	@Override
	public void onResourceSelect(Drawable drawable) {
		if(mModeFrame != null){
			mModeFrame.setonResourceSelect(drawable);
		}
	}
	
	@Override
	public void onAlphaChange(int alpha) {
		if(mModeFrame != null){
			mModeFrame.onAlphaChange(alpha);
		}
	}
	
	@Override
	public void onColorChange(int color) {
		if(mModeFrame != null){
			mModeFrame.onColorChange(color);
		}
	}
	
	@Override
	public void onTimesChange(int times) {
		if(mModeFrame != null){
			mModeFrame.onTimesChange(times);
		}
	}
	
	public int getSize() {
		return mFrames != null ? mFrames.size() : 0;
	}
	
	@Override
	public int getMax() {
		return 12;
	}
	
	@Override
	public void cutScreenShot() {
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
	
	@Override
	protected void releaseHandlerFragment() {
		super.releaseHandlerFragment();
		mDefaultDrawable = null;
		if(mFrames != null){
			mFrames.clear();
			mFrames = null;
		}
		
		if(mModeFrame != null){
			mModeFrame.setOnModeFrameAction(null);
			mModeFrame = null;
		}
	}

}
