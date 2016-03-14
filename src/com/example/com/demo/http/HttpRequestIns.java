package com.example.com.demo.http;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

public class HttpRequestIns {

	public static final String BASE_API = "http://139.196.50.165/app/";
	
	
	protected static HttpRequestIns mInst = null;
	protected static Object mLock = new Object();
	
	private Context mContext;
	private final AsyncHttpClient mHttpClient;

	public static HttpRequestIns getInst(Context context) {
		if (mInst == null) {
			synchronized (mLock) {
				if (mInst == null) {
					mInst = new HttpRequestIns(context);
				}
			}
		}
		return mInst;
	}
	
	public HttpRequestIns(Context context) {
		mContext = context;
		mHttpClient = new AsyncHttpClient();
	}
	
	public final void get(String actionName, RequestParams params, ResponseHandlerInterface responseHandler){
		mHttpClient.addHeader("User-Agent", "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)");
		mHttpClient.get(mContext, BASE_API + actionName, params, responseHandler);
	}
}
