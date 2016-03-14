package com.example.com.demo.widget.actionbar.menu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.example.com.demo.utils.DisplayUtils;

/**
 * 文本按钮
 *
 */
public class ActionbarMenuTextView extends TextView implements ActionbarMenuItem{
	
	private int mMenuId;
	
	public ActionbarMenuTextView(Context context, AttributeSet attrs) {
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
	
	@Override
	public void setCompoundDrawablesWithIntrinsicBounds(int left, int top, int right, int bottom) {
		super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
		setCompoundDrawablePadding(DisplayUtils.dip2px(getContext(), 7.5f));
	}

}
