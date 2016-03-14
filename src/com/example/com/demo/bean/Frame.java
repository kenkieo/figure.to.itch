package com.example.com.demo.bean;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import com.example.com.demo.utils.Point;

public class Frame {

	public RectF	mRectC = new RectF();
	public RectF	mRectP = new RectF();
	public Rect		mRect  = new Rect();
	public RectF	mRealRect = new RectF();
	
	/*********************自身旋转用到*********************/
	public float    mDrawableDegreesC;
	public float    mDrawableDegreesL;
	public float    mDrawableDegreesP;
	
	public Matrix   mMenuMatrix = new Matrix();
	
	public Drawable mDrawable;
	public Matrix  	mMatrix = new Matrix();

	public boolean  mMirror;
	public float    mCurrentDegrees;
	public float    mLastDegrees;
	public int      mColor;
	public int      mAlpha;
	public boolean  mShowMenu;
	
	public Point 	mPointC = new Point();
	public Point 	mPointP = new Point();
	
}
