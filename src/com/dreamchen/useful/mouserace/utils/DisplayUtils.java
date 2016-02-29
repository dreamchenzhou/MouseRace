package com.dreamchen.useful.mouserace.utils;

import com.dreamchen.useful.mouserace.global.ApplicationCore;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class DisplayUtils {

	public static DisplayMetrics getDisplayMetrics() {
		DisplayMetrics metrics = new DisplayMetrics();
		Context context = ApplicationCore.getInstance();
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		manager.getDefaultDisplay().getMetrics(metrics);
		return metrics;
	}

	/**
	 * dp转像素
	 * 
	 * @param context
	 * @param dipValue
	 * @return
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/***
	 * 像素转dp
	 * 
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

}
