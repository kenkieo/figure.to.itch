package com.example.com.demo.widget.mode;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.com.demo.R;
import com.example.com.demo.bean.Frame;
import com.example.com.demo.utils.DisplayUtils;
import com.example.com.demo.utils.MyHandler;
import com.example.com.demo.utils.Point;

public class ModeFrame extends View {

	private static final int POINT_DEGREES	= 10;
	private static final int POINT_DISTANCE	= 10;
	private static final int INIT_NUM		= 6;
	private static final int INIT_FRAME		= 100;
	private static final long DELAY_TIME	= 10;
	
	private static final int MSG_ANIMATION	= 1;

	private int  mPadding;
	private List<Frame> mFrames;
	private RectF mRectF;
	private boolean mIsInitRect;
	private Handler mHandler;
	
	private OnLayoutChangeListener mChangeListener;
	private boolean mIsLock;
	private Point mCenterPoint = new Point();
	
	private Paint mPaint;
	private Frame mSelectFrame;
	
	private Drawable mDrawableDel;
	private Drawable mDrawableRotate;
	private Drawable mDrawableReversal;
	private Drawable mDrawableScale;
	private MenuMode mMenuMode;
	
	public ModeFrame(Context context, AttributeSet attrs) {
		super(context, attrs);
		mRectF = new RectF();
		mHandler = new MyHandler(this);
		mPadding = DisplayUtils.dip2px(getContext(), 15f);
		
		mDrawableDel 		= getResources().getDrawable(R.drawable.icon_homepage_edit_delete);
		mDrawableRotate 	= getResources().getDrawable(R.drawable.icon_homepage_edit_rotate);
		mDrawableReversal 	= getResources().getDrawable(R.drawable.icon_homepage_edit_mirror);
		mDrawableScale 		= getResources().getDrawable(R.drawable.icon_homepage_edit_scale);
		
		mPaint = new Paint();
		mPaint.setColor(getResources().getColor(R.color.common_purple));
		mPaint.setStrokeWidth(1);
		mPaint.setAntiAlias(true);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		Log.i("TAG", "onDraw-----------------");
		if(mFrames != null){
			int size = mFrames.size();
			for (int i = 0; i < size; i++) {
				Frame frame = mFrames.get(i);
				canvas.save();
				Matrix matrix = canvas.getMatrix();

				matrix.preRotate(frame.mCurrentDegrees, mCenterPoint.x, mCenterPoint.y);
				matrix.preTranslate(frame.mPointC.x, frame.mPointC.y);
				matrix.preRotate(frame.mDrawableDegreesC, frame.mRectC.centerX(), frame.mRectC.centerY());
				matrix.preScale(frame.mMirror ? -1 : 1, 1, frame.mRectC.centerX(), frame.mRectC.centerY());
				canvas.concat(matrix);
				
				mPaint.setStyle(Style.FILL);
				canvas.drawLine(0, 0, getWidth(), frame.mPointC.y, mPaint);


				frame.mRectC.round(frame.mRect);
				frame.mDrawable.setBounds(frame.mRect);
				frame.mDrawable.setColorFilter(frame.mColor | 0xFF000000, Mode.SRC_IN);
				frame.mDrawable.draw(canvas);
				frame.mMatrix.set(canvas.getMatrix());
				canvas.restore();
			}
		}

		mPaint.setStyle(Style.STROKE);
		if(mSelectFrame != null){
			canvas.save();
			
			Matrix matrix = canvas.getMatrix();
			matrix.preRotate(mSelectFrame.mCurrentDegrees, mCenterPoint.x, mCenterPoint.y);
			matrix.preTranslate(mSelectFrame.mPointC.x, mSelectFrame.mPointC.y);
			matrix.preRotate(mSelectFrame.mDrawableDegreesC, mSelectFrame.mRectC.centerX(), mSelectFrame.mRectC.centerY());
			canvas.concat(matrix);
			
			drawMenu(canvas, mSelectFrame.mRectC);
			mSelectFrame.mMenuMatrix.set(canvas.getMatrix());
			canvas.restore();
		}
	}
	
