package com.example.com.demo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.com.demo.utils.DisplayUtils;
/**
 * 滑动块
 * @author Administrator
 *
 */
public class CustomSeekBar extends View{
	
	private int mPadding;
	
	private int mMin;
	private int mMax;
	private int mProgress;
	
	private int mHeight = 10;
	private int mBgColor;
	private int mProgressColor;
	
	private int mThumbColor;
	private int mThumbRadius;
	
	private int mThumbNumColor;
	private Paint mPaint;
	
	private RectF mRect;
	private float mPoint;
	
	private OnSeekBarChangeListener mSeekBarChangeListener;
	
	public CustomSeekBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		
		mMin 		= 0;
		mMax 		= 100;
		mProgress 	= 5;
		
		mHeight 		= DisplayUtils.dip2px(getContext(), 5);
		mBgColor 		= 0xFF89848B;
		mProgressColor 	= 0xFFDEA602;
		mThumbColor		= 0XFFFFFFFF;
		mThumbNumColor	= 0xFFDEA602;
		
		mRect = new RectF();
		
		mPadding = DisplayUtils.dip2px(context, 10f);
	}
	
	public void setMax(int max) {
		this.mMax = max;
	}
	
	public void setMin(int min) {
		this.mMin = min;
	}
	
	public void setProgress(int progress) {
		this.mProgress = progress;
	}

	@Override
	public void draw(Canvas canvas) {
		int left 	= 0;
		int top  	= (getHeight() - mHeight) / 2;
		int right	= getWidth();
		int bottom	= top + mHeight;
		mPaint.setColor(mBgColor);
		canvas.drawRect(left, top, right, bottom, mPaint);
		
		int progressPoint = (int) (mRect.left + mThumbRadius);
		mPaint.setColor(mProgressColor);
		canvas.drawRect(left, top, progressPoint, bottom, mPaint);
		
		mPaint.setColor(mThumbColor);
		canvas.drawCircle(progressPoint, getHeight() / 2, mThumbRadius, mPaint);
		
		mPaint.setColor(mThumbNumColor);
		mPaint.setTextSize(DisplayUtils.sp2px(getContext(), 12));
		String progressStr = String.valueOf(mProgress);
		float textWidth = mPaint.measureText(progressStr);
		canvas.drawText(progressStr, progressPoint - textWidth / 2, (getHeight() - mPaint.ascent() - mPaint.descent()) / 2, mPaint);
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		mThumbRadius = getHeight() / 2 - mPadding;
		
		int total = mMax - mMin;
		if(total == 0) total = 1;
		int currentProgress = mProgress - mMin;
		mPoint = (getWidth() - 2 * mThumbRadius) * 1.0f / total;
		
		mRect.left 	 = mPoint * currentProgress;
		mRect.top	 = mPadding;
		mRect.right  = mRect.left + mThumbRadius * 2;
		mRect.bottom = mRect.top + mThumbRadius * 2;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		float x = event.getX();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_MOVE:
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			int maxWidth = getWidth() - mThumbRadius * 2;
			if(x < 0){
				x = 0;
			}
			if(x < maxWidth){
				mRect.left 	= x;
				mRect.right = x + mThumbRadius * 2;
			}else{
				mRect.left  = maxWidth;
				mRect.right = maxWidth + mThumbRadius * 2;
			}
			mProgress = (int) (Math.floor(mRect.left / mPoint) + 0.5f) + mMin;
			
			if(mSeekBarChangeListener != null){
				mSeekBarChangeListener.onProgressChanged(this, mProgress);
			}
			break;
		}
		invalidate();
		return true;
	}
	
	public void setOnSeekBarChangeListener(OnSeekBarChangeListener l){
		mSeekBarChangeListener = l;
	}
	
	public interface OnSeekBarChangeListener{
		void onProgressChanged(CustomSeekBar seekBar, int progress);
	}
	
}
