package com.example.com.demo.app;

import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentTransaction;

import com.example.com.demo.R;
import com.example.com.demo.basefragmentactivity.BaseTitleFragmentActivity;
import com.example.com.demo.fragment.mode.StyleFirstFragment;
import com.example.com.demo.fragment.mode.StyleSecondFragment;
import com.example.com.demo.fragment.mode.StyleThreeFragment;
import com.example.com.demo.fragment.style.menu.StyleBottomFragment;
import com.example.com.demo.fragment.style.menu.StyleModeFragment.Mode;
import com.example.com.demo.fragment.style.menu.StyleModeFragment.OnChoiceModeAction;
import com.example.com.demo.interfaces.OnParameterChangeListener;
import com.example.com.demo.interfaces.OnResourceSelectAction;
import com.example.com.demo.observers.OnNetBitmapSelectObserver;
import com.example.com.demo.observers.OnNetBitmapSelectObserver.OnNetBitmapSelectAction;
import com.example.com.demo.utils.LayoutInflaterUtils;
import com.example.com.demo.widget.actionbar.menu.ActionbarMenuImageView;
import com.example.com.demo.widget.mode.ModeFrame.OnModeFrameAction;

public class MainActivity extends BaseTitleFragmentActivity implements OnChoiceModeAction, OnResourceSelectAction, OnNetBitmapSelectAction, OnParameterChangeListener, OnModeFrameAction{

	private StyleBottomFragment mStyleBottomFragment;
	
	private StyleFirstFragment  mStyleFirstFragment;
	private StyleSecondFragment mStyleSecondFragment;
	private StyleThreeFragment	mStyleThreeFragment;
	
	private ActionbarMenuImageView mLockIcon;
	private ActionbarMenuImageView mCutIcon;

	@Override
	protected int getLayoutRes() {
		return R.layout.activity_main;
	}

	@Override
	protected void initViews_BaseTitleFragmentActivity() {
	}
	
	@Override
	public void addActionbarMenus() {
		super.addActionbarMenus();
		mLockIcon = (ActionbarMenuImageView) LayoutInflaterUtils.inflateView(mContext, R.layout.layout_actionbar_menu_icon);
		mLockIcon.setMenuItemId(R.id.action_menu_lock);
		mLockIcon.setImageResource(R.drawable.icon_homepage_properties_group_selector);
		addMenuItem(mLockIcon);
		
		mCutIcon = (ActionbarMenuImageView) LayoutInflaterUtils.inflateView(mContext, R.layout.layout_actionbar_menu_icon);
		mCutIcon.setMenuItemId(R.id.action_menu_cut);
		mCutIcon.setImageResource(R.drawable.icon_homepage_properties_cut_selector);
		addMenuItem(mCutIcon);
	}
	
	@Override
	public void onMenuAction(int menuId) {
		super.onMenuAction(menuId);
		if(R.id.action_menu_lock == menuId){
			mLockIcon.setSelected(!mLockIcon.isSelected());
			if(mStyleFirstFragment != null && !mStyleFirstFragment.isHidden()){
				mStyleFirstFragment.setIsLock(mLockIcon.isSelected());
			}
		}else if(R.id.action_menu_cut == menuId){
			if(mStyleFirstFragment != null && !mStyleFirstFragment.isHidden()){
				mStyleFirstFragment.cutScreenShot();
			}
		}
	}

	@Override
	protected void addFragments() {
		FragmentTransaction ft = mFragmentManager.beginTransaction();

		mStyleBottomFragment = new StyleBottomFragment();
		mStyleBottomFragment.setOnChoiceModeAction(this);
		mStyleBottomFragment.setOnResourceSelectAction(this);
		mStyleBottomFragment.setOnParameterChangeListener(this);
		ft.add(R.id.layout_framelayout, mStyleBottomFragment);
		
		mStyleFirstFragment 	= new StyleFirstFragment();
		mStyleFirstFragment.setOnModeFrameAction(this);
		ft.add(R.id.StyleFrameLayout, mStyleFirstFragment);
		
		mStyleSecondFragment 	= new StyleSecondFragment();
		mStyleSecondFragment.setOnModeFrameAction(this);
		ft.add(R.id.StyleFrameLayout, mStyleSecondFragment);
		ft.hide(mStyleSecondFragment);
		
		mStyleThreeFragment 	= new StyleThreeFragment();
		mStyleThreeFragment.setOnModeFrameAction(this);
		ft.add(R.id.StyleFrameLayout, mStyleThreeFragment);
		ft.hide(mStyleThreeFragment);
		ft.commit();
	}

