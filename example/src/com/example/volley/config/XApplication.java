package com.example.volley.config;

import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap.CompressFormat;
import android.os.Debug;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.android.volley.RequestQueue;
import com.example.volley.http.base.BitmapLruImageCache;
import com.example.volley.http.base.RequestManager;
import com.idea.xbox.component.logger.Logger;
import com.idea.xbox.framework.core.BaseApplication;
import com.idea.xbox.framework.core.logic.builder.ILogicBuilder;

public class XApplication extends BaseApplication {
	private static final String TAG = "XApplication";

	private static final String OOM = "java.lang.OutOfMemoryError";
	private static final String HPROF_FILE_DIR = Environment
			.getExternalStorageDirectory().getPath()
			+ File.separator
			+ "volley" + File.separator + "hprof" + File.separator;

	public static int IMAGECACHE_SIZE = 1024 * 1024 * 2;
	public static CompressFormat DISK_IMAGECACHE_COMPRESS_FORMAT = CompressFormat.PNG;
	public static int DISK_IMAGECACHE_QUALITY = 30;

	public BitmapLruImageCache mImageCache;

	private static XApplication mApp = null;

	private static RequestQueue mRequestQueue;

	private String mDeviceId;

	private PackageInfo mPkgInfo;

	private XConfigLogicBuilder logicBuiler = null;

	private int mWindowWidth;
	private int mWindowHeight;
	private float mWindowDensity;
	private float mWindowDensityDpi;

	private List<Activity> mActivityList = new LinkedList<Activity>();
	private XCrashHandler mCrashHandler;

	@Override
	protected void initSystem(Context context) {
		mApp = this;
		mApp.initVolley();

		try {
			mPkgInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		DisplayMetrics metric = new DisplayMetrics();
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(metric);
		mWindowWidth = metric.widthPixels;
		mWindowHeight = metric.heightPixels;
		mWindowDensity = metric.density;
		mWindowDensityDpi = metric.densityDpi;

		mCrashHandler = new XCrashHandler();
		mCrashHandler.setWooCrashHandler(this);

	}

	@Override
	protected ILogicBuilder createLogicBuilder(Context context) {
		logicBuiler = new XConfigLogicBuilder(context);
		return logicBuiler;
	}

	public static XApplication getApp() {
		return mApp;
	}

	private void initVolley() {
		RequestManager.init(getApplicationContext());

		// 获取到可用内存的最大值，使用内存超出这个值会引起OutOfMemory异常
		// 使用最大可用内存值的1/8作为缓存的大小。
		int maxsize = (int) (Runtime.getRuntime().maxMemory() / 1024) / 8;
		mImageCache = new BitmapLruImageCache(maxsize);
	}

	/**
	 * Create the image cache. Uses Memory Cache by default. Change to Disk for
	 * a Disk based LRU implementation.
	 */
	public BitmapLruImageCache getVolleyImageCache() {
		if (mImageCache == null) {
			mImageCache = new BitmapLruImageCache(IMAGECACHE_SIZE);
		}
		return mImageCache;
	}

	public RequestQueue getRequestQueue() {
		if (mRequestQueue != null) {
			return mRequestQueue;
		} else {
			throw new IllegalStateException("RequestQueue not initialized");
		}
	}

	public String getDeviceId() {
		return mDeviceId;
	}

	public int getVersionCode() {
		return mPkgInfo.versionCode;
	}

	public String getVersionName() {
		return mPkgInfo.versionName;
	}

	public int getWindowWidth() {
		return mWindowWidth;
	}

	public int getWindowHeight() {
		return mWindowHeight;
	}

	public float getWindowDensity() {
		return mWindowDensity;
	}

	public float getWindowDensityDpi() {
		return mWindowDensityDpi;
	}

	private static class XCrashHandler implements UncaughtExceptionHandler {
		private static final String HPROF_FILE_PATH = HPROF_FILE_DIR
				+ "%1$tY%2$tm%3$td_%4$tT.hprof";

		@Override
		public void uncaughtException(Thread thread, Throwable ex) {

			final StackTraceElement[] stack = ex.getStackTrace();
			final String message = ex.getMessage();
			String log = message;

			for (int i = 0; i < stack.length; i++) {
				log += " " + stack[i].toString();
			}

			if (isOOM(ex)) {
				try {
					Date now = new Date(System.currentTimeMillis());
					String hprofPath = String.format(HPROF_FILE_PATH, now, now,
							now, now);
					Debug.dumpHprofData(hprofPath);
				} catch (Exception e) {
					Logger.e(TAG, "could not dump hprof", e);
				}
			}

			Logger.e(TAG, log, ex);
			mApp.exitApp();
		}

		public void setWooCrashHandler(Context context) {
			Thread.setDefaultUncaughtExceptionHandler(XCrashHandler.this);
		}
	}

	public static boolean isOOM(Throwable throwable) {
		Logger.d(TAG, "ExceptionName:" + throwable.getClass().getName());
		if (OOM.equals(throwable.getClass().getName())) {
			return true;
		} else {
			Throwable cause = throwable.getCause();
			if (cause != null) {
				return isOOM(cause);
			}
			return false;
		}
	}

	public void addActivity(Activity activity) {
		mActivityList.add(activity);
	}

	public void exitApp() {
		for (Activity activity : mActivityList) {
			activity.finish();
		}
	}

}
