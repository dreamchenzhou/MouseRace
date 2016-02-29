package com.dreamchen.useful.mouserace;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dreamchen.useful.mouserace.global.ApplicationCore;

import android.os.Environment;

public class PathManager {
	private static String dir = Environment.getExternalStorageDirectory().getPath();
	
	private static String AppName = ApplicationCore.getInstance().getString(R.string.app_name);
	
	private static String startDir = dir+File.separator+AppName;
	
	private static final String IMAGE = "images";
	
	private static SimpleDateFormat mFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
	
	public static String getImagePath(){
		File startDirFile= new File(startDir);
		if(!startDirFile.exists()){
			startDirFile.mkdirs();
		}
		File dir = new File(startDir+File.separator+IMAGE);
		if(!dir.exists()){
			dir.mkdirs();
		}
		return dir.getAbsolutePath();
	}
	
	
	public static String getImageFileByTime(){
		String name =mFormat.format(new Date());
		File file = new File(getImagePath()+File.separator+name);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return file.getAbsolutePath();
		
	}
	
	
}
