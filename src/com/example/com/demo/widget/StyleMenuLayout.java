package com.example.com.demo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.com.demo.R;

public class StyleMenuLayout extends LinearLayout implements View.OnClickListener{
	
	public static final int MENU_MODE 		= 0;
	public static final int MENU_RESOURCE 	= 1;
	public static final int MENU_PARAMETER 	= 2;
	
	private TextView mMenuMode;
	private TextView mMenuResource;
	private TextView mMenuParameter;
	
	private View 	mTmpView;
	
	private OnStyleMenuClickAction mAction;

	public StyleMenuLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		initStyleMenuLayout(this);
	}

	private void initStyleMenuLayout(View convertView) {
		mMenuMode 		= (TextView) convertView.findViewById(R.id.fragment_sytle_menu_mode);
		mMenuResource 	= (TextView) convertView.findViewById(R.id.fragment_sytle_menu_resource);
		mMenuParameter 	= (TextView) convertView.findViewById(R.id.fragment_sytle_menu_parameter);
		
		if(mMenuMode != null){
			mMenuMode.setOnClickListener(this);
		}
		
		if(mMenuResource != null){
			mMenuResource.setOnClickListener(this);
		}
		
		if(mMenuParameter != null){
			mMenuParameter.setOnClickListener(this);
		}
		
		setItemSelected(mMenuMode);
	}

	@Override
	public void onClick(View v) {
		int position = MENU_MODE;
		switch (v.getId()) {
		case R.id.fragment_sytle_menu_mode:
			position = MENU_MODE;
			break;
		case R.id.fragment_sytle_menu_resource:
			position = MENU_RESOURCE;
			break;
		case R.id.fragment_sytle_menu_parameter:
			position = MENU_PARAMETER;
			break;
		}
		setItemSelected(v);
		
		if(mAction != null){
			mAction.onMenuItemClick(position);
		}
	}
	
	public void setItemSelected(View tmpView){
		if(mTmpView != null){
			mTmpView.setSelected(false);
		}
		mTmpView = tmpView;
		if(mTmpView != null){
			mTmpView.setSelected(true);
		}
	}
	
	public TextView getMenuResource() {
		return mMenuResource;
	}
	
	public TextView getMenuMode() {
		return mMenuMode;
	}
	
	public void setOnStyleMenuClickAction(OnStyleMenuClickAction action) {
		this.mAction = action;
	}
	
	public interface OnStyleMenuClickAction{
		void onMenuItemClick(int position);
	}

}
