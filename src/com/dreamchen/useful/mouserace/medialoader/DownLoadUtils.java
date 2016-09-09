package com.dreamchen.useful.mouserace.medialoader;

public class DownLoadUtils {

	private  static DownFinishedCallBack mCallBack;
	
	public static void addDownCallBack(DownFinishedCallBack callBack)
	{
		mCallBack = callBack;
	}
	
	public static void DownFinished(String path){
		if(mCallBack!=null){
			mCallBack.onDownFinished(path);
		}
	}
}
