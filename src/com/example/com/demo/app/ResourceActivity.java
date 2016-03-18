package com.example.com.demo.app;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentTransaction;
import android.widget.ListView;

import com.example.com.demo.R;
import com.example.com.demo.adapter.CategoryAdapter;
import com.example.com.demo.adapter.CategoryAdapter.OnCategoryItemAction;
import com.example.com.demo.basefragmentactivity.BaseTitleFragmentActivity;
import com.example.com.demo.bean.EntityResourceCategorysBean;
import com.example.com.demo.fragment.style.menu.ResourceFragment;
import com.example.com.demo.http.IProtocolListener;
import com.example.com.demo.http.protocol.ProtocolGetCategorys;
import com.example.com.demo.observers.OnNetBitmapSelectObserver;
import com.example.com.demo.utils.DisplayUtils;
import com.example.com.demo.utils.LayoutInflaterUtils;
import com.example.com.demo.utils.ToastUtils;
import com.example.com.demo.widget.actionbar.menu.ActionbarMenuImageView;

/**
 * 素材
 * @author Administrator
 *
 */
public class ResourceActivity extends BaseTitleFragmentActivity implements OnCategoryItemAction{
	
	private ListView mCategoryListView;
	private CategoryAdapter mAdapter;
	private List<EntityResourceCategorysBean> mBeans;
	
	private ProtocolGetCategorys mGetCategorys;
	
	private ResourceFragment mResourceFragment;
	
	@Override
	protected int getLayoutRes() {
		return R.layout.activity_resource;
	}

	@Override
	protected void addFragments() {
		FragmentTransaction ft = mFragmentManager.beginTransaction();
		mResourceFragment = new ResourceFragment();
		ft.add(R.id.layout_framelayout, mResourceFragment);
		ft.commit();
	}

	@Override
	protected void initViews_BaseTitleFragmentActivity() {
		mCategoryListView = (ListView) findViewById(R.id.layout_listview);
		mCategoryListView.setDivider(getResources().getDrawable(R.color.common_transparent));
		mCategoryListView.setDividerHeight(DisplayUtils.dip2px(mContext, 15));
	}

	@Override
	protected void setSelection(int idx, boolean show) {
		
	}

	@Override
	protected void initData() {
		setTitle(R.string.text_menu_material);
		mBeans = new ArrayList<EntityResourceCategorysBean>();
		mAdapter = new CategoryAdapter(mContext, mBeans, this);
		mCategoryListView.setAdapter(mAdapter);
	}
	
	@Override
	public void addActionbarMenus() {
		super.addActionbarMenus();
		ActionbarMenuImageView sureIcon = (ActionbarMenuImageView) LayoutInflaterUtils.inflateView(mContext, R.layout.layout_actionbar_menu_icon);
		sureIcon.setImageResource(R.drawable.icon_alert_success_icon);
		addMenuItem(sureIcon);
	}
	
	@Override
	public void onMenuAction(int menuId) {
		super.onMenuAction(menuId);
		Drawable selectDrawable = mResourceFragment.getSelectedDrawable();
		if(selectDrawable != null){
			OnNetBitmapSelectObserver.getInst().onResourceSelect(selectDrawable);
			finish();
		}else{
			ToastUtils.showLongToast(mContext, "未选择图片或所选的图片暂时未加载出来~");
		}
	}
	
	@Override
	protected void loadData(Context context) {
		super.loadData(context);
		mGetCategorys = new ProtocolGetCategorys(mContext, new IProtocolListener<List<EntityResourceCategorysBean>>(){
			
			@Override
			public void onSuccess(List<EntityResourceCategorysBean> t) {
				super.onSuccess(t);
				mBeans.addAll(t);
				mAdapter.notifyDataSetChanged();
				if(!mBeans.isEmpty()){
					onItemSelected(0);
				}
			}
			
		});
		mGetCategorys.postReuqest();
	}
	
	@Override
	public void onItemSelected(final int position) {
		EntityResourceCategorysBean bean = mBeans.get(position);
		mResourceFragment.setCode(bean.id);
	}

	@Override
	protected void release_BaseTitleFragmentActivity() {
		
		if(mCategoryListView != null){
			mCategoryListView.setAdapter(null);
			mCategoryListView = null;
		}
		
		if(mAdapter != null){
			mAdapter.releaseAdapter();
			mAdapter = null;
		}
		
		if(mBeans != null){
			mBeans.clear();
			mBeans = null;
		}
		mGetCategorys	 	= null;
		mResourceFragment 	= null;
	}

}
