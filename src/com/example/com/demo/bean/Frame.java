package com.example.com.demo.bean;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import com.example.com.demo.utils.Point;

public class Frame {

	public RectF	mRectC = new RectF();
	public RectF	mRectL = new RectF();
	public Rect		mRect  = new Rect();
	public RectF	mRealRect = new RectF();
	
	/*********************自身旋转用到*********************/
	public float    mDrawableDegreesC;
	public float    mDrawableDegreesL;
	public float    mDrawableDegreesP;
	
	public Matrix   mMenuMatrix = new Matrix();//菜单参数
	
	public Drawable mDrawable;
	public Matrix  	mMatrix = new Matrix();//自身参数

	public boolean  mMirror;//是否反转
	public float    mCurrentDegrees;//当前角度(对应中心点)
	public float    mLastDegrees;//最终角度
	public int      mColor;//颜色
	public boolean  mChangeColor;
	public int      mAlpha = 255;//透明度
	
	public Point 	mPointC = new Point();//当前位移
	public Point 	mPointP = new Point();//前一位移:记录在touch down的时候
	
	public float    mScaleC = 1;
	public float    mScaleP;
	
	public static final void clone(Frame srcFrame, Frame dstFrame){
		dstFrame.mRectC.set(srcFrame.mRectC);
		dstFrame.mRectL.set(srcFrame.mRectL);
		
		dstFrame.mDrawableDegreesC 	= srcFrame.mDrawableDegreesC;
		dstFrame.mDrawableDegreesL 	= srcFrame.mDrawableDegreesL;
		dstFrame.mDrawableDegreesP 	= srcFrame.mDrawableDegreesP;
		dstFrame.mDrawable 			= srcFrame.mDrawable;
		
		dstFrame.mMirror 			= srcFrame.mMirror;
//		dstFrame.mCurrentDegrees 	= srcFrame.mCurrentDegrees;
//		dstFrame.mLastDegrees 		= srcFrame.mLastDegrees;
		dstFrame.mColor 			= srcFrame.mColor;
		dstFrame.mChangeColor		= srcFrame.mChangeColor;
		dstFrame.mAlpha 			= srcFrame.mAlpha;
		
		dstFrame.mPointC.set(srcFrame.mPointC);
		dstFrame.mPointP.set(srcFrame.mPointP);

		dstFrame.mScaleC 			= srcFrame.mScaleC;
		dstFrame.mScaleP 			= srcFrame.mScaleP;
	}
	
}
