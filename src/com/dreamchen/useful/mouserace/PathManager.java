package com.dreamchen.useful.mouserace;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import topevery.android.core.StringCollection;
import topevery.android.framework.utils.TextUtils;
import android.os.Environment;

import com.dreamchen.useful.mouserace.global.ApplicationCore;

public class PathManager {
//	private static String dir = Environment.getExternalStorageDirectory().getPath();
//	
	private static String AppName = ApplicationCore.getInstance().getString(R.string.app_name);
//	
//	private static String startDir = dir+File.separator+AppName;
//	
//	private static final String IMAGE = "images";
	
	
	public static String sdcard = "";
	public static String startUpPath = null;
	public static String pathStartUpByMap = "";

	static StringCollection cardCollection = new StringCollection();
	
	private static SimpleDateFormat mFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
	
	public static String getImagePath(){
		String path = getStartUp() + "/Files/" +"images";
		mkdirs(path);
		return path;
	}
	
	static
	{
		String appDomainPath = "/dream/%s";
		appDomainPath = String.format(appDomainPath, AppName);
		
		cardCollection.add("/storage/sdcard0");
		cardCollection.add("/storage/sdcard1");
		cardCollection.add("/storage/extSdCard");
		String strReturn = getsdcard(appDomainPath);
		if (!TextUtils.isEmpty(strReturn))
		{
			sdcard = strReturn;
		}

		if (android.text.TextUtils.isEmpty(sdcard))
		{
			sdcard = Environment.getExternalStorageDirectory().getAbsolutePath();
		}

		startUpPath = pathStartUpByMap = sdcard + appDomainPath;
		mkdirs(startUpPath);
	}

	static String getsdcard(String appDomainPath)
	{
		String strReturn = "";

		for (String item : cardCollection)
		{
			File file = new File(item + appDomainPath);
			if (file.exists())
			{
				return item;
			}
		}

		return strReturn;
	}

	public static String getStartUp()
	{
		return startUpPath;
	}

	public static void setStartUp(String path)
	{
		startUpPath = path;
	}

	/**
	 * Environments.getLoginName()后面必须加trim
	 * 要不然文件格式为"username"+"空格"
	 * @return
	 */
	public static String getUserPath()
	{
		String path = getStartUp() + "/Files/" +"users";
		mkdirs(path);
		return path;
	}

	public static String getDBPath()
	{
		String path = startUpPath + "/Files/";
		mkdirs(path);
		path = path + "/";
		return path;
	}

	public static String getOffDBPath()
	{
		String path = startUpPath + "/Files/";
		mkdirs(path);
		path = path + "/";
		return path;
	}

	public static String getOffDBTempPath()
	{
		getDownTempPath();

		String path = startUpPath + "/Files/Temp/";
		mkdirs(path);
		path = path + "/";
		return path;
	}

	public static String getDownTempPath()
	{
		String path = getStartUp() + "/Files/Temp";
		mkdirs(path);
		return path;
	}

	public static String dirError()
	{
		String path = null;
		path = getStartUp() + "/Files/log/";
		mkdirs(path);
		return path;
	}

	public static boolean exists(String path)
	{
		if (!TextUtils.isEmpty(path))
		{
			return exists(new File(path));
		}
		return false;
	}

	public static boolean exists(File file)
	{
		if (file != null)
		{
			return file.exists();
		}
		return false;
	}

	public static String getTempPath()
	{
		String path = getStartUp() + "/Files/Temp";
		mkdirs(path);
		return path;
	}

	public static void clearTemp()
	{
		try
		{
			String path = getTempPath();
			File file = new File(path);
			File[] files = file.listFiles();
			if (files != null && files.length > 0)
			{
				for (File item : files)
				{
					item.delete();
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void clearPhotosTemp()
	{
		try
		{
			String path = getPhotosTemp();
			File file = new File(path);
			File[] files = file.listFiles();
			if (files != null && files.length > 0)
			{
				for (File item : files)
				{
					item.delete();
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static String getPhotosTemp()
	{
		String path = sdcard + "/dream/TempPhotos";
		mkdirs(path);
		return path;
	}

	public static String getPhotosPath()
	{
		String path = getUserPath() + "/Photos";
		mkdirs(path);
		return path;
	}
	
	public static String getAudiosPath(){
		String path = getUserPath() + "/Audios";
		mkdirs(path);
		return path;
	}
	
	public static String getVideosPath(){
		String path = getUserPath() + "/Videos";
		mkdirs(path);
		return path;
	}

	// public static String getRecordsPath()
	// {
	// String path = getUserPath() + "/Records";
	// mkdirs(path);
	// return path;
	// }
	//
	// public static String getTaskSucceedPath()
	// {
	// String path = getUserPath() + "/TaskSucceed2";
	// mkdirs(path);
	// return path;
	// }
	//
	// public static String getCasePath()
	// {
	// String path = getUserPath() + "/Cases";
	// mkdirs(path);
	// return path;
	// }

	public static void mkdirs(String path)
	{
		File file = new File(path);
		if (!file.exists())
		{
			file.mkdirs();
		}
	}

	public static void delete(String path)
	{
		delete(new File(path));
	}
	
	public static String getMediaPath(){
		String path = getUserPath() + "/media";
		mkdirs(path);
		return path;
	}

	public static void delete(File file)
	{
		try
		{
			if (file != null && file.exists())
			{
				file.delete();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
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
