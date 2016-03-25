package com.dreamchen.useful.mouserace.multithread;

import java.io.File;

import com.dreamchen.useful.mouserace.utils.FileUtils;

import android.text.TextUtils;

public class MultiCopy extends FileBase {
	
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
	
}
