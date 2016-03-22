package com.example.com.demo.fragment.mode;

import android.app.ProgressDialog;

import com.example.com.demo.fragment.BaseHandlerFragment;
import com.example.com.demo.interfaces.OnParameterChangeListener;
import com.example.com.demo.interfaces.OnResourceSelectAction;
import com.example.com.demo.widget.mode.ModeFrame.OnModeFrameAction;

public abstract class StyleContentFrament extends BaseHandlerFragment implements OnResourceSelectAction, OnParameterChangeListener, OnModeFrameAction{

	private OnModeFrameAction mOnModeFrameAction;
	private ProgressDialog    mProgressDialog;
	
	public StyleContentFrament(){
		
	}
	
	public void setOnModeFrameAction(OnModeFrameAction action) {
		this.mOnModeFrameAction = action;
	}
	
	@Override
	public void showStyleResourceFragment() {
		if(mOnModeFrameAction != null){
			mOnModeFrameAction.showStyleResourceFragment();
		}
	}
	
	public abstract void setIsLock(boolean isLock);
	
	public abstract int getSize();
	
	public abstract int getMax();
	
	public abstract void cutScreenShot();
	
	public void showProgressDialog(){
		dismissProgressDialog();
		mProgressDialog = new ProgressDialog(mParent);
		mProgressDialog.setMessage("正在保存中,请稍后...");
		mProgressDialog.setCanceledOnTouchOutside(false);
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
	}
	
	public void dismissProgressDialog(){
		if(mProgressDialog != null){
			mProgressDialog.dismiss();
			mProgressDialog = null;
		}
	}
	
	@Override
	protected void releaseHandlerFragment() {
		mOnModeFrameAction = null;
		dismissProgressDialog();
	}
	
	
}
