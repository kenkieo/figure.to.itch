package com.example.com.demo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class ParameterColorIcon extends View{
	
	private int 	mColor;
	private boolean mIsChangeColor;
	private int     mChangeColor;
	private Paint 	mPaint;
	private float   mCx;
	private float   mCy;
	private float   mRadius;

	public ParameterColorIcon(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
	}
	
	@Override
	public void draw(Canvas canvas) {
		if(mPaint != null){
			mPaint.setColor(mColor);
			canvas.drawCircle(mCx, mCy, mRadius, mPaint);
		}
		
		if(getBackground() != null){
			getBackground().setState(getDrawableState());
			getBackground().setBounds(0, 0, getWidth(), getHeight());
			getBackground().draw(canvas);
		}
	}
	
	@Override
	public void setSelected(boolean selected) {
		mIsChangeColor = false;
		super.setSelected(selected);
	}
	
	public void setColor(int color) {
		mIsChangeColor = false;
		this.mColor = color | 0xFF000000;
		invalidate();
	}
	
	public void setRed(int red){
		mIsChangeColor = true;
		this.mChangeColor = Color.argb(Color.alpha(mColor), red, Color.green(mColor), Color.blue(mColor));
	}
	
	public void setGreen(int greed){	
		mIsChangeColor = true;
		this.mChangeColor = Color.argb(Color.alpha(mColor), Color.red(mColor), greed, Color.blue(mColor));
	}
	
	public void setBlue(int blue){	
		mIsChangeColor = true;
		this.mChangeColor = Color.argb(Color.alpha(mColor), Color.red(mColor), Color.green(mColor), blue);
	}
	
	public int getColor() {
		if(mIsChangeColor){
			return mChangeColor;
		}
		return mColor;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mCx = getMeasuredWidth()  / 2;
		mCy = getMeasuredHeight() / 2;
		mRadius = getMeasuredWidth() / 2 - 1;
	}
	
}
