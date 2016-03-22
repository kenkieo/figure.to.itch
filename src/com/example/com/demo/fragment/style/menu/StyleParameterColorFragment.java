package com.example.com.demo.fragment.style.menu;

import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.com.demo.R;
import com.example.com.demo.fragment.BaseHandlerFragment;
import com.example.com.demo.interfaces.OnCustomColorAction;
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
public class StyleParameterColorFragment extends BaseHandlerFragment implements OnSeekBarChangeListener, OnClickListener{

	private static int MAX = 255;
	
	private ImageView	 	mCollapseIcon;
	private CustomSeekBar 	mRedSeekBar;
	private CustomSeekBar 	mGreenSeekBar;
	private CustomSeekBar 	mBlueSeekBar;
	private ViewGroup		mColorLayout;
	private ParameterColorIcon	mTmpView;
	
	private OnCustomColorAction			mAction;
	private OnParameterChangeListener 	mOnParameterChangeListener;

	@Override
	protected int getLayoutRes() {
		return R.layout.fragment_style_parameter_color;
	}

	@Override
	protected void initViews(View convertView) {
		mCollapseIcon = (ImageView) convertView.findViewById(R.id.fragment_style_parameter_color_collapse);
		mRedSeekBar   = (CustomSeekBar) convertView.findViewById(R.id.fragment_style_parameter_color_red_seek);
		mGreenSeekBar = (CustomSeekBar) convertView.findViewById(R.id.fragment_style_parameter_color_green_seek);
		mBlueSeekBar  = (CustomSeekBar) convertView.findViewById(R.id.fragment_style_parameter_color_blue_seek);
		mColorLayout  = (ViewGroup) convertView.findViewById(R.id.fragment_style_parameter_color_list_layout);
		
		mRedSeekBar.setMax(MAX);
		mGreenSeekBar.setMax(MAX);
		mBlueSeekBar.setMax(MAX);
		
		mCollapseIcon.setOnClickListener(this);
		mRedSeekBar.setOnSeekBarChangeListener(this);
		mGreenSeekBar.setOnSeekBarChangeListener(this);
		mBlueSeekBar.setOnSeekBarChangeListener(this);
		addItemViews();
	}
	
	private void addItemViews(){
		
		int index = 0;
		for (int color : Constants.StyleColors) {
			View convertView = LayoutInflaterUtils.inflateView(mParent, R.layout.layout_color_item);
			final ParameterColorIcon colorItem = (ParameterColorIcon) convertView.findViewById(R.id.layout_color_item_icon);
			colorItem.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					setItemSelected(v);
					if(mOnParameterChangeListener != null){
						mOnParameterChangeListener.onColorChange(colorItem.getColor());
					}
				}
			});
			colorItem.setColor(color);
			mColorLayout.addView(convertView);
			
			if(index == 0){
				int firstColor = Constants.StyleColors[0];
				mRedSeekBar.setProgress(Color.red(firstColor));
				mGreenSeekBar.setProgress(Color.green(firstColor));
				mBlueSeekBar.setProgress(Color.blue(firstColor));
				setItemSelected(colorItem);
			}
			index++;
		}
		
	}

	@Override
	public void onProgressChanged(CustomSeekBar seekBar, int progress) {
		if(seekBar.equals(mRedSeekBar)){
			mTmpView.setRed(progress);
		}else if(seekBar.equals(mGreenSeekBar)){
			mTmpView.setGreen(progress);
		}else if(seekBar.equals(mBlueSeekBar)){
			mTmpView.setBlue(progress);
		}
		if(mOnParameterChangeListener != null){
			mOnParameterChangeListener.onColorChange(mTmpView.getColor());
		}
	}
	
	public void setOnParameterChangeListener(OnParameterChangeListener l) {
		this.mOnParameterChangeListener = l;
	}
	
	public void setOnCustomColorAction(OnCustomColorAction action) {
		this.mAction = action;
	}
	
	@Override
	public void onClick(View v) {
		if(mAction != null){
			mAction.showParameterColorFragment(false);
		}
	}
	
	private void setItemSelected(View v){
		if(mTmpView != null){
			mTmpView.setSelected(false);
		}
		mTmpView = (ParameterColorIcon) v;
		if(mTmpView != null){
			mTmpView.setSelected(true);
		}
	}
	
	@Override
	protected void releaseHandlerFragment() {
		mAction = null;
		mOnParameterChangeListener = null;
		if(mRedSeekBar != null){
			mRedSeekBar.setOnSeekBarChangeListener(null);
			mRedSeekBar = null;
		}
		
		if(mRedSeekBar != null){
			mRedSeekBar.setOnSeekBarChangeListener(null);
			mRedSeekBar = null;
		}
		
		if(mBlueSeekBar != null){
			mBlueSeekBar.setOnSeekBarChangeListener(null);
			mBlueSeekBar = null;
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
	}

}

