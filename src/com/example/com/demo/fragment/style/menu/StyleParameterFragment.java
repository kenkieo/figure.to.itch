package com.example.com.demo.fragment.style.menu;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.example.com.demo.R;
import com.example.com.demo.fragment.BaseHandlerFragment;
import com.example.com.demo.interfaces.OnHideParameterAction;
import com.example.com.demo.interfaces.OnParameterChangeListener;
import com.example.com.demo.view.CustomSeekBar;
import com.example.com.demo.view.CustomSeekBar.OnSeekBarChangeListener;

/**
 * 参数
 * @author Administrator
 *
 */
public class StyleParameterFragment extends BaseHandlerFragment implements OnSeekBarChangeListener, OnClickListener{
	
	private CustomSeekBar 	mTimesSeekBar;
	private CustomSeekBar 	mAlphaSeekBar;
	private ImageView	 	mCollapseIcon;
	
	private OnHideParameterAction		mAction;
	private OnParameterChangeListener 	mOnParameterChangeListener;

	@Override
	protected int getLayoutRes() {
		return R.layout.fragment_style_parameter;
	}

	@Override
	protected void initViews(View convertView) {
		mCollapseIcon = (ImageView) convertView.findViewById(R.id.fragment_style_parameter_collapse);
		mTimesSeekBar = (CustomSeekBar) convertView.findViewById(R.id.fragment_style_parameter_times_seek);
		mAlphaSeekBar = (CustomSeekBar) convertView.findViewById(R.id.fragment_style_parameter_alpha_seek);
		
		mTimesSeekBar.setProgress(2);
		mTimesSeekBar.setMax(12);
		
		mAlphaSeekBar.setProgress(100);
		mAlphaSeekBar.setMax(100);
		
		mCollapseIcon.setOnClickListener(this);
		mTimesSeekBar.setOnSeekBarChangeListener(this);
		mAlphaSeekBar.setOnSeekBarChangeListener(this);
	}

	@Override
	public void onProgressChanged(CustomSeekBar seekBar, int progress) {
		if(seekBar.equals(mTimesSeekBar)){
			if(mOnParameterChangeListener != null){
				mOnParameterChangeListener.onTimesChange(progress);
			}
		}else if(seekBar.equals(mAlphaSeekBar)){
			if(mOnParameterChangeListener != null){
				mOnParameterChangeListener.onAlphaChange(progress);
			}
		}
	}
	
	public void setOnParameterChangeListener(OnParameterChangeListener l) {
		this.mOnParameterChangeListener = l;
	}
	
	public void setOnHideParameterAction(OnHideParameterAction action) {
		this.mAction = action;
	}
	
	@Override
	public void onClick(View v) {
		if(mAction != null){
			mAction.onHideParameter();
		}
	}
	
	@Override
	protected void releaseHandlerFragment() {
		mAction = null;
		mOnParameterChangeListener = null;
		if(mTimesSeekBar != null){
			mTimesSeekBar.setOnSeekBarChangeListener(null);
			mTimesSeekBar = null;
		}
		
		if(mAlphaSeekBar != null){
			mAlphaSeekBar.setOnSeekBarChangeListener(null);
			mAlphaSeekBar = null;
		}
		
		if(mCollapseIcon != null){
			mCollapseIcon.setOnClickListener(null);
			mCollapseIcon = null;
		}
		
	}

}

