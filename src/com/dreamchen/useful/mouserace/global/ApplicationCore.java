package com.dreamchen.useful.mouserace.global;

import android.app.Application;

public class ApplicationCore extends Application {
	private static ApplicationCore applicationCore;
	
	public void onCreate() {
		applicationCore = this;
	};
	
	public static ApplicationCore getInstance(){
		return applicationCore;
	}
}
