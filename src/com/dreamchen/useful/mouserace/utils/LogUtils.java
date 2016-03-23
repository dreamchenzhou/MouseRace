package com.dreamchen.useful.mouserace.utils;

import android.util.Log;

import com.dreamchen.useful.mouserace.Setting;

public class LogUtils {
		public static void Log_E(String msg){
			if(Setting.LOGGER_SWITCHER){
				Log.e(Setting.TAG_DREAM, msg);
			}
		}
}	
