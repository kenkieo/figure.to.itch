package com.example.com.demo.fragment.style.menu;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.example.com.demo.R;
import com.example.com.demo.fragment.BaseHandlerFragment;
/**
 * 模式选择
 * @author Administrator
 *
 */
public class StyleModeFragment extends BaseHandlerFragment implements OnClickListener{

	private ImageView mMode_1;
	private ImageView mMode_2;
	private ImageView mMode_3;
	
	private View 	mTmpView;
	
	private OnChoiceModeAction mAction;

	
	@Override
	protected int getLayoutRes() {
		return R.layout.fragment_style_mode_content;
	}
	
	@Override
	protected void initViews(View convertView) {
		mMode_1 	= (ImageView) convertView.findViewById(R.id.fragment_style_menu_mode_1);
		mMode_2 	= (ImageView) convertView.findViewById(R.id.fragment_style_menu_mode_2);
		mMode_3 	= (ImageView) convertView.findViewById(R.id.fragment_style_menu_mode_3);
		
		if(mMode_1 != null){
			mMode_1.setOnClickListener(this);
		}
		
		if(mMode_2 != null){
			mMode_2.setOnClickListener(this);
		}
		
		if(mMode_3 != null){
			mMode_3.setOnClickListener(this);
		}
		
		setItemSelected(mMode_1);
	}


	@Override
	public void onClick(View v) {
		Mode mode = Mode.MODE_1;
		switch (v.getId()) {
		case R.id.fragment_style_menu_mode_1:
			mode = Mode.MODE_1;
			break;
		case R.id.fragment_style_menu_mode_2:
			mode = Mode.MODE_2;
			break;
		case R.id.fragment_style_menu_mode_3:
			mode = Mode.MODE_3;
			break;
		}
		setItemSelected(v);
		
		if(mAction != null){
			mAction.onMenuChoiced(mode);
		}
	}
	
	private void setItemSelected(View tmpView){
		if(mTmpView != null){
			mTmpView.setSelected(false);
		}
		mTmpView = tmpView;
		if(mTmpView != null){
			mTmpView.setSelected(true);
		}
	}
	
	public void setOnChoiceModeAction(OnChoiceModeAction action) {
		this.mAction = action;
	}
	
	public interface OnChoiceModeAction{
		void onMenuChoiced(Mode mode);
	}
	
	public enum Mode{
		MODE_1,//第一种模式
		MODE_2,//第二种模式
		MODE_3//第三种模式
	}
	
	@Override
	protected void releaseHandlerFragment() {
		mAction = null;
		if(mMode_1 != null){
			mMode_1.setOnClickListener(null);
			mMode_1 = null;
		}
		
		if(mMode_2 != null){
			mMode_2.setOnClickListener(null);
			mMode_2 = null;
		}
		
		if(mMode_3 != null){
			mMode_3.setOnClickListener(null);
			mMode_3 = null;
		}
		mTmpView = null;
	}
	
}
