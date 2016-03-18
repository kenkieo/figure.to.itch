package com.example.com.demo.fragment.style.menu;

import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.example.com.demo.R;
import com.example.com.demo.fragment.BaseHandlerFragment;
import com.example.com.demo.fragment.style.menu.StyleModeFragment.Mode;
import com.example.com.demo.fragment.style.menu.StyleModeFragment.OnChoiceModeAction;
import com.example.com.demo.interfaces.OnResourceSelectAction;
import com.example.com.demo.widget.StyleMenuLayout;
import com.example.com.demo.widget.StyleMenuLayout.OnStyleMenuClickAction;

public class StyleBottomFragment extends BaseHandlerFragment implements OnStyleMenuClickAction, OnChoiceModeAction, OnResourceSelectAction{

	private StyleMenuLayout mStyleMenuLayout;
	
	private StyleModeFragment 		mStyleModeFragment;
	private StyleResourceFragment 	mStyleResourceFragment;
	
	private OnChoiceModeAction		mChoiceModeAction;
	private OnResourceSelectAction	mOnResourceSelectAction;
	
	@Override
	protected int getLayoutRes() {
		return R.layout.fragment_style_bottom_layout;
	}

	@Override
	protected void initViews(View convertView) {
		mStyleMenuLayout = (StyleMenuLayout) convertView.findViewById(R.id.fragment_style_menu);
		mStyleMenuLayout.setOnStyleMenuClickAction(this);
	}
	
	@Override
	protected void initData() {
		super.initData();
		postDelayed(new Runnable() {
			
			@Override
			public void run() {
				initFragments();		
			}
		}, 100);
	}
	
	private void initFragments(){
		mStyleModeFragment = new StyleModeFragment();
		mStyleModeFragment.setOnChoiceModeAction(this);
		FragmentTransaction ft = getChildFragmentManager().beginTransaction();
		ft.setCustomAnimations(R.anim.translate_from_bottom_in, 0);
		ft.add(R.id.fragment_style_bottom_layout_content, mStyleModeFragment);
		
		mStyleResourceFragment = new StyleResourceFragment();
		mStyleResourceFragment.setOnResourceSelectAction(this);
		ft.add(R.id.fragment_style_bottom_layout_content, mStyleResourceFragment);
		ft.hide(mStyleResourceFragment);
		ft.commit();	
	}
	
	@Override
	public void onMenuItemClick(int position) {
		switch (position) {
		case StyleMenuLayout.MENU_MODE:
			showStyleResourceFragment(false);
			break;
		case StyleMenuLayout.MENU_RESOURCE:
			showStyleResourceFragment(true);
			break;
		case StyleMenuLayout.MENU_PARAMETER:
			break;
		}
	}
	
	private void showStyleResourceFragment(boolean shown){
		FragmentTransaction ft = getChildFragmentManager().beginTransaction();
		if(shown && mStyleResourceFragment.isHidden()){
			ft.setCustomAnimations(R.anim.translate_from_bottom_in, 0);
			ft.show(mStyleResourceFragment);
		}else if(!mStyleResourceFragment.isHidden() && !shown){
			ft.setCustomAnimations(0, R.anim.translate_from_bottom_out);
			ft.hide(mStyleResourceFragment);
		}
		ft.commit();
	}
	
	@Override
	public void onMenuChoiced(Mode mode) {
		if(mChoiceModeAction != null){
			mChoiceModeAction.onMenuChoiced(mode);
		}
	}
	
	@Override
	public void onResourceSelect(Drawable drawable) {
		if(mOnResourceSelectAction != null){
			mOnResourceSelectAction.onResourceSelect(drawable);
		}
	}
	
	public void setOnChoiceModeAction(OnChoiceModeAction action) {
		this.mChoiceModeAction = action;
	}
	
	public void setOnResourceSelectAction(OnResourceSelectAction action) {
		this.mOnResourceSelectAction = action;
	}
	
	@Override
	protected void releaseHandlerFragment() {
		
	}

}
