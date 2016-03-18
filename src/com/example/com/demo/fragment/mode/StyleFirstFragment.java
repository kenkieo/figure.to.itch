package com.example.com.demo.fragment.mode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.example.com.demo.R;
import com.example.com.demo.bean.Frame;
import com.example.com.demo.widget.mode.ModeFrame;
import com.example.com.demo.widget.mode.ModeFrame.OnLayoutChangeListener;

public class StyleFirstFragment extends StyleContentFrament implements OnLayoutChangeListener {
	
	public Drawable mDefaultDrawable;
	private static final int INIT_NUM = 6;
	
	private List<Frame> mFrames;
	private ModeFrame mModeFrame;
	
	@Override
	protected int getLayoutRes() {
		return R.layout.layout_frame;
	}

	@Override
	protected void initViews(View convertView) {
		mDefaultDrawable = getResources().getDrawable(R.drawable.icon_default_source_1);
		mModeFrame = (ModeFrame) convertView.findViewById(R.id.layout_frame);
		mModeFrame.setIsLock(true);
	}
	
	@Override
	protected void initData() {
		super.initData();
		mFrames = new ArrayList<Frame>();
		mModeFrame.setFrames(mFrames);
		mModeFrame.setOnLayoutChangeListener(this);
	}
	
	@Override
	public void onLayoutChange() {
		float degrees = 360.0f / INIT_NUM;
		for (int i = 0; i < INIT_NUM; i++) {
			Frame frame = new Frame();
			frame.mAlpha = 255;
			frame.mColor = new Random().nextInt(0xFFFFFF);
			frame.mCurrentDegrees = 0;
			frame.mLastDegrees = degrees * i;
			frame.mDrawable = mDefaultDrawable;
			frame.mRectC.set(mModeFrame.getRectF());
			mFrames.add(frame);
		}
		mModeFrame.startAnimation();
	}
	
	@Override
	public void onResourceSelect(Drawable drawable) {
		if(mModeFrame != null){
			mModeFrame.setonResourceSelect(drawable);
		}
	}
	
	@Override
	protected void releaseHandlerFragment() {
		
	}

}
