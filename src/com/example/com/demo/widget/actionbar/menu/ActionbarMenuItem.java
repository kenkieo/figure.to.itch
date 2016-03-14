package com.example.com.demo.widget.actionbar.menu;

import android.view.View;

public interface ActionbarMenuItem {

	View getMenuItemView();
	
	int  getMenuItemId();
	
	void setMenuItemId(int id);
}
