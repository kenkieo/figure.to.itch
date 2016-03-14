package com.example.com.demo.widget.mode;

import android.graphics.Rect;

public interface IModeFrameAction {

	void layout(int left, int top, int right, int bottom);
	
	void setCurrentDegrees(float degrees);
	
	void setFinalRect(Rect rect);
	
	Rect getFinalRect();
	
	float getRelativeParentDegrees();
	
}
