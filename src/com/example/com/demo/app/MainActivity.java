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

public class MainActivity extends BaseTitleFragmentActivity implements OnChoiceModeAction, OnResourceSelectAction, OnNetBitmapSelectAction, OnParameterChangeListener {

	private StyleBottomFragment mStyleBottomFragment;
	
	private StyleFirstFragment  mStyleFirstFragment;
	private StyleSecondFragment mStyleSecondFragment;
	private StyleThreeFragment	mStyleThreeFragment;
	
	private ActionbarMenuImageView mLockIcon;

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
		mLockIcon.setId(R.id.action_menu_lock);
		mLockIcon.setImageResource(R.drawable.icon_homepage_properties_group_01);
		addMenuItem(mLockIcon);
	}
	
	@Override
	public void onMenuAction(int menuId) {
		super.onMenuAction(menuId);
		mLockIcon.setSelected(!mLockIcon.isSelected());
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
		ft.add(R.id.StyleFrameLayout, mStyleFirstFragment);
		
		mStyleSecondFragment 	= new StyleSecondFragment();
		ft.add(R.id.StyleFrameLayout, mStyleSecondFragment);
		ft.hide(mStyleSecondFragment);
		
		mStyleThreeFragment 	= new StyleThreeFragment();
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
	
	@Override
	protected void release_BaseTitleFragmentActivity() {
		OnNetBitmapSelectObserver.getInst().removeOnNetBitmapSelectAction(this);
	}

}
