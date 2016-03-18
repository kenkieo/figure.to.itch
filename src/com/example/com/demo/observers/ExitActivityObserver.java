package com.example.com.demo.observers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
/**
 * activity退出时view类调用 
 * @author 蔡国辉
 *
 */
public class ExitActivityObserver {

	private static ExitActivityObserver mInst = null;
	private static Object mLock = new Object();

	private HashMap<Context, List<ExitActivityObserverAction>> mObserverActions;
	
	private ExitActivityObserver(){
		mObserverActions = new HashMap<Context, List<ExitActivityObserverAction>>();
	}
	
	public static ExitActivityObserver getInst(){
		if(mInst == null){
			synchronized(mLock){
				if(mInst == null){
					mInst = new ExitActivityObserver();
				}
			}
		}
		return mInst;
	}
	
	public void addExitActivityObserverAction(Context context, ExitActivityObserverAction observer){
		List<ExitActivityObserverAction> mActions = mObserverActions.get(context);
		if(mActions == null){
			mActions = new ArrayList<ExitActivityObserver.ExitActivityObserverAction>();
			mObserverActions.put(context, mActions);
		}
		
		if(!mActions.contains(observer)){
			mActions.add(observer);
		}
	}
	
	public void removeExitActivityObserverAction(Context context, ExitActivityObserverAction observer){
		List<ExitActivityObserverAction> mActions = mObserverActions.get(context);
		if(mActions != null){
			mActions.remove(observer);
		}
	}
	
	/**
	 * activity销毁时调用
	 * @param context
	 */
	public void onActivityDestory(Context context){
		List<ExitActivityObserverAction> mActions = mObserverActions.get(context);
		if(mActions != null){
			int size = mActions.size();
			for (int i = size - 1; i >= 0; i--) {
				try {
					mActions.get(i).onActivityDestory();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mActions.clear();
		}
		mObserverActions.remove(context);
	}

	public interface ExitActivityObserverAction {
		/**
		 * activity销毁时调用
		 */
		void onActivityDestory();
	}
}
