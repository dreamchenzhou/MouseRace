package com.dreamchen.useful.mouserace.broadcast;

import java.util.ArrayList;

import android.content.ComponentName;
import android.content.Intent;

public class PurposeBroadCastManager {

	 public static ArrayList<PurposeBroadCast> list = new ArrayList<PurposeBroadCast>();

	 public static void registerBroadCastReceiver(PurposeBroadCast broadCast){
		 if(!list.contains(broadCast)){
			 list.add(broadCast);
		 }
	 }
	 
	 public static void unRegisterBroadCastReceiver(PurposeBroadCast broadCast){
		 list.remove(broadCast);
	 }
	 
	 public static void sendBroadCast(Intent intent){
		 for (PurposeBroadCast broadCast : list) {
			if(broadCast.getComponentName().getClassName().equals(intent.getComponent().getClassName())){
				broadCast.onReceiveBroadCast(intent);
				break;
			}
		}
	 }
	 
}
