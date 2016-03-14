package com.example.com.demo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.com.demo.R;

public class CategoryItemView extends TextView{
	
	private Paint mPaint;
	private RectF mRectF;

	public CategoryItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		mPaint = new Paint();
		mPaint.setColor(getResources().getColor(R.color.common_yellow));
		mPaint.setAntiAlias(true);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		if(isPressed() || isFocused() || isSelected()){
			if(mPaint != null){
				float radius = getHeight() / 2;
				if(mRectF == null){
					float left 		= -radius;
					float top  		= 0;
					float right 	= getWidth();
					float bottom 	= getHeight();
					mRectF = new RectF(left, top, right, bottom);
				}
				canvas.drawRoundRect(mRectF, radius, radius, mPaint);
			}
		}
		super.onDraw(canvas);
	}

}
