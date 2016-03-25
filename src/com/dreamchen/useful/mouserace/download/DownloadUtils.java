package com.dreamchen.useful.mouserace.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.dreamchen.useful.mouserace.utils.LogUtils;

public class DownloadUtils {
	/**
	 * 
	 * 下载（单线程）
	 *
	 * @param urlAddr
	 * @param type
	 * @return 返回本地路径
	 * @throws Exception
	 */
	public  static String DownLoad(String urlAddr,String path) throws Exception
	{
		
		InputStream is = null;
		FileOutputStream fos = null;

		try
		{
//			int mapId = Environments.getMapId();
//			String down_url = String.format(dbDownUrl, mapId);
			HttpURLConnection urlConnection = null;
			URL url = new URL(urlAddr);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setDoInput(true);
			urlConnection.connect();
			is = urlConnection.getInputStream();
			fos = new FileOutputStream(new File(path));

			int read;

			byte[] bt = new byte[1024];

			while ((read = is.read(bt)) > 0)
			{
				fos.write(bt, 0, read);
			}
			LogUtils.Log_E("single thread download end time:"+System.currentTimeMillis());
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			if (fos != null)
			{
				fos.close();
			}
			if (is != null)
			{
				is.close();
			}

			File file = new File(path);
			if (file.exists() && file.length() > 0)
			{
				return file.getAbsolutePath();
			}
			return "";
		}
	}
}
