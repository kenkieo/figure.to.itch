package com.example.com.demo.widget.actionbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.com.demo.R;
import com.example.com.demo.widget.actionbar.interfaces.OnActionBarMenuAction;
import com.example.com.demo.widget.actionbar.menu.ActionbarMenuItem;
import com.example.com.demo.widget.actionbar.menu.ActionbarMenuLayout;

/**
 * 标题
 * 
 * @author 蔡国辉
 * 
 */
public class ActionbarNormalLayout extends ActionbarBasicLayout{

	private ViewGroup 	mTitleLayout;
	private ImageView 	mBackIcon;
	private TextView	mTitleTv;
	private ActionbarMenuLayout mMenuLayout;
	
	public ActionbarNormalLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void initActionbarBasic(View view) {
		mTitleLayout = (ViewGroup) view.findViewById(R.id.layout_actionbar_title_layout);
		mBackIcon    = (ImageView) view.findViewById(R.id.layout_actionbar_back);
		if(mBackIcon != null){
			mBackIcon.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(mAction != null){
						mAction.onCloseAction();
					}
				}
			});
		}
		
		mTitleTv = (TextView) view.findViewById(R.id.layout_actionbar_title);
		
		mMenuLayout	= (ActionbarMenuLayout) view.findViewById(R.id.ActionbarMenuLayout);
		if(mMenuLayout != null){
			mMenuLayout.setOnActionBarMenuAction(new OnActionBarMenuAction() {
				
				@Override
				public void onMenuAction(int menuId) {
					if(mAction != null){
						mAction.onMenuAction(menuId);
					}
				}
			});
		}
	}
	
	public void hideBackBtn(){
		if(mBackIcon != null){
			mBackIcon.setVisibility(View.INVISIBLE);
		}
	}
	
	@Override
	protected ViewGroup getTitleLayout() {
		return mTitleLayout;
	}
	
	public void setTitle(CharSequence text){
		if(mTitleTv != null){
			mTitleTv.setText(text);
		}
	}
	
	public void addMenuItem(ActionbarMenuItem... items){
		if(mMenuLayout != null){
			mMenuLayout.addMenuItem(items);
		}
	}
	
	public void removeAllMenuItem(){
		if(mMenuLayout != null){
			mMenuLayout.removeAllViews();
		}
	}


	@Override
	public void onActivityDestory() {
		super.onActivityDestory();
		mAction = null;
		mTitleLayout = null;
		if(mBackIcon != null){
			mBackIcon.setOnClickListener(null);
			mBackIcon = null;
		}
		if(mMenuLayout != null){
			mMenuLayout.setOnActionBarMenuAction(null);
			mMenuLayout = null;
		}
	}
	
}
