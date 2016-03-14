package com.example.com.demo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.com.demo.R;

@SuppressLint("NewApi")
public class RatioImageView extends ImageView {
	
	protected int mRatio_x = -1;
	protected int mRatio_y = -1;
	
	public RatioImageView(Context context) {
		super(context);
	}

	public RatioImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioImageView);
		mRatio_x = typedArray.getInt(R.styleable.RatioImageView_RatioImageView_X, -1);
		mRatio_y = typedArray.getInt(R.styleable.RatioImageView_RatioImageView_Y, -1);
		typedArray.recycle();
	}
	
	public void setImageBitmap(Bitmap bm, String url) {
		try {
			super.setImageBitmap(bm);
		} catch (Exception e) {
		}
	}
	
	@Override
	public void setImageDrawable(Drawable drawable) {
		try {
			super.setImageDrawable(drawable);
		} catch (Exception e) {
		}
	}
	
	@Override
	public void setImageURI(Uri uri) {
		try {
			super.setImageURI(uri);
		} catch (Exception e) {
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);

		if(mRatio_y > 0 && mRatio_x > 0){
			if(widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY){
				if(widthSize > heightSize){
					widthMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize * mRatio_x / mRatio_y, MeasureSpec.EXACTLY);
				}else{
					heightMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize * mRatio_y / mRatio_x, MeasureSpec.EXACTLY);
				}
			}else if (widthMode == MeasureSpec.EXACTLY) {
				heightMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize * mRatio_y / mRatio_x, MeasureSpec.EXACTLY);
			}else if (heightMode == MeasureSpec.EXACTLY) {
				widthMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize * mRatio_x / mRatio_y, MeasureSpec.EXACTLY);
			}
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		try {
			super.onDraw(canvas);
		} catch (Exception e) {
		}
	}
//	@Override
//	public void onActivityDestory() {
//		super.onDetachedFromWindow();
//		recycle();
//	}
//	
//	@Override
//	public void setImageBitmap(Bitmap bm) {
//		if(bm.getWidth() > getWidth() && getWidth() > 0){
//			Bitmap bm2 = Bitmap.createScaledBitmap(bm, getWidth(), getHeight(), true);
//			bm.recycle();
//			super.setImageBitmap(bm2);
//		}else{
//			super.setImageBitmap(bm);
//		}
//		super.setImageBitmap(bm);
//	}
//	
//	@Override
//	public void setImageDrawable(Drawable drawable) {
//		recycle();
//		super.setImageDrawable(drawable);
//	}
//	
//	private void recycle(){
//		if(getDrawable() != null && getDrawable() instanceof BitmapDrawable){
//			BitmapDrawable bitmapDrawable = (BitmapDrawable) getDrawable();
//			bitmapDrawable.getBitmap().recycle();
//		}
//	}
}
