package com.example.com.demo.widget.actionbar.menu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.example.com.demo.observer.ExitActivityObserver;
import com.example.com.demo.observer.ExitActivityObserver.ExitActivityObserverAction;
import com.example.com.demo.widget.actionbar.interfaces.OnActionBarMenuAction;

/**
 * 右侧按钮菜单
 * @author 蔡国辉
 *
 */
public class ActionbarMenuLayout extends LinearLayout implements ExitActivityObserverAction {
	
	public OnActionBarMenuAction mAction;
	
	public ActionbarMenuLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		ExitActivityObserver.getInst().addExitActivityObserverAction(context, this);
	}

	public void addMenuItem(ActionbarMenuItem... items){
		for (final ActionbarMenuItem item : items) {
			View view = item.getMenuItemView();
			view.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(mAction != null){
						mAction.onMenuAction(item.getMenuItemId());
					}
				}
			});
			addView(view, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
		}
	}
	
	public void setOnActionBarMenuAction(OnActionBarMenuAction action) {
		this.mAction = action;
	}

	@Override
	public void onActivityDestory() {
		mAction = null;
	}
}
