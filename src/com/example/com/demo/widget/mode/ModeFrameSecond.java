package com.example.com.demo.widget.mode;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;

import com.example.com.demo.bean.Frame;
import com.example.com.demo.utils.Constants;

public class ModeFrameSecond extends ModeFrame{
	
	private float mPadding;

	public ModeFrameSecond(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
	
	@Override
	protected void doScale(float downPointX, float downPointY, float movePointX, float movePointY) {
		float rPts1[] = new float[]{movePointX, movePointY};
		float dPts1[] = new float[2];

		float rPts2[] = new float[]{downPointX, downPointY};
		float dPts2[] = new float[2];
		
		Matrix inverse = new Matrix();
		mSelectFrame.mMatrix.invert(inverse);
		inverse.mapPoints(dPts1, rPts1);
		inverse.mapPoints(dPts2, rPts2);
		
		dPts1[0] = dPts1[0] - mInitRectF.centerX();
		dPts1[1] = dPts1[1] - mInitRectF.centerY();
		
		dPts2[0] = dPts2[0] - mInitRectF.centerX();
		dPts2[1] = dPts2[1] - mInitRectF.centerY();
		
		
		float radiusM = Float.parseFloat(String.valueOf(Math.sqrt(Double.parseDouble(String.valueOf(dPts1[0] * dPts1[0] + dPts1[1] * dPts1[1])))));//点击时的圆半径
		float radiusD = Float.parseFloat(String.valueOf(Math.sqrt(Double.parseDouble(String.valueOf(dPts2[0] * dPts2[0] + dPts2[1] * dPts2[1])))));//移动时的圆半径
		
		float scale = radiusM / radiusD;
		
		if(mIsLock){
			for (Frame frame : mFrames) {
				frame.mScaleC = frame.mScaleP * scale;
			}
			mLockFrame.mScaleC = mLockFrame.mScaleP * scale;
		}else{
			mSelectFrame.mScaleC = mSelectFrame.mScaleP * scale;
		}
	}
	
	@Override
	protected void doRotate(float downPointX, float downPointY, float movePointX, float movePointY) {
		float rPts[]  = new float[]{mInitRectF.centerX(), mInitRectF.centerY()};
		float dPts[]  = new float[2];
		mSelectFrame.mMenuMatrix.mapPoints(dPts, rPts);
		double atan1  = Math.atan2(downPointY - dPts[1], downPointX - dPts[0]);
		double atan2  = Math.atan2(movePointY - dPts[1], movePointX - dPts[0]);
		float degrees = Float.valueOf(String.valueOf(180 * atan2/ Math.PI - 180 * atan1 / Math.PI));
		
		if(mIsLock){
			boolean even = mSelectFrame.mPosition % 2 == 0;//当前选中位置
			boolean reverse =  !even;//如果是反向位置
			for (Frame frame : mFrames) {
				even = frame.mPosition % 2 == 0;
				if(reverse && even || !reverse && !even){
					frame.mDrawableDegreesC = frame.mDrawableDegreesP + Constants.DEGRESS - degrees;
				}else{
					frame.mDrawableDegreesC = frame.mDrawableDegreesP + degrees;
				}
				
				if(frame.mPosition == 0){
					mLockFrame.mDrawableDegreesC = frame.mDrawableDegreesC;
				}
			}
		}else{
			mSelectFrame.mDrawableDegreesC = mSelectFrame.mDrawableDegreesP + degrees;
		}
	}
	
	@Override
	public void setIsLock(boolean isLock) {
		this.mIsLock = isLock;
		for (Frame frame : mFrames) {
			Frame.cloneSecond(mLockFrame, frame);
			frame.mRectC.set(frame.mRectL);
		}
		invalidate();	
	}
	
	@Override
	protected void onInitLayout() {
		mPadding = (mInitRectF.right - mInitRectF.left) / 2;
		mInitRectF.offset(mPadding, 0);
	}
	
	public float getPadding() {
		return mPadding;
	}
	
	@Override
	protected void doMove(float downPointX, float downPointY, float movePointX, float movePointY) {}
	
	@Override
	public void onTimesChange(int times) {
		super.onTimesChange(times);
		int size = mFrames.size();
		float degrees = Constants.DEGRESS / times;
		times = times * 2;
		if(times >= size){
			for (int i = 0; i < size; i++) {
				Frame frame = mFrames.get(i);
				frame.mCurrentDegrees = degrees * (i / 2);
			}
			for (int i = size; i < times; i++) {
				Frame frame = new Frame();
				frame.mPosition = i;
				Frame.cloneSecond(mLockFrame, frame);
				frame.mRectC.set(mInitRectF);
				frame.mCurrentDegrees = degrees * (i / 2);
				if(i % 2 != 0){
					frame.mMirror = true;
					frame.mDrawableDegreesC = Constants.DEGRESS - mLockFrame.mDrawableDegreesC;
					frame.mPointC.offset(-frame.mRectC.width(), 0);
				}
				mFrames.add(frame);
			}
		}else{
			for (int i = size - 1; i >= times; i--) {
				Frame frame = mFrames.remove(i);
				if(frame.equals(mSelectFrame)){
					mSelectFrame = null;
				}
			}
			
			for (int i = 0; i < times; i++) {
				Frame frame = mFrames.get(i);
				frame.mCurrentDegrees = degrees * (i / 2);
			}
		}
		invalidate();
	}

}