	private void drawMenu(Canvas canvas, RectF rectF){
		float left 		= rectF.left   - mPadding;
		float top 		= rectF.top    - mPadding;
		float right 	= rectF.right  + mPadding;
		float bottom 	= rectF.bottom + mPadding;
		canvas.drawRect(left, top, right, bottom, mPaint);
		
		int dLeft 	= (int) (left - mDrawableDel.getIntrinsicWidth()  / 2);
		int dTop 	= (int) (top  - mDrawableDel.getIntrinsicHeight() / 2);
		int dRight 	= dLeft + mDrawableDel.getIntrinsicWidth();
		int dBottom	= dTop  + mDrawableDel.getIntrinsicHeight();
		mDrawableDel.setBounds(dLeft, dTop, dRight, dBottom);
		mDrawableDel.draw(canvas);
		
		dLeft 	= (int) (right - mDrawableRotate.getIntrinsicWidth() / 2);
		dTop 	= (int) (top - mDrawableRotate.getIntrinsicHeight()  / 2);
		dRight 	= dLeft + mDrawableRotate.getIntrinsicWidth();
		dBottom	= dTop  + mDrawableRotate.getIntrinsicHeight();
		mDrawableRotate.setBounds(dLeft, dTop, dRight, dBottom);
		mDrawableRotate.draw(canvas);
		
		dLeft 	= (int) (left 	- mDrawableReversal.getIntrinsicWidth()  / 2);
		dTop 	= (int) (bottom - mDrawableReversal.getIntrinsicHeight() / 2);
		dRight 	= dLeft + mDrawableReversal.getIntrinsicWidth();
		dBottom	= dTop  + mDrawableReversal.getIntrinsicHeight();
		mDrawableReversal.setBounds(dLeft, dTop, dRight, dBottom);
		mDrawableReversal.draw(canvas);
		
		dLeft 	= (int) (right  - mDrawableScale.getIntrinsicWidth()  / 2);
		dTop 	= (int) (bottom - mDrawableScale.getIntrinsicHeight() / 2);
		dRight 	= dLeft + mDrawableScale.getIntrinsicWidth();
		dBottom	= dTop  + mDrawableScale.getIntrinsicHeight();
		mDrawableScale.setBounds(dLeft, dTop, dRight, dBottom);
		mDrawableScale.draw(canvas);
	}
	
	public void setFrames(List<Frame> frames) {
		this.mFrames = frames;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if(!mIsInitRect){
			mIsInitRect = true;
			mCenterPoint.x	= getMeasuredWidth() / 2;
			mCenterPoint.y	= getMeasuredHeight() / 2;
			
			mRectF.left 	= (getMeasuredWidth() - INIT_FRAME) / 2;
			mRectF.top  	= (getMeasuredHeight() / 2 - INIT_FRAME) / 2;
			mRectF.right	= mRectF.left + INIT_FRAME;
			mRectF.bottom	= mRectF.top + INIT_FRAME;
		}
	}
	
	public void startAnimation(){
		if(mHandler != null){
			mHandler.sendEmptyMessageDelayed(MSG_ANIMATION, DELAY_TIME);
		}
	}
	
	protected void handleMessage(Message msg) {
		Log.i("TAG", "handleMessage");
		boolean stop = true;
		for (int i = 0; i < mFrames.size(); i++) {
			Frame frame = mFrames.get(i);
			if(Math.abs(frame.mLastDegrees - frame.mCurrentDegrees) > POINT_DEGREES){
				if(frame.mLastDegrees > frame.mCurrentDegrees){
					frame.mCurrentDegrees += POINT_DEGREES;
				}else{
					frame.mCurrentDegrees -= POINT_DEGREES;
				}
				stop &= false;
			}else{
				frame.mCurrentDegrees = frame.mLastDegrees;
				stop &= true;
			}
			
		}
		
		invalidate();
		if(!stop){
			startAnimation();
		}
		
	}
	
	private static final long MAX_SING_DOWN_TIME = 750;
	private Point mDownPoint = new Point();
	private Point mMovePoint = new Point();
	private long  mDownTime;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		float x = event.getX();
		float y = event.getY();
		checkData(x, y);
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			mDownPoint.x = x;
			mDownPoint.y = y;
			mDownTime = System.currentTimeMillis();
			
