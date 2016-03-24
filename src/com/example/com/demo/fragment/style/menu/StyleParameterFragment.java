package com.example.com.demo.fragment.style.menu;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.com.demo.R;
import com.example.com.demo.fragment.BaseHandlerFragment;
import com.example.com.demo.interfaces.OnCustomColorAction;
import com.example.com.demo.interfaces.OnHideParameterAction;
import com.example.com.demo.interfaces.OnParameterChangeListener;
import com.example.com.demo.utils.Constants;
import com.example.com.demo.utils.LayoutInflaterUtils;
import com.example.com.demo.view.CustomSeekBar;
import com.example.com.demo.view.CustomSeekBar.OnSeekBarChangeListener;
import com.example.com.demo.view.ParameterColorIcon;

/**
 * 参数
 * @author Administrator
 *
 */
public class StyleParameterFragment extends BaseHandlerFragment implements OnSeekBarChangeListener, OnClickListener{
	
	private CustomSeekBar 	mTimesSeekBar;
	private CustomSeekBar 	mAlphaSeekBar;
	private ImageView	 	mCollapseIcon;
	private ViewGroup		mColorLayout;
	private View			mTmpView;

	private OnCustomColorAction			mCustomColorAction;
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
		mColorLayout  = (ViewGroup) convertView.findViewById(R.id.fragment_style_parameter_color_layout);
		
		mTimesSeekBar.setProgress(Constants.INIT_NUM_MODE_1);
		mTimesSeekBar.setMin(Constants.MIN_NUM);
		mTimesSeekBar.setMax(Constants.MAX_NUM);
		
		mAlphaSeekBar.setProgress(100);
		mAlphaSeekBar.setMax(100);
		
		mCollapseIcon.setOnClickListener(this);
		mTimesSeekBar.setOnSeekBarChangeListener(this);
		mAlphaSeekBar.setOnSeekBarChangeListener(this);
		
		addItemFirst();
		addItemViews();
	}
	
	private void addItemFirst(){
		TextView addView = (TextView) LayoutInflaterUtils.inflateView(mParent, R.layout.layout_color_add);
		addView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mCustomColorAction != null){
					mCustomColorAction.showParameterColorFragment(true);
				}
			}
		});
		addView.setTag(0);
		mColorLayout.addView(addView);
	}
	
	private void addItemViews(){
		for (final int color : Constants.StyleColors) {
			View convertView = LayoutInflaterUtils.inflateView(mParent, R.layout.layout_color_item);
			ParameterColorIcon colorItem = (ParameterColorIcon) convertView.findViewById(R.id.layout_color_item_icon);
			colorItem.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					setItemSelected(v);
					if(mOnParameterChangeListener != null){
						mOnParameterChangeListener.onColorChange(color);
					}
				}
			});
			colorItem.setColor(color);
			mColorLayout.addView(convertView);
		}
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
	
	public void setTimesSeekBarProgress(int progress, int max){
		Log.i("TAG", "progress:" + progress);
		Log.i("TAG", "max:" + max);
		if(mTimesSeekBar != null){
			mTimesSeekBar.setMax(max);
			mTimesSeekBar.setProgress(progress);
		}
	}
	
	public void setOnParameterChangeListener(OnParameterChangeListener l) {
		this.mOnParameterChangeListener = l;
	}
	
	public void setOnHideParameterAction(OnHideParameterAction action) {
		this.mAction = action;
	}
	
	public void setOnCustomColorAction(OnCustomColorAction action) {
		this.mCustomColorAction = action;
	}
	
	@Override
	public void onClick(View v) {
		if(mAction != null){
			mAction.onHideParameter();
		}
	}
	
	private void setItemSelected(View v){
		if(mTmpView != null){
			mTmpView.setSelected(false);
		}
		mTmpView = v;
		if(mTmpView != null){
			mTmpView.setSelected(true);
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
		
		if(mColorLayout != null){
			mColorLayout.removeAllViews();
			mColorLayout = null;
		}
		mTmpView = null;
		mCustomColorAction = null;
	}

}

