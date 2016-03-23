package com.example.com.demo.widget.mode;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
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

public abstract class ModeFrame extends View implements OnParameterChangeListener{

	private static final int INIT_FRAME		= 100;
	private static final long DELAY_TIME	= 10;
	private static final int MSG_ANIMATION	= 1;

	protected List<Frame> mFrames;
	protected RectF 	mInitRectF;
	protected boolean 	mIsInitRect;
	protected Handler 	mHandler;
	protected OnModeFrameAction mAction;
	protected boolean 	mIsLock;
	protected Point   	mCenterPoint = new Point();
	protected Frame 	mSelectFrame;
	protected Frame	 	mLockFrame;
	private   Drawable  mMenuDrawables[];
	
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
		}
	}
	
	private static final long MAX_SING_DOWN_TIME = 750;
	private Point mDownPoint = new Point();
	private Point mMovePoint = new Point();
	private long  mDownTime;
	protected MenuMode 	mMenuMode;
	
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
			
			FrameUtils.keepPreValue(mLockFrame, mInitRectF);
			for (Frame frame : mFrames) {
				FrameUtils.keepPreValue(frame, mInitRectF);
			}
			mMenuMode = MenuMode.IDE;
			if(mSelectFrame != null){
				mMenuMode = FrameUtils.checkMenuContains(mSelectFrame, mDownPoint, mMenuDrawables);
				if(mMenuMode == MenuMode.IDE){
					if(mSelectFrame.mDrawable != null && mSelectFrame.mRealRect.contains(mDownPoint.x, mDownPoint.y)){
						mMenuMode = MenuMode.MOVE;
					}					
				}
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if(mSelectFrame != null){
				switch (mMenuMode) {
				case SCALE:{
					doScale(mDownPoint.x, mDownPoint.y, x, y);
				}
				case ROTATE:{
					doRotate(mDownPoint.x, mDownPoint.y, x, y);
					break;
				}
				case MOVE:{
					doMove(mDownPoint.x, mDownPoint.y, x, y);
					break;
				}
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
					FrameUtils.delDrawable(mSelectFrame, mFrames, mIsLock);
					break;
				case MIRROR:
					FrameUtils.reversalDrawable(mSelectFrame, mLockFrame, mFrames, mIsLock);
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
	
	protected abstract void doScale(float downPointX, float downPointY, float movePointX, float movePointY);
	protected abstract void doRotate(float downPointX, float downPointY, float movePointX, float movePointY);
	protected abstract void doMove(float downPointX, float downPointY, float movePointX, float movePointY);

	public final void setIsLock(boolean isLock) {
		this.mIsLock = isLock;
		for (Frame frame : mFrames) {
			Frame.clone(mLockFrame, frame);
			frame.mRectC.set(frame.mRectL);
		}
		invalidate();
	}
	
	public final RectF getRectF() {
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
	
	public final void setOnModeFrameAction(OnModeFrameAction action) {
		this.mAction = action;
	}
	
	public final void setonResourceSelect(Drawable drawable){
		FrameUtils.onResourceChange(mSelectFrame, mLockFrame, mFrames, mIsLock, drawable);
		invalidate();
	}
	
	@Override
	public final void onAlphaChange(int alpha) {
		FrameUtils.onAlphaChange(mSelectFrame, mLockFrame, mFrames, mIsLock, alpha);
		invalidate();
	}
	
	@Override
	public final void onColorChange(int color) {
		FrameUtils.onColorChange(mSelectFrame, mLockFrame, mFrames, mIsLock, color);
		invalidate();
	}
	
	@Override
	public final void onTimesChange(int times) {
		HandlerUtils.removeCallbacksAndMessages(mHandler);
		int size = mFrames.size();
		float degrees = 360.0f / times;
		if(times >= size){
			addFrames(size, times, degrees);
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
	
	protected abstract void addFrames(int size, int times, float degrees);
	
	public final void setDefaultDrawable(Drawable defaultDrawable){
		mLockFrame.mDrawable = defaultDrawable;
	}
	
	public final void hideSelectFrame(){
		mSelectFrame = null;
		invalidate();
	}
	
	public interface OnModeFrameAction{
		void onLayoutChange();
		
		void showStyleResourceFragment();
	}
	
}
