package com.example.com.demo.basefragmentactivity;

import com.example.com.demo.R;
import com.example.com.demo.utils.LayoutInflaterUtils;
import com.example.com.demo.widget.actionbar.ActionbarBasicLayout.ActionbarBasicAction;
import com.example.com.demo.widget.actionbar.ActionbarNormalLayout;
import com.example.com.demo.widget.actionbar.menu.ActionbarMenuItem;



public abstract class BaseTitleFragmentActivity extends BaseHandlerFragmentActivity implements ActionbarBasicAction{
	
	private ActionbarNormalLayout mActionbarBasic;
	
	
	@Override
	protected final void initViews_BaseFragmentActivity() {
		initViews_BaseTitleFragmentActivity();
		mActionbarBasic = (ActionbarNormalLayout) LayoutInflaterUtils.inflateView(mContext, R.layout.layout_actionbar_normal);
		if(mActionbarBasic != null){
			mActionbarBasic.attachToActivity(this);
			mActionbarBasic.setActionbarBasicAction(this);
			addActionbarMenus();
		}
	}
	
	protected abstract void initViews_BaseTitleFragmentActivity();
	
	public void addActionbarMenus() {
		
	}
	
	@Override
	public void setTitle(int titleId) {
		setTitle(getString(titleId));
	}
	
	@Override
	public void setTitle(CharSequence title) {
		if(mActionbarBasic != null){
			mActionbarBasic.setTitle(title);
		}
	}
	
	public void addMenuItem(ActionbarMenuItem... items){
		if(mActionbarBasic != null){
			mActionbarBasic.addMenuItem(items);
		}
	}
	
	public void setActionBarLayoutVisibility(int visibility){
		if(mActionbarBasic != null){
			mActionbarBasic.setVisibility(visibility);
		}
	}
	
	@Override
	public void onMenuAction(int menuId) {
		
	}

	@Override
	protected final void release_BaseHandlerFragmentActivity() {
		release_BaseTitleFragmentActivity();
		if(mActionbarBasic != null){
			mActionbarBasic.setActionbarBasicAction(null);
			mActionbarBasic = null;
		}
	}
	
	protected abstract void release_BaseTitleFragmentActivity();

}