	@Override
	protected void setSelection(int idx, boolean show) {
		
	}
	
	@Override
	protected void initData() {
		setTitle(R.string.app_name);
		OnNetBitmapSelectObserver.getInst().addOnNetBitmapSelectAction(this);
	}
	
	@Override
	public void onMenuChoiced(Mode mode) {
		FragmentTransaction ft = mFragmentManager.beginTransaction();
		switch (mode) {
		case MODE_1:
			default:
			ft.show(mStyleFirstFragment);
			ft.hide(mStyleSecondFragment);
			ft.hide(mStyleThreeFragment);
			setTimesSeekBarProgress(mStyleFirstFragment.getSize());
			break;
		case MODE_2:
			ft.hide(mStyleFirstFragment);
			ft.show(mStyleSecondFragment);
			ft.hide(mStyleThreeFragment);
			break;
		case MODE_3:
			ft.hide(mStyleFirstFragment);
			ft.hide(mStyleSecondFragment);
			ft.show(mStyleThreeFragment);
			break;
		}
		ft.commit();
	}
	
	@Override
	public void onResourceSelect(Drawable drawable) {
		if(mStyleFirstFragment != null && !mStyleFirstFragment.isHidden()){
			mStyleFirstFragment.onResourceSelect(drawable);
		}
	}
	
	@Override
	public void onAlphaChange(int alpha) {
		if(mStyleFirstFragment != null && !mStyleFirstFragment.isHidden()){
			mStyleFirstFragment.onAlphaChange(alpha);
		}
	}
	
	@Override
	public void onTimesChange(int times) {
		if(mStyleFirstFragment != null && !mStyleFirstFragment.isHidden()){
			mStyleFirstFragment.onTimesChange(times);
		}
	}
	
	@Override
	public void onColorChange(int color) {
		if(mStyleFirstFragment != null && !mStyleFirstFragment.isHidden()){
			mStyleFirstFragment.onColorChange(color);
		}
	}
	
	public void setTimesSeekBarProgress(int progress){
		if(mStyleBottomFragment != null){
			mStyleBottomFragment.setTimesSeekBarProgress(progress);
		}
	}
	
	@Override
	public void showStyleResourceFragment() {
		if(mStyleBottomFragment != null){
			mStyleBottomFragment.showStyleResourceFragment(true);
		}
	}
	
	@Override
	public void onLayoutChange() {
		
	}
	
	@Override
	public void onBackPressed() {
		if(mStyleBottomFragment != null && mStyleBottomFragment.onBackPressed()){
			return;
		}
		super.onBackPressed();
	}
	
	@Override
	protected void release_BaseTitleFragmentActivity() {
		OnNetBitmapSelectObserver.getInst().removeOnNetBitmapSelectAction(this);

		if(mStyleBottomFragment != null){
			mStyleBottomFragment.setOnChoiceModeAction(null);
			mStyleBottomFragment.setOnResourceSelectAction(null);
			mStyleBottomFragment.setOnParameterChangeListener(null);
			mStyleBottomFragment = null;
		}
		
		if(mStyleFirstFragment != null){
			mStyleFirstFragment.setOnModeFrameAction(null);
			mStyleFirstFragment = null;
		}
		
		if(mStyleSecondFragment != null){
			mStyleSecondFragment.setOnModeFrameAction(null);
			mStyleSecondFragment = null;
		}
		
		if(mStyleThreeFragment != null){
			mStyleThreeFragment.setOnModeFrameAction(null);
			mStyleThreeFragment = null;
		}

		if(mLockIcon != null){
			mLockIcon.setOnClickListener(null);
			mLockIcon = null;
		}
		
		if(mCutIcon != null){
			mCutIcon.setOnClickListener(null);
			mCutIcon = null;
		}
		
	}

}
