package com.example.com.demo.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.com.demo.R;
import com.example.com.demo.bean.EntityResourceCategorysBean;
import com.example.com.demo.utils.LayoutInflaterUtils;

public class CategoryAdapter extends Adapter<EntityResourceCategorysBean>{
	
	private OnCategoryItemAction mAction;
	private int mSelection;

	public CategoryAdapter(Context context, List<EntityResourceCategorysBean> beans, OnCategoryItemAction action) {
		super(context, beans);
		mAction 	= action;
		mSelection 	= 0;
	}

	@Override
	public View newView(Context context, int position, View convertView) {
		return LayoutInflaterUtils.inflateView(context, R.layout.layout_category_item);
	}

	@Override
	public void bindView(Context context, final int position, View convertView) {
		final ViewHolder holder;
		if(convertView.getTag() == null){
			holder = new ViewHolder();
			holder.mCategoryItem = (TextView) convertView.findViewById(R.id.layout_category_item_name);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		EntityResourceCategorysBean bean = mBeans.get(position);
		holder.mCategoryItem.setText(bean.name);
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
		holder.mCategoryItem.setSelected(mSelection == position);
	}
	
	private class ViewHolder{
		TextView mCategoryItem;
	}
	
	public interface OnCategoryItemAction{
		void onItemSelected(int position);
	}

}
