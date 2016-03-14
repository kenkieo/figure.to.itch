package com.example.com.demo.fragment.mode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.example.com.demo.R;
import com.example.com.demo.bean.Frame;
import com.example.com.demo.fragment.BaseHandlerFragment;
import com.example.com.demo.widget.mode.ModeFrame;
import com.example.com.demo.widget.mode.ModeFrame.OnLayoutChangeListener;

public class StyleFirstFragment extends BaseHandlerFragment implements OnLayoutChangeListener {
	
	public Drawable mDefaultDrawable;
	private static final int INIT_NUM = 6;
	
	private List<Frame> mFrames;
	private ModeFrame modeFrame;
	
	@Override
	protected int getLayoutRes() {
		return R.layout.layout_frame;
	}

	@Override
	protected void initViews(View convertView) {
		mDefaultDrawable = getResources().getDrawable(R.drawable.icon_default_source_1);
		modeFrame = (ModeFrame) convertView.findViewById(R.id.layout_frame);
		modeFrame.setIsLock(true);
	}
	
	@Override
	protected void initData() {
		super.initData();
		mFrames = new ArrayList<Frame>();
		modeFrame.setFrames(mFrames);
		modeFrame.setOnLayoutChangeListener(this);
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
			frame.mRectC.set(modeFrame.getRectF());
			mFrames.add(frame);
		}
		modeFrame.startAnimation();
	}
	
	private int getVertexX(int left, int right){
		return 0;
	}
	
	@Override
	protected void releaseHandlerFragment() {
		
	}

}
