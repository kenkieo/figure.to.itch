package com.example.com.demo.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.example.com.demo.R;
import com.example.com.demo.bean.EntityResourceIconBean;
import com.example.com.demo.utils.DisplayImageOptionsUtils;
import com.example.com.demo.utils.LayoutInflaterUtils;

public class ResourceIconAdapter extends Adapter<EntityResourceIconBean>{
	
	private OnIconItemAction mAction;
	private int mSelection = -1;

	public ResourceIconAdapter(Context context, List<EntityResourceIconBean> beans, OnIconItemAction action) {
		super(context, beans);
		mAction = action;
	}

	@Override
	public View newView(Context context, int position, View convertView) {
		return LayoutInflaterUtils.inflateView(mContext, R.layout.layout_resource_item);
	}

	@Override
	public void bindView(Context context, final int position, View convertView) {
		final ViewHolder holder;
		if(convertView.getTag() == null){
			holder = new ViewHolder();
			holder.mIconImg = (ImageView) convertView.findViewById(R.id.layout_resource_item_icon);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		EntityResourceIconBean bean = mBeans.get(position);
		convertView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mSelection = position;
				if(mAction != null){
					mAction.onItemSelected(position);
				}
				notifyDataSetChanged();
			}
		});
		DisplayImageOptionsUtils.displayImage(bean.url, holder.mIconImg, DisplayImageOptionsUtils.getDefault());
		holder.mIconImg.setSelected(mSelection == position);
	}
	
	private class ViewHolder{
		ImageView mIconImg;
	}
	
	public interface OnIconItemAction{
		void onItemSelected(int position);
	}

}
