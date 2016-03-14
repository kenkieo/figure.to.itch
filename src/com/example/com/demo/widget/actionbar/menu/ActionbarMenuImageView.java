package com.example.com.demo.widget.actionbar.menu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class ActionbarMenuImageView extends ImageView implements ActionbarMenuItem{
	
	private int mMenuId;
	
	public ActionbarMenuImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public View getMenuItemView() {
		return this;
	}

	@Override
	public int getMenuItemId() {
		return mMenuId;
	}
	
	@Override
	public void setMenuItemId(int id) {
		mMenuId = id;
	}

}
