package com.example.com.demo.widget.mode.action;

import com.example.com.demo.bean.Frame;

public class FrameUtils {
	
	private static final int POINT_DEGREES	= 13;
	private static final int POINT_LOCATION	= 36;

	public static boolean changeDegrees(Frame frame){
		float degrees = frame.mLastDegrees - frame.mCurrentDegrees;
		if(degrees != 0){
			if(Math.abs(degrees) > POINT_DEGREES){
				if(degrees < 0){
					degrees = - POINT_DEGREES;
				}else{
					degrees = POINT_DEGREES;
				}
			}
			frame.mCurrentDegrees = frame.mCurrentDegrees + degrees;
			return true;
		}
		return false;
	}
	
	public static boolean changeLocation(Frame frame){
		float padding = frame.mRectL.top - frame.mRectC.top;
		if(padding != 0){
			if(Math.abs(padding) > POINT_LOCATION){
				if(padding < 0){
					padding = -POINT_LOCATION;
				}else{
					padding = POINT_LOCATION;
				}
			}
			frame.mRectC.offset(0, padding);
			return true;
		}
		return false;
	}
	
}
