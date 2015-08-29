package com.idea.xbox.framework.core.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public abstract class BaseLogic implements ILogic {
	private static final String TAG = "BaseLogic";

	private boolean mIsSingleton = false;

	private boolean mIsProxy = false;

	/**
	 * logic对象中UI监听的handler缓存集合
	 */
	private final List<Handler> mHandlerList = new Vector<Handler>();

	/**
	 * 数据库Uri对应的Observer对象的集�?
	 */
	private final Map<Uri, ContentObserver> mObserverCache = new HashMap<Uri, ContentObserver>();

	/**
	 * 系统的context对象
	 */
	private Context mContext;

	private Handler mHandler = new InnerHandler(this, mHandlerList);

	private Object mTargetObject;

	public Object getTargetObject() {
		return mTargetObject;
	}

	public void setTargetObject(Object targetObject) {
		this.mTargetObject = targetObject;
	}

	/**
	 * 初始化方�?BR> 在被系统管理的logic在注册到LogicBuilder中后立即被调用的初始化方法�?
	 * 
	 * @param context
	 *            系统的context对象
	 */
	public void init(Context context) {
		this.mContext = context;
		// 对子类定义的URI进行数据库表操作监听
		Uri[] uris = getObserverUris();
		if (uris != null) {
			for (Uri uri : uris) {
				registerObserver(uri);
			}
		}
	}

	/**
	 * 对logic增加handler<BR>
	 * 在logic对象里加入UI的handler
	 * 
	 * @param handler
	 *            UI传入的handler对象
	 */
	public final void addHandler(Handler handler) {
		int size = mHandlerList.size();
		if (!mHandlerList.contains(handler)) {
			mHandlerList.add(handler);
		}
		Log.i(TAG, "In add hander method." + this.getClass().getName()
				+ " have " + size + "->" + mHandlerList.size() + " hander.");
	}

	/**
	 * 对logic移除handler<BR>
	 * 在logic对象里移除UI的handler
	 * 
	 * @param handler
	 *            UI传入的handler对象
	 */
	public final void removeHandler(Handler handler) {
		int size = mHandlerList.size();
		mHandlerList.remove(handler);
		Log.i(TAG, "In remove hander method." + this.getClass().getName()
				+ " have " + size + "->" + mHandlerList.size() + " hander.");
	}

	/**
	 * �?该logic对象定义被监听所有Uri<BR>
	 * 通过子类重载该方法，传入Uri数组来监听该logic�?��监听的数据库对象�? 没有重载或返回为空将不监听任何数据库表变�?
	 * 
	 * @return Uri数组
	 */
	protected Uri[] getObserverUris() {
		return null;
	}

	/**
	 * 对URI注册鉴定数据库表<BR>
	 * 根据URI监听数据库表的变化，放入监听对象缓存
	 * 
	 * @param uri
	 *            数据库的Content Provider�?Uri
	 */
	protected final void registerObserver(final Uri uri) {
		ContentObserver observer = new ContentObserver(new Handler()) {
			public void onChange(final boolean selfChange) {
				BaseLogic.this.onChangeByUri(selfChange, uri);
			}
		};
		mContext.getContentResolver().registerContentObserver(uri, true,
				observer);
		mObserverCache.put(uri, observer);
	}

	/**
	 * 对URI注册鉴定数据库表<BR>
	 * 根据URI监听数据库表的变化，放入监听对象缓存
	 * 
	 * @param uri
	 *            数据库的Content Provider�?Uri
	 * @param observer
	 *            数据库的Content Provider
	 */
	protected final void registerObserver(final Uri uri,
			ContentObserver observer) {
		mContext.getContentResolver().registerContentObserver(uri, true,
				observer);
		mObserverCache.put(uri, observer);
	}

	/**
	 * 对URI解除注册鉴定数据库表<BR>
	 * 根据URI移除对数据库表变化的监听，移出监听对象缓�?
	 * 
	 * @param uri
	 *            数据库的Content Provider�?Uri
	 */
	protected final void unRegisterObserver(Uri uri) {
		ContentObserver observer = mObserverCache.get(uri);
		if (observer != null) {
			mContext.getContentResolver().unregisterContentObserver(observer);
			mObserverCache.remove(uri);
		}
	}

	/**
	 * 当对数据库表定义的Uri进行监听后，被回调方�?BR> 子类中需重载该方法，可以在该方法的代码中对表变化进行监听实现
	 * 
	 * @param selfChange
	 *            如果是true，被监听是由于代码执行了commit造成�?
	 * @param uri
	 *            被监听的Uri
	 */
	protected void onChangeByUri(boolean selfChange, Uri uri) {

	}

	/**
	 * 发�?消息给UI<BR>
	 * 通过监听回调，�?知在该logic对象中所有注册了handler的UI消息message对象
	 * 
	 * @param what
	 *            返回的消息标�?
	 * @param obj
	 *            返回的消息数据对�?
	 */
	public void sendMessage(int what, Object obj) {
		synchronized (mHandlerList) {
			for (Handler handler : mHandlerList) {
				if (obj == null) {
					handler.sendEmptyMessage(what);
				} else {
					Message message = new Message();
					message.what = what;
					message.obj = obj;
					handler.sendMessage(message);
				}
			}
		}
	}

	/**
	 * 发�?无数据对象消息给UI<BR>
	 * 通过监听回调，�?知在该logic对象中所有注册了handler的UI消息message对象
	 * 
	 * @param what
	 *            返回的消息标�?
	 */
	public void sendEmptyMessage(int what) {
		synchronized (mHandlerList) {
			for (Handler handler : mHandlerList) {
				handler.sendEmptyMessage(what);
			}
		}
	}

	/**
	 * 延迟发�?空消息给UI<BR>
	 * 通过监听回调，延迟�?知在该logic对象中所有注册了handler的UI消息message对象
	 * 
	 * @param what
	 *            返回的消息标�?
	 * @param delayMillis
	 *            延迟时间，单位秒
	 */
	public void sendEmptyMessageDelayed(int what, long delayMillis) {
		if (!mHandler.hasMessages(what)) {
			mHandler.sendEmptyMessageDelayed(what, delayMillis);
		}

	}

	/**
	 * 延迟发�?消息给UI<BR>
	 * 通过监听回调，延迟�?知在该logic对象中所有注册了handler的UI消息message对象
	 * 
	 * @param what
	 *            返回的消息标�?
	 * @param obj
	 *            返回的消息数据对�?
	 * @param delayMillis
	 *            延迟时间，单位秒
	 */
	public void sendMessageDelayed(int what, Object obj, long delayMillis) {
		if (!mHandler.hasMessages(what)) {
			Message msg = new Message();
			msg.what = what;
			msg.obj = obj;
			mHandler.sendMessageDelayed(msg, delayMillis);
		}
	}

	/**
	 * onSendMessageDelayed
	 * 
	 * @param msg
	 *            返回的消�?
	 */
	protected void onSendMessageDelayed(Message msg) {
	}

	/**
	 * onSendEmptyMessageDelayed
	 * 
	 * @param what
	 *            返回的消�?
	 */
	protected void onSendEmptyMessageDelayed(int what) {
	}

	public void setIsProxy(boolean isProxy) {
		this.mIsProxy = isProxy;
	}

	public boolean isProxy() {
		return mIsProxy;
	}

	public void setIsSinleton(boolean isSingleton) {
		this.mIsSingleton = isSingleton;
	}

	public boolean isSingleton() {
		return mIsSingleton;
	}

	private static class InnerHandler extends Handler {
		private BaseLogic mLogic;

		private final List<Handler> mHandlerList;

		public InnerHandler(BaseLogic logic, List<Handler> mHandlerList) {
			this.mLogic = logic;
			this.mHandlerList = mHandlerList;
		}

		public void handleMessage(Message msg) {
			synchronized (mHandlerList) {
				if (msg.obj == null) {
					mLogic.onSendEmptyMessageDelayed(msg.what);
				} else {
					mLogic.onSendMessageDelayed(msg);
				}
				for (Handler handler : mHandlerList) {
					if (!handler.hasMessages(msg.what)) {
						if (msg.obj == null) {
							handler.sendEmptyMessage(msg.what);
						} else {
							Message message = new Message();
							message.what = msg.what;
							message.obj = msg.obj;
							handler.sendMessage(message);
						}
					}
				}
			}
		}
	}
}
