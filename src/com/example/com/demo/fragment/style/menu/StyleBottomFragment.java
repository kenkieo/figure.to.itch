package com.example.com.demo.fragment.style.menu;

import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.example.com.demo.R;
import com.example.com.demo.fragment.BaseHandlerFragment;
import com.example.com.demo.fragment.style.menu.StyleModeFragment.Mode;
import com.example.com.demo.fragment.style.menu.StyleModeFragment.OnChoiceModeAction;
import com.example.com.demo.interfaces.OnCustomColorAction;
import com.example.com.demo.interfaces.OnHideParameterAction;
import com.example.com.demo.interfaces.OnParameterChangeListener;
import com.example.com.demo.interfaces.OnResourceSelectAction;
import com.example.com.demo.widget.StyleMenuLayout;
import com.example.com.demo.widget.StyleMenuLayout.OnStyleMenuClickAction;

public class StyleBottomFragment extends BaseHandlerFragment implements OnStyleMenuClickAction, OnChoiceModeAction, OnResourceSelectAction, OnParameterChangeListener, OnHideParameterAction, OnCustomColorAction{

	private StyleMenuLayout mStyleMenuLayout;
	
	private StyleModeFragment 			mStyleModeFragment;
	private StyleResourceFragment 		mStyleResourceFragment;
	private StyleParameterFragment		mStyleParameterFragment;
	private StyleParameterColorFragment mParameterColorFragment;
	
	private OnChoiceModeAction			mChoiceModeAction;
	private OnResourceSelectAction		mOnResourceSelectAction;
	private OnParameterChangeListener 	mOnParameterChangeListener;
	
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
		
		mStyleParameterFragment = new StyleParameterFragment();
		mStyleParameterFragment.setOnParameterChangeListener(this);
		mStyleParameterFragment.setOnHideParameterAction(this);
		mStyleParameterFragment.setOnCustomColorAction(this);
		ft.add(R.id.fragment_style_bottom_layout, mStyleParameterFragment);
		ft.hide(mStyleParameterFragment);
		
