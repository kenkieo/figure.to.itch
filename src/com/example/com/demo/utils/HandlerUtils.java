package com.example.com.demo.utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class HandlerUtils {
	
	public static final void sendEmptyMessage(Handler handler, int what){
		sendEmptyMessageDelayed(handler, what, 0);
	}
	
	public static final void sendEmptyMessageDelayed(Handler handler, int what, long delayMillis){
		if(handler != null){
			handler.sendEmptyMessageDelayed(what, delayMillis);
		}
	}
	
	public static final void sendMessage(Handler handler, Message msg){
		sendMessageDelayed(handler, msg, 0);
	}
	
	public static final void sendMessageDelayed(Handler handler, Message msg, long delayMillis){
		if(handler != null){
			handler.sendMessageDelayed(msg, delayMillis);
		}
	}
	
	public static final Message obtainMessage(int what, int arg1, int arg2){
		Message message = new Message();
		message.what = what;
		message.arg1 = arg1;
		message.arg2 = arg2;
		return message;
	}
	
	public static final void post(Handler handler, Runnable r){
		postDelayed(handler, r, 0);
	}
	
	public static final void postDelayed(Handler handler, Runnable r, long delayMillis){
		if(handler != null){
			handler.postDelayed(r, delayMillis);
		}
	}
	
	public static final void removeMessages(Handler handler, int what){
		if(handler != null){
			handler.removeMessages(what);
		}
	}
	
	public static final void removeCallbacks(Handler handler, Runnable r){
		if(handler != null){
			handler.removeCallbacks(r);
		}
	}
	
	public static final void removeCallbacksAndMessages(Handler handler){
		Log.i("TAG", "removeCallbacksAndMessages");
		if(handler != null){
			handler.removeCallbacksAndMessages(null);
		}
	}
	
	public static final void release(Handler handler){
		removeCallbacksAndMessages(handler);
	}
	
}
