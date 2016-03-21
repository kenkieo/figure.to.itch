package com.example.com.demo.widget.mode.action;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import com.example.com.demo.R;
import com.example.com.demo.bean.Frame;
import com.example.com.demo.utils.DisplayUtils;
import com.example.com.demo.utils.Point;

public class ModeFrameDraw {
	
	private static ModeFrameDraw mInst = null;
	private static Object mLock = new Object();
	private int  	mPadding;
	private Paint 	mPaint;
	private Point   mCenterPoint = new Point();
	
	public ModeFrameDraw(Context context) {
		mPadding = DisplayUtils.dip2px(context, 15f);
		
		mPaint = new Paint();
		mPaint.setColor(context.getResources().getColor(R.color.common_purple));
		mPaint.setStrokeWidth(1);
		mPaint.setStyle(Style.STROKE);
		mPaint.setAntiAlias(true);
	}
	
	public void setCenterPoint(Point point){
		mCenterPoint.x = point.x;
		mCenterPoint.y = point.y;
	}
	
	public static ModeFrameDraw getInst(Context context){
		if(mInst == null){
			synchronized(mLock){
				if(mInst == null){
					mInst = new ModeFrameDraw(context);
				}
			}
		}
		return mInst;
	}

	public final void drawFrames(Canvas canvas, List<Frame> frames){
		if(frames != null){
			for (Frame frame : frames) {
				canvas.save();
				Matrix matrix = canvas.getMatrix();

				matrix.preRotate(frame.mCurrentDegrees, mCenterPoint.x, mCenterPoint.y);//绕中心点旋转
				matrix.preTranslate(frame.mPointC.x, frame.mPointC.y);//移动
				matrix.preRotate(frame.mDrawableDegreesC, frame.mRectC.centerX(), frame.mRectC.centerY());//自身旋转
				matrix.preScale(frame.mMirror ? -1 : 1, 1, frame.mRectC.centerX(), frame.mRectC.centerY());//自身反转
				matrix.preScale(frame.mScaleC, frame.mScaleC, frame.mRectC.centerX(), frame.mRectC.centerY());//自身放大
				canvas.concat(matrix);
				
				frame.mRectC.round(frame.mRect);
				frame.mDrawable.setBounds(frame.mRect);
				if(frame.mChangeColor){
					frame.mDrawable.setColorFilter(frame.mColor | 0xFF000000, Mode.SRC_IN);
				}
				frame.mDrawable.setAlpha(frame.mAlpha);
				frame.mDrawable.draw(canvas);
				frame.mMatrix.set(canvas.getMatrix());
				canvas.restore();
			}
		}
	}
	
	public final void drawMenu(Canvas canvas, Frame selectFrame, Drawable[] menuDrawables){
		if(selectFrame != null){
			canvas.save();
			
			Matrix matrix = canvas.getMatrix();
			matrix.preRotate(selectFrame.mCurrentDegrees, mCenterPoint.x, mCenterPoint.y);
			matrix.preTranslate(selectFrame.mPointC.x, selectFrame.mPointC.y);
			matrix.preRotate(selectFrame.mDrawableDegreesC, selectFrame.mRectC.centerX(), selectFrame.mRectC.centerY());
			matrix.preScale(selectFrame.mScaleC, selectFrame.mScaleC, selectFrame.mRectC.centerX(), selectFrame.mRectC.centerY());
			canvas.concat(matrix);
			
			drawMenu(canvas, selectFrame.mRectC, menuDrawables);
			selectFrame.mMenuMatrix.set(canvas.getMatrix());
			canvas.restore();
		}
	}
	
	private void drawMenu(Canvas canvas, RectF rectF, Drawable[] menuDrawables){
		float left 		= rectF.left   - mPadding;
		float top 		= rectF.top    - mPadding;
		float right 	= rectF.right  + mPadding;
		float bottom 	= rectF.bottom + mPadding;
		canvas.drawRect(left, top, right, bottom, mPaint);
		
		int dLeft 	= (int) (left - menuDrawables[0].getIntrinsicWidth()  / 2);
		int dTop 	= (int) (top  - menuDrawables[0].getIntrinsicHeight() / 2);
		int dRight 	= dLeft + menuDrawables[0].getIntrinsicWidth();
		int dBottom	= dTop  + menuDrawables[0].getIntrinsicHeight();
		menuDrawables[0].setBounds(dLeft, dTop, dRight, dBottom);
		menuDrawables[0].draw(canvas);
		
		dLeft 	= (int) (right - menuDrawables[1].getIntrinsicWidth() / 2);
		dTop 	= (int) (top - menuDrawables[1].getIntrinsicHeight()  / 2);
		dRight 	= dLeft + menuDrawables[1].getIntrinsicWidth();
		dBottom	= dTop  + menuDrawables[1].getIntrinsicHeight();
		menuDrawables[1].setBounds(dLeft, dTop, dRight, dBottom);
		menuDrawables[1].draw(canvas);
		
		dLeft 	= (int) (left 	- menuDrawables[2].getIntrinsicWidth()  / 2);
		dTop 	= (int) (bottom - menuDrawables[2].getIntrinsicHeight() / 2);
		dRight 	= dLeft + menuDrawables[2].getIntrinsicWidth();
		dBottom	= dTop  + menuDrawables[2].getIntrinsicHeight();
		menuDrawables[2].setBounds(dLeft, dTop, dRight, dBottom);
		menuDrawables[2].draw(canvas);
		
		dLeft 	= (int) (right  - menuDrawables[3].getIntrinsicWidth()  / 2);
		dTop 	= (int) (bottom - menuDrawables[3].getIntrinsicHeight() / 2);
		dRight 	= dLeft + menuDrawables[3].getIntrinsicWidth();
		dBottom	= dTop  + menuDrawables[3].getIntrinsicHeight();
		menuDrawables[3].setBounds(dLeft, dTop, dRight, dBottom);
		menuDrawables[3].draw(canvas);
	}
	
}
