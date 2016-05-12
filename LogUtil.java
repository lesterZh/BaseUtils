package com.zhanghaitao.mobileguard.utils;

import android.util.Log;

public class LogUtil {
	public static final int VERBOSE = 1;
	public static final int DEBUG = 2;
	public static final int INFO = 3;
	public static final int WARN = 4;
	public static final int ERROR = 5;
	public static final int NOTHING = 6;
	
	//想要屏蔽输出信息 可LEVEL=NOTHING
	public static final int LEVEL = VERBOSE;
	
	public static void v(String tag, String msg) {
		if (LEVEL <= VERBOSE) {
			Log.v(tag, msg);
		}
	}
	
	public static void d(String tag, String msg) {
		if (LEVEL <= DEBUG) {
			Log.d(tag, msg);
		}
	}
	
	public static void i(String tag, String msg) {
		if (LEVEL <= INFO) {
			Log.i(tag, msg);
		}
	}
	public static void i(String msg) {
		if (LEVEL <= INFO) {
			Log.i("ZHT", msg);
		}
	}
	
	public static void w(String tag, String msg) {
		if (LEVEL <= WARN) {
			Log.w(tag, msg);
		}
	}
	
	public static void e(String tag, String msg) {
		if (LEVEL <= ERROR) {
			Log.e(tag, msg);
		}
	}
	
	public static void w(String msg) {
		if (LEVEL <= WARN) {
			Log.w("ZHT", msg);
		}
	}
	
}
