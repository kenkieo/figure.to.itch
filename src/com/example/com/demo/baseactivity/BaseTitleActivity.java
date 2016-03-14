package com.example.com.demo.baseactivity;

import com.example.com.demo.R;
import com.example.com.demo.utils.LayoutInflaterUtils;
import com.example.com.demo.widget.actionbar.ActionbarBasicLayout.ActionbarBasicAction;
import com.example.com.demo.widget.actionbar.ActionbarNormalLayout;
import com.example.com.demo.widget.actionbar.menu.ActionbarMenuItem;


/**
 * 标题
 * @author Administrator
 *
 */
public abstract class BaseTitleActivity extends BaseHandlerActivity implements ActionbarBasicAction{
	
	private ActionbarNormalLayout mActionbarBasic;
	
	protected final void initViews_BaseSwipeToCloseActivity() {
		initViews_BaseTitleActivity();
		mActionbarBasic = (ActionbarNormalLayout) LayoutInflaterUtils.inflateView(mContext, R.layout.layout_actionbar_normal);
		if(mActionbarBasic != null){
			mActionbarBasic.attachToActivity(this);
			mActionbarBasic.setActionbarBasicAction(this);
			addActionbarMenus();
		}
	}
	
	protected abstract void initViews_BaseTitleActivity();
	
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
	
	public void removeAllMenuItem(){
		if(mActionbarBasic != null){
			mActionbarBasic.removeAllMenuItem();
		}
	}
	
	public void addActionbarMenus(){
		
	}
	
	@Override
	public void onMenuAction(int menuId) {
		
	}
	
	@Override
	protected void release_BaseHandlerActivity() {
		release_BaseTitleActivity();
		if(mActionbarBasic != null){
			mActionbarBasic.setActionbarBasicAction(null);
			mActionbarBasic = null;
		}
	}
	
	protected abstract void release_BaseTitleActivity();
	
}