			for (Frame frame : mFrames) {
				frame.mDrawableDegreesP = frame.mDrawableDegreesC;
				frame.mMatrix.mapRect(frame.mRealRect, frame.mRectC);
				frame.mPointP.set(frame.mPointC);
				frame.mScaleP = frame.mScaleC;
			}
			if(mSelectFrame != null){
				if(checkMenuContains(mDrawableDel)){
					mMenuMode = MenuMode.DEL;
				}else if(checkMenuContains(mDrawableRotate)){
					mMenuMode = MenuMode.ROTATE;
				}else if(checkMenuContains(mDrawableReversal)){
					mMenuMode = MenuMode.MIRROR;
				}else if(checkMenuContains(mDrawableScale)){
					mMenuMode = MenuMode.SCALE;
				}else if(mSelectFrame.mRealRect.contains(mDownPoint.x, mDownPoint.y)){
					mMenuMode = MenuMode.MOVE;
				}else{
					mMenuMode = MenuMode.IDE;
				}
			}else{
				mMenuMode = MenuMode.IDE;
			}
			Log.i("TAG", "mMenuMode:" + mMenuMode);
			break;
		case MotionEvent.ACTION_MOVE:
			if(mSelectFrame != null){
				switch (mMenuMode) {
				case DEL:
					break;
				case SCALE:
					float scaleX = Math.abs(mDownPoint.x - x);
					float scaleY = Math.abs(mDownPoint.y - y);
					float tmp = scaleX;
					if(tmp < scaleY) tmp = scaleY;
					
					if(mIsLock){
//						for (Frame frame : mFrames) {
//							frame.mRectC.left 	= frame.mRectP.left - tmp;
//							frame.mRectC.top   	= frame.mRectP.top - tmp;
//							frame.mRectC.right  = frame.mRectP.right + tmp;
//							frame.mRectC.bottom = frame.mRectP.bottom + tmp;
//						}
//					}else{
//						mSelectFrame.mRectC.left 	= mSelectFrame.mRectP.left - tmp;
//						mSelectFrame.mRectC.top   	= mSelectFrame.mRectP.top - tmp;
//						mSelectFrame.mRectC.right   = mSelectFrame.mRectP.right + tmp;
//						mSelectFrame.mRectC.bottom  = mSelectFrame.mRectP.bottom + tmp;
					}
				case ROTATE:
					Log.i("TAG", "ROTATE");
					float rPts[]  = new float[]{mSelectFrame.mRectC.centerX(), mSelectFrame.mRectC.centerY()};
					float dPts[]  = new float[2];
					mSelectFrame.mMenuMatrix.mapPoints(dPts, rPts);
					double atan1  = Math.atan2(mDownPoint.y - dPts[1], mDownPoint.x - dPts[0]);
					double atan2  = Math.atan2(y - dPts[1], x - dPts[0]);
					float degrees = Float.valueOf(String.valueOf(180 * atan2/ Math.PI - 180 * atan1 / Math.PI));
					Log.i("TAG", "degrees:" + degrees);
					if(mIsLock){
						for (Frame frame : mFrames) {
							frame.mDrawableDegreesC = frame.mDrawableDegreesP + degrees;
						}
					}else{
						mSelectFrame.mDrawableDegreesC = mSelectFrame.mDrawableDegreesP + degrees;
					}
					break;
				case MOVE:
					Log.i("TAG", "MOVE-------------------------------------------");
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
					}else{
						mSelectFrame.mPointC.x	= mSelectFrame.mPointP.x + dPts1[0] - dPts2[0];
						mSelectFrame.mPointC.y	= mSelectFrame.mPointP.y + dPts1[1] - dPts2[1];
					}
					Log.i("TAG", "mSelectFrame:" + mSelectFrame.mPointP.toString());
					Log.i("TAG", "mSelectFrame:" + mSelectFrame.mPointC.toString());
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
					break;
				case ROTATE:
					
					break;
				case MIRROR:
					if(mIsLock){
						for (Frame frame : mFrames) {
							frame.mMirror = !frame.mMirror;
						}
					}else{
						mSelectFrame.mMirror = !mSelectFrame.mMirror;
					}
					break;
				case IDE:
					mSelectFrame = null;
					for (Frame frame : mFrames) {
						if(frame.mRealRect.contains(mDownPoint.x, mDownPoint.y)){
							mSelectFrame = frame;
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
	
	private void checkData(float x, float y) {
		if(mSelectFrame != null){
//			Log.i("TAG", "checkData------------------------------------------");
//			float rPts1[] = new float[]{x, y};
//			float dPts1[] = new float[2];
//
//			float rPts2[] = new float[]{mDownPoint.x, mDownPoint.y};
//			float dPts2[] = new float[2];
//			
//			Log.i("TAG", mSelectFrame.mRectC.toString());
//			
//			Log.i("TAG", "rPts1:" + rPts1[0] + "---" + rPts1[1]);
//			Log.i("TAG", "rPts2:" + rPts2[0] + "---" + rPts2[1]);
//			
//			Matrix matrix = mSelectFrame.mMatrix;
//			Log.i("TAG", matrix.toString());
//			matrix.mapPoints(dPts1, rPts1);
//			matrix.mapPoints(dPts2, rPts2);
//			
//			Log.i("TAG", "dPts1:" + dPts1[0] + "---" + dPts1[1]);
//			Log.i("TAG", "dPts2:" + dPts2[0] + "---" + dPts2[1]);
//			mSelectFrame.mMatrix.invert(matrix);
//			
//			matrix.mapPoints(dPts1, rPts1);
//			matrix.mapPoints(dPts2, rPts2);
//			
//			Log.i("TAG", "dPts1:" + dPts1[0] + "---" + dPts1[1]);
//			Log.i("TAG", "dPts2:" + dPts2[0] + "---" + dPts2[1]);
		}
	}

	private boolean checkMenuContains(Drawable drawable){
		RectF mDst = new RectF();
		RectF mSrc = new RectF();
		mSrc.set(drawable.getBounds());
		
		Matrix matrix = mSelectFrame.mMenuMatrix;
		matrix.mapRect(mDst, mSrc);
		
		return mDst.contains(mDownPoint.x, mDownPoint.y);
	}
	
	public enum MenuMode{
		IDE,//没有任何状态
		MOVE,
		DEL,//删除
		ROTATE,//旋转
		MIRROR,//反转
		SCALE//按比例放大
	}
	
	public void setIsLock(boolean isLock) {
		this.mIsLock = isLock;
	}
	
	public RectF getRectF() {
		return mRectF;
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		if(mChangeListener != null){
			mChangeListener.onLayoutChange();
			mChangeListener = null;
		}
	}
	
	public void setOnLayoutChangeListener(OnLayoutChangeListener l) {
		this.mChangeListener = l;
	}
	
	public interface OnLayoutChangeListener{
		void onLayoutChange();
	}
	
}
