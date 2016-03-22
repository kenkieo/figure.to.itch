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
	private Drawable mAddDrawable;
	
	public ModeFrameDraw(Context context) {
		mPadding = DisplayUtils.dip2px(context, 15f);
		mAddDrawable = context.getResources().getDrawable(R.drawable.icon_source_delete);
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
				if(frame.mDrawable != null){
					frame.mDrawable.setBounds(frame.mRect);
					if(frame.mColor != 0){
						frame.mDrawable.setColorFilter(frame.mColor, Mode.SRC_IN);
					}else{
						frame.mDrawable.clearColorFilter();
					}
					frame.mDrawable.setAlpha(frame.mAlpha);
					frame.mDrawable.draw(canvas);
				}else{
					mAddDrawable.setBounds(frame.mRect);
					mAddDrawable.draw(canvas);
				}
				frame.mMatrix.set(canvas.getMatrix());
				canvas.restore();
			}
		}
	}
	
	public final void drawMenu(Canvas canvas, Frame selectFrame, Drawable[] menuDrawables){
		if(selectFrame != null && selectFrame.mDrawable != null){
			canvas.save();
			
			Matrix matrix = canvas.getMatrix();
			matrix.preRotate(selectFrame.mCurrentDegrees, mCenterPoint.x, mCenterPoint.y);
			matrix.preTranslate(selectFrame.mPointC.x, selectFrame.mPointC.y);
			matrix.preRotate(selectFrame.mDrawableDegreesC, selectFrame.mRectC.centerX(), selectFrame.mRectC.centerY());
			matrix.preScale(selectFrame.mScaleC, selectFrame.mScaleC, selectFrame.mRectC.centerX(), selectFrame.mRectC.centerY());
			
			canvas.concat(matrix);

			drawMenuDrawable(canvas, selectFrame, menuDrawables);
			selectFrame.mMenuMatrix.set(canvas.getMatrix());
			canvas.restore();
		}
	}
	
	private void drawMenuDrawable(Canvas canvas, Frame selectFrame, Drawable[] menuDrawables){
		RectF rectF = selectFrame.mRectC;
		float left 		= rectF.left   - mPadding;
		float top 		= rectF.top    - mPadding;
		float right 	= rectF.right  + mPadding;
		float bottom 	= rectF.bottom + mPadding;
		canvas.drawRect(left, top, right, bottom, mPaint);
		
		drawItemDrawable(canvas, left, 	top, 	selectFrame.mScaleC, menuDrawables[0]);
		drawItemDrawable(canvas, right, top,  	selectFrame.mScaleC, menuDrawables[1]);
		drawItemDrawable(canvas, left,  bottom, selectFrame.mScaleC, menuDrawables[2]);
		drawItemDrawable(canvas, right, bottom, selectFrame.mScaleC, menuDrawables[3]);
	}

	private final void drawItemDrawable(Canvas canvas, float left, float top, float scale, Drawable drawable){
		int drawableWidth  = drawable.getIntrinsicWidth();
		int drawableHeight = drawable.getIntrinsicHeight();
		
		try {
			drawableWidth  = (int) (drawableWidth  / scale);
			drawableHeight = (int) (drawableHeight / scale);
		} catch (Exception e) {
		}

		int dLeft 	= (int) (left - drawableWidth  / 2);
		int dTop 	= (int) (top  - drawableHeight / 2);
		int dRight 	= dLeft + drawableWidth;
		int dBottom	= dTop  + drawableHeight;
		drawable.setBounds(dLeft, dTop, dRight, dBottom);
		drawable.draw(canvas);
	}
	
}
