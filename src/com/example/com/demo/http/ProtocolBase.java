package com.example.com.demo.http;

import org.apache.http.Header;
import org.json.JSONObject;

import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public abstract class ProtocolBase<T> {

	protected Context 	mContext;
	protected IProtocolListener<T> mIProtocolListener;
	
	public ProtocolBase(Context context, IProtocolListener<T> iProtocolListener) {
		mContext = context;
		mIProtocolListener = iProtocolListener;
	}
	
	protected abstract String getActionName();
	
	protected abstract RequestParams getRequestParams();
	
	public void postReuqest(){
		HttpRequestIns.getInst(mContext).get(getActionName(), getRequestParams(), new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				int code = -1;
				String msg = "";
				try {
					String response = new String(arg2);
					JSONObject jsonObject = new JSONObject(response);
					code = jsonObject.optInt("code");
					msg  = jsonObject.optString("msg");
					if(code == 0){
						T t = parseJson(msg);
						if(mIProtocolListener != null){
							mIProtocolListener.onSuccess(t);
						}
						return;
					}
				} catch (Exception e) {
				}
				if(mIProtocolListener != null){
					mIProtocolListener.onFail(code, "");
				}
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				if(mIProtocolListener != null){
					mIProtocolListener.onFail(400, "");
				}
			}
		});
	}
	
	protected abstract T parseJson(String response);
	
}
