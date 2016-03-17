package com.dreamchen.useful.mouserace.global;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.dreamchen.useful.mouserace.log.LogManager;

public class ApplicationCore extends Application implements UncaughtExceptionHandler {
	private static ApplicationCore applicationCore;
	
	public void onCreate() {
		applicationCore = this;
		SDKInitializer.initialize(this);
	};

	public static ApplicationCore getInstance(){
		return applicationCore;
	}

	/**
	 * 捕获错误
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		// TODO 自动生成的方法存根
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		StackTraceElement[] trace = ex.getStackTrace();
		StackTraceElement[] trace2 = new StackTraceElement[trace.length + 3];
		System.arraycopy(trace, 0, trace2, 0, trace.length);
		trace2[trace.length + 0] = new StackTraceElement("Android", "MODEL", android.os.Build.MODEL, -1);
		trace2[trace.length + 1] = new StackTraceElement("Android", "VERSION", android.os.Build.VERSION.RELEASE, -1);
		trace2[trace.length + 2] = new StackTraceElement("Android", "FINGERPRINT", android.os.Build.FINGERPRINT, -1);
		ex.setStackTrace(trace2);
		ex.printStackTrace(printWriter);
		String stacktrace = stringWriter.toString();
		printWriter.close();
		LogManager.writeLog(stacktrace);
	}
}
