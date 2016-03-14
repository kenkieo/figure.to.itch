package com.example.com.demo.fragment.style.menu;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.GridView;

import com.example.com.demo.R;
import com.example.com.demo.adapter.ResourceIconAdapter;
import com.example.com.demo.adapter.ResourceIconAdapter.OnIconItemAction;
import com.example.com.demo.bean.EntityResourceIconBean;
import com.example.com.demo.fragment.BaseHandlerFragment;
import com.example.com.demo.http.IProtocolListener;
import com.example.com.demo.http.protocol.ProtocolGetImgs;

public class ResourceFragment extends BaseHandlerFragment implements OnIconItemAction{
	
	private String 				mCode;
	private ProtocolGetImgs 	mProtocolGetImgs;
	private GridView 			mGridView;
	private ResourceIconAdapter mAdapter;
	private List<EntityResourceIconBean> mBeans;

	@Override
	protected int getLayoutRes() {
		return R.layout.layout_gridview;
	}

	@Override
	protected void initViews(View convertView) {
		mGridView = (GridView) convertView.findViewById(R.id.layout_gridview);
		mBeans = new ArrayList<EntityResourceIconBean>();
		mAdapter = new ResourceIconAdapter(mParent, mBeans, this);
		mGridView.setAdapter(mAdapter);
	}
	
	public void setCode(String code) {
		if(code.equals(mCode)){
			return;
		}
		this.mCode = code;
		if(loadDataAble()){
			mBeans.clear();
			mAdapter.notifyDataSetChanged();
			loadData(mParent);
		}else{
			lazyLoadData(mParent);
		}
	}
	
	@Override
	protected void loadData(Context context) {
		super.loadData(context);
		mProtocolGetImgs = new ProtocolGetImgs(mParent, mCode, new IProtocolListener<List<EntityResourceIconBean>>(){
			
			public void onSuccess(List<EntityResourceIconBean> t) {
				mBeans.addAll(t);
				mAdapter.notifyDataSetChanged();
			}
			
			@Override
			public void onFail(int code, String msg) {
				super.onFail(code, msg);
			}
			
		});
		mProtocolGetImgs.postReuqest();
	}
	
	@Override
	public void onItemSelected(int position) {
		
	}
	
	@Override
	protected void releaseHandlerFragment() {
		
	}

}
