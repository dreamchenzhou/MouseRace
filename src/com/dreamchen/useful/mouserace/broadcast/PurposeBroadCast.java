package com.dreamchen.useful.mouserace.broadcast;

import android.content.ComponentName;
import android.content.Intent;

/**
 * 
 * 定向广播
 */
public interface PurposeBroadCast  {
	public ComponentName getComponentName();
	
	public void onReceiveBroadCast(Intent intent);
}
