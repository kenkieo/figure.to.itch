package com.example.com.demo.widget.mode;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.com.demo.R;
import com.example.com.demo.bean.Frame;
import com.example.com.demo.interfaces.OnParameterChangeListener;
import com.example.com.demo.utils.HandlerUtils;
import com.example.com.demo.utils.MyHandler;
import com.example.com.demo.utils.Point;
import com.example.com.demo.widget.mode.action.FrameUtils;
import com.example.com.demo.widget.mode.action.MenuMode;
import com.example.com.demo.widget.mode.action.ModeFrameDraw;

public class ModeFrame extends View implements OnParameterChangeListener{

	private static final int INIT_FRAME		= 100;
	private static final long DELAY_TIME	= 10;
	
	private static final int MSG_ANIMATION	= 1;
	private static final int MSG_ADD		= 2;

	private List<Frame> mFrames;
	private RectF 	mInitRectF;
	
	private boolean mIsInitRect;
	private Handler mHandler;
	
	private OnModeFrameAction mAction;
	private boolean 	mIsLock;
	private Point   	mCenterPoint = new Point();
	private Frame 		mSelectFrame;
	private Frame	 	mLockFrame;
	private Drawable    mMenuDrawables[];
	private MenuMode 	mMenuMode;
	
	public ModeFrame(Context context, AttributeSet attrs) {
		super(context, attrs);
		mInitRectF 			= new RectF();
		mHandler 			= new MyHandler(this);
		mMenuDrawables		= new Drawable[]{
				getResources().getDrawable(R.drawable.icon_homepage_edit_delete), 
				getResources().getDrawable(R.drawable.icon_homepage_edit_rotate), 
				getResources().getDrawable(R.drawable.icon_homepage_edit_mirror),
				getResources().getDrawable(R.drawable.icon_homepage_edit_scale)};
		mLockFrame 			= new Frame();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		ModeFrameDraw.getInst(getContext()).drawFrames(canvas, mFrames);
		ModeFrameDraw.getInst(getContext()).drawMenu(canvas, mSelectFrame, mMenuDrawables);
	}
	
	public void setFrames(List<Frame> frames) {
		this.mFrames = frames;
	}
	
	public void startAnimation(){
		HandlerUtils.sendEmptyMessageDelayed(mHandler, MSG_ANIMATION, DELAY_TIME);
	}
	
	private void startAnimationForAdd(){
		HandlerUtils.sendEmptyMessageDelayed(mHandler, MSG_ADD, DELAY_TIME);
	}
	
	protected void handleMessage(Message msg) {
		switch (msg.what) {
		case MSG_ANIMATION:{
			boolean stop = true;
			for (Frame frame : mFrames) {
				stop &= !FrameUtils.changeDegrees(frame);
			}
			
			invalidate();
			if(!stop){
				startAnimation();
			}
			break;
		}
		case MSG_ADD:{
			boolean stop = true;
			for (Frame frame : mFrames) {
				boolean isLC = FrameUtils.changeLocation(frame); 
				boolean isDC = FrameUtils.changeDegrees(frame);
				stop &=  !isLC || !isDC;
			}
			if(stop){
				invalidate();
				startAnimationForAdd();
				break;
			}
		}
		default:
			break;
		}
	}
	
	private static final long MAX_SING_DOWN_TIME = 750;
	private Point mDownPoint = new Point();
	private Point mMovePoint = new Point();
	private long  mDownTime;
	
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		float x = event.getX();
		float y = event.getY();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			mDownPoint.x = x;
			mDownPoint.y = y;
			mDownTime = System.currentTimeMillis();
			
