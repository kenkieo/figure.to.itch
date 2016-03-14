package com.example.com.demo.http;

public class IProtocolListener<T> {

	public void onStart(){}
	
	public void onSuccess(T t){}
	
	public void onFail(int code, String msg){}
	
	public void onFinish(){}
}
