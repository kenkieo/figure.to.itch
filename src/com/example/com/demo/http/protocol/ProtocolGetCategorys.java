package com.example.com.demo.http.protocol;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.example.com.demo.bean.EntityResourceCategorysBean;
import com.example.com.demo.http.IProtocolListener;
import com.example.com.demo.http.ProtocolBase;
import com.example.com.demo.utils.GsonUtils;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

/**
 * 获取分类
 * @author Administrator
 *
 */
public class ProtocolGetCategorys extends ProtocolBase<List<EntityResourceCategorysBean>>{

	public ProtocolGetCategorys(Context context, IProtocolListener<List<EntityResourceCategorysBean>> iProtocolListener) {
		super(context, iProtocolListener);
	}

	@Override
	protected String getActionName() {
		return "categorys";
	}

	@Override
	protected RequestParams getRequestParams() {
		return new RequestParams();
	}

	@Override
	protected List<EntityResourceCategorysBean> parseJson(String response) {
		List<EntityResourceCategorysBean> beans;
		try {
			beans = GsonUtils.fromJson(response, new TypeToken<List<EntityResourceCategorysBean>>(){}.getType());
		} catch (Exception e) {
			beans = new ArrayList<EntityResourceCategorysBean>();
		}
		return beans;
	}
	
}
