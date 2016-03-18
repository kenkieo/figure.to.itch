package com.example.com.demo.fragment.style.menu;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.com.demo.R;
import com.example.com.demo.fragment.BaseHandlerFragment;
import com.example.com.demo.interfaces.OnResourceSelectAction;
import com.example.com.demo.utils.ActivityUtils;
import com.example.com.demo.utils.LayoutInflaterUtils;
/**
 * 素材
 * @author Administrator
 *
 */
public class StyleResourceFragment extends BaseHandlerFragment implements OnClickListener{

	private ViewGroup mContentLayout;
	private static final int mResIds[] = new int[]{R.drawable.icon_default_source_1,
		R.drawable.icon_default_source_2, R.drawable.icon_default_source_3, R.drawable.icon_default_source_4,
		R.drawable.icon_default_source_5, R.drawable.icon_default_source_6, R.drawable.icon_default_source_7,
		R.drawable.icon_default_source_8, R.drawable.icon_default_source_9, R.drawable.icon_default_source_10};
	private View mClickView;
	private OnResourceSelectAction mAction;
	
	@Override
	protected int getLayoutRes() {
		return R.layout.fragment_style_resource;
	}

	@Override
	protected void initViews(View convertView) {
		mContentLayout = (ViewGroup) convertView.findViewById(R.id.fragment_style_resource_content);
		addItemFirst();
		addItemViews();
	}
	
	private void addItemFirst(){
		View convertView = LayoutInflaterUtils.inflateView(mParent, R.layout.fragment_style_resource_add);
		convertView.setOnClickListener(this);
		convertView.setTag(0);
		mContentLayout.addView(convertView);
	}
	
	private void addItemViews(){
		for (int resId : mResIds) {
			View convertView = LayoutInflaterUtils.inflateView(mParent, R.layout.layout_resource_item);
			ImageView mItemIcon = (ImageView) convertView.findViewById(R.id.layout_resource_item_icon);
			mItemIcon.setImageResource(resId);
			mItemIcon.setOnClickListener(this);
			mItemIcon.setTag(resId);
			mContentLayout.addView(convertView);
		}
	}

	@Override
	public void onClick(View v) {
		setItemSelected(v);
		switch (v.getId()) {
		case R.id.fragment_style_resource_add:
			ActivityUtils.startResourceActivity(mParent);
			break;
		}
	}
	
	private void setItemSelected(View v){
		if(mClickView != null){
			mClickView.setSelected(false);
		}
		mClickView = v;
		if(mClickView != null){
			mClickView.setSelected(true);
			int resId = 0;
			try {
				resId = Integer.parseInt(mClickView.getTag().toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			Log.i("TAG", "resId:" + resId);
			if(resId > 0){
				if(mAction != null){
					mAction.onResourceSelect(getResources().getDrawable(resId));
				}
			}
		}
	}
	
	public void setOnResourceSelectAction(OnResourceSelectAction action) {
		this.mAction = action;
	}
	
	@Override
	protected void releaseHandlerFragment() {
		if(mContentLayout != null){
			mContentLayout.removeAllViews();
			mContentLayout = null;
		}
		mClickView = null;
	}

}
