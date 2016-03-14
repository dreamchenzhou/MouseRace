package com.dreamchen.useful.mouserace.global;

import com.baidu.mapapi.SDKInitializer;

import android.app.Application;

public class ApplicationCore extends Application {
	private static ApplicationCore applicationCore;
	
	public void onCreate() {
		applicationCore = this;
		SDKInitializer.initialize(this);
	};

	public static ApplicationCore getInstance(){
		return applicationCore;
	}
}
