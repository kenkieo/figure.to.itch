package com.example.com.demo.fragment.mode;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.example.com.demo.R;
import com.example.com.demo.bean.Frame;
import com.example.com.demo.utils.Constants;
import com.example.com.demo.widget.mode.ModeFrame.OnModeFrameAction;
import com.example.com.demo.widget.mode.ModeFrameSecond;

public class StyleSecondFragment extends StyleContentFrament implements OnModeFrameAction{
	
	private boolean 			mInitLayout;
	
	@Override
	protected int getLayoutRes() {
		return R.layout.layout_frame_mode_2;
	}

	@Override
	protected void initViews(View convertView) {
		mDefaultDrawable = getResources().getDrawable(R.drawable.icon_default_source_1);
		mModeFrame = (ModeFrameSecond) convertView.findViewById(R.id.layout_frame_mode_2);
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
		mInitLayout = true;
		float degrees = Constants.DEGRESS / Constants.INIT_NUM_MODE_2;
		for (int i = 0; i < Constants.INIT_NUM_MODE_2 * 2; i++) {
			Frame frame = new Frame();
			frame.mAlpha 			= 255;
			frame.mCurrentDegrees 	= 0;
			frame.mLastDegrees 		= degrees * (i / 2);
			frame.mDrawable 		= mDefaultDrawable;
			frame.mRectC.set(mModeFrame.getRectF());
			frame.mRectL.set(mModeFrame.getRectF());
			frame.mPosition = i;
			if(i % 2 != 0){
				frame.mMirror = true;
				frame.mPointC.offset(-frame.mRectC.width(), 0);
			}
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
	
	@Override
	public int getProgress() {
		int size = 0;
		if(mInitLayout){
			size = mFrames != null ? mFrames.size() / 2 : 0;
		}else{
			size = Constants.INIT_NUM_MODE_2;
		}
		return size;
	}
	
	@Override
	public int getMax() {
		return Constants.MAX_NUM / 2;
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
