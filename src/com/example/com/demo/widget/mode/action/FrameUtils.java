package com.example.com.demo.widget.mode.action;

import java.util.List;

import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import com.example.com.demo.bean.Frame;
import com.example.com.demo.utils.Point;

public class FrameUtils {
	
	private static final int POINT_DEGREES	= 13;
	private static final int POINT_LOCATION	= 36;

	public static boolean changeDegrees(Frame frame){
		float degrees = frame.mLastDegrees - frame.mCurrentDegrees;
		if(degrees != 0){
			if(Math.abs(degrees) > POINT_DEGREES){
				if(degrees < 0){
					degrees = - POINT_DEGREES;
				}else{
					degrees = POINT_DEGREES;
				}
			}
			frame.mCurrentDegrees = frame.mCurrentDegrees + degrees;
			return true;
		}
		return false;
	}
	
	public static boolean changeLocation(Frame frame){
		float padding = frame.mRectL.top - frame.mRectC.top;
		if(padding != 0){
			if(Math.abs(padding) > POINT_LOCATION){
				if(padding < 0){
					padding = -POINT_LOCATION;
				}else{
					padding = POINT_LOCATION;
				}
			}
			frame.mRectC.offset(0, padding);
			return true;
		}
		return false;
	}
	
	public static void keepPreValue(Frame frame, RectF initRectF){
		frame.mDrawableDegreesP = frame.mDrawableDegreesC;
		frame.mPointP.set(frame.mPointC);
		frame.mScaleP = frame.mScaleC;
		frame.mMatrix.mapRect(frame.mRealRect, initRectF);
	}
	
	public static MenuMode checkMenuContains(Frame frame, Point point, Drawable[] menuDrawables){
		MenuMode menuMode = MenuMode.IDE;
		if(checkMenuContains(frame, point, menuDrawables[0])){
			menuMode = MenuMode.DEL;
		}else if(checkMenuContains(frame, point, menuDrawables[1])){
			menuMode = MenuMode.ROTATE;
		}else if(checkMenuContains(frame, point, menuDrawables[2])){
			menuMode = MenuMode.MIRROR;
		}else if(checkMenuContains(frame, point, menuDrawables[3])){
			menuMode = MenuMode.SCALE;
		}
		return menuMode;
	}
	
	private final static boolean checkMenuContains(Frame frame, Point point, Drawable drawable){
		RectF mDst = new RectF();
		RectF mSrc = new RectF();
		mSrc.set(drawable.getBounds());
		
		Matrix matrix = frame.mMenuMatrix;
		matrix.mapRect(mDst, mSrc);
		
		return mDst.contains(point.x, point.y);
	}
	
	public static final void delDrawable(Frame selectFrame, List<Frame> frames, boolean isLock){
		if(isLock){
			for (Frame frame : frames) {
				frame.mDrawable = null;
			}
		}else{
			selectFrame.mDrawable = null;
		}
	}
	
	public static final void reversalDrawable(Frame selectFrame, Frame lockFrame, List<Frame> frames, boolean isLock){
		if(isLock){
			for (Frame frame : frames) {
				frame.mMirror = !frame.mMirror;
			}
			lockFrame.mMirror = !lockFrame.mMirror;
		}else{
			selectFrame.mMirror = !selectFrame.mMirror;
		}
	}
	
	public static final void onAlphaChange(Frame selectFrame, Frame lockFrame, List<Frame> frames, boolean isLock, int alpha){
		alpha = alpha  * 255 / 100;
		if(!isLock){
			if(selectFrame != null){
				selectFrame.mAlpha = alpha;
			}
		}else {
			lockFrame.mAlpha = alpha;
			for (Frame frame : frames) {
				frame.mAlpha = alpha;
			}
		}
	}
	
	public static final void onColorChange(Frame selectFrame, Frame lockFrame, List<Frame> frames, boolean isLock, int color){
		if(!isLock){
			if(selectFrame != null){
				selectFrame.mColor = color;
			}
		}else {
			lockFrame.mColor = color;
			for (Frame frame : frames) {
				frame.mColor = color;
			}
		}
	}
	
	public static final void onResourceChange(Frame selectFrame, Frame lockFrame, List<Frame> frames, boolean isLock, Drawable drawable){
		if(!isLock){
			if(selectFrame != null){
				selectFrame.mDrawable = drawable;
			}
		}else {
			lockFrame.mDrawable = drawable;
			for (Frame frame : frames) {
				frame.mDrawable = drawable;
			}
		}
	}
	
}
