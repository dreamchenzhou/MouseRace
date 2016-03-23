package com.dreamchen.useful.mouserace.multithread;

import java.io.File;

import com.dreamchen.useful.mouserace.utils.FileUtils;

import android.text.TextUtils;

public class MultiCopy {
	
	private static final String COUNT_TAG_NOMAL ="COUNT_TAG_NOMAL"; 
	
	
	private static final String COUNT_TAG_10_M = "COUNT_TAG_10_M";
	
	private static final String COUNT_TAG_50_M ="COUNT_TAG_50_M";
	
	private static final String COUNT_TAG_100_M = "COUNT_TAG_100_M";
	
	private static final String COUNT_TAG_500_M = "COUNT_TAG_500_M";
	
	/**
	 * 
	 * @param threadCount 线程数量，如果文件过小的话，不会使用多线程
	 * @param fromPath 源路径
	 * @param toPath 目的路径 
	 * @return
	 */
	public static boolean copy(int threadCount,String fromPath,String toPath,StateInterface stateCallBack){
		if(TextUtils.isEmpty(fromPath)){
			return false;
		}
		File fromFile =new File(fromPath);
		if(!fromFile.exists()){
			return false;
		}
		File toFile = new File(toPath);
		try {
			if(!toFile.exists()){
				toFile.createNewFile();
			}
			String tag = getcountTag(fromFile);
			if(tag.equals(COUNT_TAG_NOMAL)||tag.equals(COUNT_TAG_10_M)){
				FileUtils.copy_chanel(fromFile, toFile, false);
			}else{
				//TODO 多线程
				FileUtils.multi_copy(5, fromFile, toFile, stateCallBack);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return true;
	}
	
	public static  String getcountTag(File file){
		if(file==null){
			throw new IllegalArgumentException("The file is null!");
		}
		long length = file.length();
		long M = 1024*1024;
		if(length<=M){
			return COUNT_TAG_NOMAL;
		}else if(length>M&&length<=10*M){
			return COUNT_TAG_10_M;
		}else if(length>10*M&&length<=100*M){
			return COUNT_TAG_50_M;
		}else if(length>100*M&&length<500*M){
			return COUNT_TAG_100_M;
		}else{
			throw new IllegalStateException("The file is too large!");
		}
	}
}
