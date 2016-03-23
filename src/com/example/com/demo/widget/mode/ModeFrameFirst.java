package com.example.com.demo.widget.mode;

import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;

import com.example.com.demo.bean.Frame;

public class ModeFrameFirst extends ModeFrame{

	public ModeFrameFirst(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void doScale(float downPointX, float downPointY, float movePointX, float movePointY) {
		float rPts1[] = new float[]{movePointX, movePointY};
		float dPts1[] = new float[2];

		float rPts2[] = new float[]{downPointX, downPointY};
		float dPts2[] = new float[2];
		
		Matrix inverse = new Matrix();
		mSelectFrame.mMatrix.invert(inverse);
		
		dPts1[0] = dPts1[0] - mInitRectF.centerX();
		dPts1[1] = dPts1[1] - mInitRectF.centerY();
		
		dPts2[0] = dPts2[0] - mInitRectF.centerX();
		dPts2[1] = dPts2[1] - mInitRectF.centerY();
		
		inverse.mapPoints(dPts1, rPts1);
		inverse.mapPoints(dPts2, rPts2);
		
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
			for (Frame frame : mFrames) {
				frame.mDrawableDegreesC = frame.mDrawableDegreesP + degrees;
			}
			mLockFrame.mDrawableDegreesC = mLockFrame.mDrawableDegreesP + degrees;
		}else{
			mSelectFrame.mDrawableDegreesC = mSelectFrame.mDrawableDegreesP + degrees;
		}
	}
	
	@Override
	protected void doMove(float downPointX, float downPointY, float movePointX, float movePointY) {
		float rPts1[] = new float[]{movePointX, movePointY};
		float dPts1[] = new float[2];

		float rPts2[] = new float[]{downPointX, downPointY};
		float dPts2[] = new float[2];
		
		Matrix inverse = new Matrix();
		inverse.preRotate(-mSelectFrame.mCurrentDegrees, mCenterPoint.x, mCenterPoint.y);
		inverse.mapPoints(dPts1, rPts1);
		inverse.mapPoints(dPts2, rPts2);
		if(mIsLock){
			for (Frame frame : mFrames) {
				moveFrame(frame, dPts2[0], dPts2[1], dPts1[0], dPts1[1]);
			}
			moveFrame(mLockFrame, dPts2[0], dPts2[1], dPts1[0], dPts1[1]);
		}else{
			moveFrame(mSelectFrame, dPts2[0], dPts2[1], dPts1[0], dPts1[1]);
		}
	}
	
	/**
	 * 
	 * @param frame
	 * @param dPtsDx downx
	 * @param dPtsDy downy
	 * @param dPtsMx movex
	 * @param dPtsMy movey
	 */
	private void moveFrame(Frame frame, float dPtsDx, float dPtsDy, float dPtsMx, float dPtsMy){
		frame.mPointC.x	= frame.mPointP.x + dPtsMx - dPtsDx;
		frame.mPointC.y	= frame.mPointP.y + dPtsMy - dPtsDy;
	}
	
	@Override
	protected void addFrames(int size, int times, float degrees) {
		for (int i = 0; i < size; i++) {
			Frame frame = mFrames.get(i);
			frame.mCurrentDegrees = degrees * i;
		}
		for (int i = size; i < times; i++) {
			Frame frame = new Frame();
			Frame.clone(mLockFrame, frame);
			frame.mCurrentDegrees = degrees * i;
			frame.mRectC.set(mInitRectF);
			mFrames.add(frame);
		}
		invalidate();
	}

}