		mParameterColorFragment = new StyleParameterColorFragment();
		mParameterColorFragment.setOnParameterChangeListener(this);
		mParameterColorFragment.setOnCustomColorAction(this);
		ft.add(R.id.fragment_style_bottom_layout, mParameterColorFragment);
		ft.hide(mParameterColorFragment);
		ft.commit();	
	}
	
	@Override
	public void onMenuItemClick(int position) {
		switch (position) {
		case StyleMenuLayout.MENU_MODE:
			showStyleResourceFragment(false);
			showStyleParameterFragment(false);
			break;
		case StyleMenuLayout.MENU_RESOURCE:
			showStyleResourceFragment(true);
			break;
		case StyleMenuLayout.MENU_PARAMETER:
			showStyleParameterFragment(true);
			break;
		}
	}
	
	public void showStyleResourceFragment(boolean shown){
		FragmentTransaction ft = getChildFragmentManager().beginTransaction();
		if(shown && mStyleResourceFragment.isHidden()){
			ft.setCustomAnimations(R.anim.translate_from_bottom_in, 0);
			ft.show(mStyleResourceFragment);
		}else if(!mStyleResourceFragment.isHidden() && !shown){
			ft.setCustomAnimations(0, R.anim.translate_from_bottom_out);
			ft.hide(mStyleResourceFragment);
			selectedMenuMode();
		}
		ft.commit();
	}
	
	private void showStyleParameterFragment(boolean shown){
		FragmentTransaction ft = getChildFragmentManager().beginTransaction();
		if(shown && mStyleParameterFragment.isHidden()){
			ft.setCustomAnimations(R.anim.translate_from_bottom_in, 0);
			ft.show(mStyleParameterFragment);
		}else if(!mStyleParameterFragment.isHidden() && !shown){
			ft.setCustomAnimations(0, R.anim.translate_from_bottom_out);
			ft.hide(mStyleParameterFragment);
			selectItemView();
		}
		ft.commit();
	}
	
	private void selectItemView(){
		if(mStyleResourceFragment != null && !mStyleResourceFragment.isHidden()){
			mStyleMenuLayout.setItemSelected(mStyleMenuLayout.getMenuResource());
		}else{
			selectedMenuMode();
		}
	}
	
	private void selectedMenuMode(){
		mStyleMenuLayout.setItemSelected(mStyleMenuLayout.getMenuMode());
	}
	
	@Override
	public void showParameterColorFragment(boolean shown){
		FragmentTransaction ft = getChildFragmentManager().beginTransaction();
		if(shown && mParameterColorFragment.isHidden()){
			ft.setCustomAnimations(R.anim.translate_from_bottom_in, 0);
			ft.show(mParameterColorFragment);
		}else if(!mParameterColorFragment.isHidden() && !shown){
			ft.setCustomAnimations(0, R.anim.translate_from_bottom_out);
			ft.hide(mParameterColorFragment);
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
	
	@Override
	public void onAlphaChange(int alpha) {
		if(mOnParameterChangeListener != null){
			mOnParameterChangeListener.onAlphaChange(alpha);
		}
	}
	
	@Override
	public void onColorChange(int color) {
		if(mOnParameterChangeListener != null){
			mOnParameterChangeListener.onColorChange(color);
		}
	}
	
	@Override
	public void onTimesChange(int times) {
		if(mOnParameterChangeListener != null){
			mOnParameterChangeListener.onTimesChange(times);
		}
	}
	
	public void setOnChoiceModeAction(OnChoiceModeAction action) {
		this.mChoiceModeAction = action;
	}
	
	public void setOnResourceSelectAction(OnResourceSelectAction action) {
		this.mOnResourceSelectAction = action;
	}
	
	public void setOnParameterChangeListener(OnParameterChangeListener l) {
		this.mOnParameterChangeListener = l;
	}
	
	@Override
	public void onHideParameter() {
		showStyleParameterFragment(false);
	}
	
	public void setTimesSeekBarProgress(int progress, int max){
		if(mStyleParameterFragment != null){
			mStyleParameterFragment.setTimesSeekBarProgress(progress, max);
		}
	}
	
	@Override
	public boolean onBackPressed() {
		if(mParameterColorFragment != null && !mParameterColorFragment.isHidden()){
			showParameterColorFragment(false);
			return true;
		}else if(mStyleParameterFragment != null && !mStyleParameterFragment.isHidden()){
			showStyleParameterFragment(false);
			return true;
		}else if(mStyleResourceFragment != null && !mStyleResourceFragment.isHidden()){
			showStyleResourceFragment(false);
			return true;
		} 
		return super.onBackPressed();
	}
	
	@Override
	protected void releaseHandlerFragment() {
		mChoiceModeAction = null;
		mOnResourceSelectAction = null;
		mOnParameterChangeListener = null;
		
		if(mStyleMenuLayout != null){
			mStyleMenuLayout.removeAllViews();
		}
		
		if(mStyleModeFragment != null){
			mStyleModeFragment.setOnChoiceModeAction(null);
			mStyleModeFragment = null;
		}
		
		if(mStyleResourceFragment != null){
			mStyleResourceFragment.setOnResourceSelectAction(null);
			mStyleResourceFragment = null;
		}
		
		if(mStyleParameterFragment != null){
			mStyleParameterFragment.setOnParameterChangeListener(null);
			mStyleParameterFragment.setOnHideParameterAction(null);
			mStyleParameterFragment.setOnCustomColorAction(null);
			mStyleParameterFragment = null;
		}
		
		if(mParameterColorFragment != null){
			mParameterColorFragment.setOnParameterChangeListener(null);
			mParameterColorFragment.setOnCustomColorAction(null);
			mParameterColorFragment = null;
		}
	}

}
