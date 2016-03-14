package com.example.com.demo.http.protocol;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.example.com.demo.bean.EntityResourceIconBean;
import com.example.com.demo.http.IProtocolListener;
import com.example.com.demo.http.ProtocolBase;
import com.example.com.demo.utils.GsonUtils;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

public class ProtocolGetImgs extends ProtocolBase<List<EntityResourceIconBean>>{
	
	private String mId;

	public ProtocolGetImgs(Context context, String id, IProtocolListener<List<EntityResourceIconBean>> iProtocolListener) {
		super(context, iProtocolListener);
		mId = id;
	}

	@Override
	protected String getActionName() {
		return "images";
	}

	@Override
	protected RequestParams getRequestParams() {
		RequestParams requestParams = new RequestParams();
		requestParams.put("id", mId);
		return requestParams;
	}

	@Override
	protected List<EntityResourceIconBean> parseJson(String response) {
		List<EntityResourceIconBean> beans;
		try {
			beans = GsonUtils.fromJson(response, new TypeToken<List<EntityResourceIconBean>>(){}.getType());
		} catch (Exception e) {
			beans = new ArrayList<EntityResourceIconBean>();
		}
		return beans;
	}

}
