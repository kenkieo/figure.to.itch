package com.example.com.demo.observers;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;

public class OnNetBitmapSelectObserver {

	private static OnNetBitmapSelectObserver mInst = null;
	private static Object mLock = new Object();

	private List<OnNetBitmapSelectAction> mActions;
	
	private OnNetBitmapSelectObserver(){
		mActions = new ArrayList<OnNetBitmapSelectAction>();
	}
	
	public static OnNetBitmapSelectObserver getInst(){
		if(mInst == null){
			synchronized(mLock){
				if(mInst == null){
					mInst = new OnNetBitmapSelectObserver();
				}
			}
		}
		return mInst;
	}
	
	public void addOnNetBitmapSelectAction(OnNetBitmapSelectAction action){
		if(!mActions.contains(action)){
			mActions.add(action);
		}
	}
	
	public void removeOnNetBitmapSelectAction(OnNetBitmapSelectAction action){
		if(mActions != null){
			mActions.remove(action);
		}
	}
	
	public void onResourceSelect(Drawable drawable){
		if(mActions != null){
			int size = mActions.size();
			for (int i = 0; i < size; i++) {
				try {
					mActions.get(i).onResourceSelect(drawable);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public interface OnNetBitmapSelectAction{
		void onResourceSelect(Drawable drawable);
	}
	
}