			mLockFrame.mDrawableDegreesP = mLockFrame.mDrawableDegreesC;
			mLockFrame.mPointP.set(mLockFrame.mPointC);
			mLockFrame.mScaleP = mLockFrame.mScaleC;
			for (Frame frame : mFrames) {
				frame.mDrawableDegreesP = frame.mDrawableDegreesC;
				frame.mPointP.set(frame.mPointC);
				frame.mScaleP = frame.mScaleC;
				frame.mMatrix.mapRect(frame.mRealRect, mInitRectF);
			}
			if(mSelectFrame != null){
				if(checkMenuContains(mMenuDrawables[0])){
					mMenuMode = MenuMode.DEL;
				}else if(checkMenuContains(mMenuDrawables[1])){
					mMenuMode = MenuMode.ROTATE;
				}else if(checkMenuContains(mMenuDrawables[2])){
					mMenuMode = MenuMode.MIRROR;
				}else if(checkMenuContains(mMenuDrawables[3])){
					mMenuMode = MenuMode.SCALE;
				}else if(mSelectFrame.mDrawable != null && mSelectFrame.mRealRect.contains(mDownPoint.x, mDownPoint.y)){
					mMenuMode = MenuMode.MOVE;
				}else{
					mMenuMode = MenuMode.IDE;
				}
			}else{
				mMenuMode = MenuMode.IDE;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if(mSelectFrame != null){
				switch (mMenuMode) {
				case SCALE:{
					float rPts1[] = new float[]{x, y};
					float dPts1[] = new float[2];

					float rPts2[] = new float[]{mDownPoint.x, mDownPoint.y};
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
				case ROTATE:
					float rPts[]  = new float[]{mInitRectF.centerX(), mInitRectF.centerY()};
					float dPts[]  = new float[2];
					mSelectFrame.mMenuMatrix.mapPoints(dPts, rPts);
					double atan1  = Math.atan2(mDownPoint.y - dPts[1], mDownPoint.x - dPts[0]);
					double atan2  = Math.atan2(y - dPts[1], x - dPts[0]);
					float degrees = Float.valueOf(String.valueOf(180 * atan2/ Math.PI - 180 * atan1 / Math.PI));
					if(mIsLock){
						for (Frame frame : mFrames) {
							frame.mDrawableDegreesC = frame.mDrawableDegreesP + degrees;
						}
						mLockFrame.mDrawableDegreesC = mLockFrame.mDrawableDegreesP + degrees;
					}else{
						mSelectFrame.mDrawableDegreesC = mSelectFrame.mDrawableDegreesP + degrees;
					}
					break;
				case MOVE:
					float rPts1[] = new float[]{x, y};
					float dPts1[] = new float[2];

					float rPts2[] = new float[]{mDownPoint.x, mDownPoint.y};
					float dPts2[] = new float[2];
					
					Matrix inverse = new Matrix();
					inverse.preRotate(-mSelectFrame.mCurrentDegrees, mCenterPoint.x, mCenterPoint.y);
					inverse.mapPoints(dPts1, rPts1);
					inverse.mapPoints(dPts2, rPts2);
					if(mIsLock){
						for (Frame frame : mFrames) {
							frame.mPointC.x	= frame.mPointP.x + dPts1[0] - dPts2[0];
							frame.mPointC.y	= frame.mPointP.y + dPts1[1] - dPts2[1];
						}
						mLockFrame.mPointC.x	= mLockFrame.mPointP.x + dPts1[0] - dPts2[0];
						mLockFrame.mPointC.y	= mLockFrame.mPointP.y + dPts1[1] - dPts2[1];
					}else{
						mSelectFrame.mPointC.x	= mSelectFrame.mPointP.x + dPts1[0] - dPts2[0];
						mSelectFrame.mPointC.y	= mSelectFrame.mPointP.y + dPts1[1] - dPts2[1];
					}
					break;
				default:
					break;
				}
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			long time = System.currentTimeMillis();
			if(time - mDownTime < MAX_SING_DOWN_TIME){
				switch (mMenuMode) {
				case DEL:
					if(mIsLock){
						for (Frame frame : mFrames) {
							frame.mDrawable = null;
						}
					}else{
						mSelectFrame.mDrawable = null;
					}
					break;
				case MIRROR:
					if(mIsLock){
						for (Frame frame : mFrames) {
							frame.mMirror = !frame.mMirror;
						}
						mLockFrame.mMirror = !mLockFrame.mMirror;
					}else{
						mSelectFrame.mMirror = !mSelectFrame.mMirror;
					}
					break;
				case IDE:
					mSelectFrame = null;
					for (Frame frame : mFrames) {
						if(frame.mRealRect.contains(mDownPoint.x, mDownPoint.y)){
							mSelectFrame = frame;
							if(frame.mDrawable == null){
								if(mAction != null){
									mAction.showStyleResourceFragment();
								}
							}
							break;
						}
					}
					break;
				default:
					break;
				}
			}
			break;
		}
		mMovePoint.x = y;
		mMovePoint.y = y;
		invalidate();
		return true;
	}

	private boolean checkMenuContains(Drawable drawable){
		RectF mDst = new RectF();
		RectF mSrc = new RectF();
		mSrc.set(drawable.getBounds());
		
		Matrix matrix = mSelectFrame.mMenuMatrix;
		matrix.mapRect(mDst, mSrc);
		
		return mDst.contains(mDownPoint.x, mDownPoint.y);
	}
	
	public void setIsLock(boolean isLock) {
		this.mIsLock = isLock;
		for (Frame frame : mFrames) {
			Frame.clone(mLockFrame, frame);
			frame.mRectC.set(frame.mRectL);
		}
		invalidate();
	}
	
	public RectF getRectF() {
		return mInitRectF;
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		if(!mIsInitRect){
			mIsInitRect = true;
			mCenterPoint.x	= getMeasuredWidth() / 2;
			mCenterPoint.y	= getMeasuredHeight() / 2;
			ModeFrameDraw.getInst(getContext()).setCenterPoint(mCenterPoint);
			
			mInitRectF.left 	= (getMeasuredWidth() - INIT_FRAME) / 2;
			mInitRectF.top  	= (getMeasuredHeight() / 2 - INIT_FRAME) / 2;
			mInitRectF.right	= mInitRectF.left + INIT_FRAME;
			mInitRectF.bottom	= mInitRectF.top  + INIT_FRAME;
			
			mLockFrame.mRectL.set(mInitRectF);
			
			mLockFrame.mRectC.left		= (getMeasuredWidth()  - INIT_FRAME) / 2;
			mLockFrame.mRectC.top  		= (getMeasuredHeight() - INIT_FRAME) / 2;
			mLockFrame.mRectC.right		= mLockFrame.mRectC.left + INIT_FRAME;
			mLockFrame.mRectC.bottom	= mLockFrame.mRectC.top  + INIT_FRAME;
			if(mAction != null){
				mAction.onLayoutChange();
			}
		}
	}
	
	public void setOnModeFrameAction(OnModeFrameAction action) {
		this.mAction = action;
	}
	
	public void setonResourceSelect(Drawable drawable){
		if(!mIsLock){
			if(mSelectFrame != null){
				mSelectFrame.mDrawable = drawable;
			}
		}else {
			mLockFrame.mDrawable = drawable;
			for (Frame frame : mFrames) {
				frame.mDrawable = drawable;
			}
		}
		invalidate();
	}
	
	@Override
	public void onAlphaChange(int alpha) {
		alpha = alpha  * 255 / 100;
		if(!mIsLock){
			if(mSelectFrame != null){
				mSelectFrame.mAlpha = alpha;
			}
		}else {
			mLockFrame.mAlpha = alpha;
			for (Frame frame : mFrames) {
				frame.mAlpha = alpha;
			}
		}
		invalidate();
	}
	
	@Override
	public void onColorChange(int color) {
		if(!mIsLock){
			if(mSelectFrame != null){
				mSelectFrame.mColor = color;
			}
		}else {
			mLockFrame.mColor = color;
			for (Frame frame : mFrames) {
				frame.mColor = color;
			}
		}
		invalidate();
	}
	
	@Override
	public void onTimesChange(int times) {
		HandlerUtils.removeCallbacksAndMessages(mHandler);
		int size = mFrames.size();
		float degrees = 360.0f / times;
		if(times >= size){
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
		}else{
			for (int i = times; i < size; i++) {
				Frame frame = mFrames.remove(i);
				if(frame.equals(mSelectFrame)){
					mSelectFrame = null;
				}
			}
			invalidate();
		}
	}
	
	public void setDefaultDrawable(Drawable defaultDrawable){
		mLockFrame.mDrawable = defaultDrawable;
	}
	
	public void hideSelectFrame(){
		mSelectFrame = null;
		invalidate();
	}
	
	public interface OnModeFrameAction{
		void onLayoutChange();
		
		void showStyleResourceFragment();
	}
	
}
