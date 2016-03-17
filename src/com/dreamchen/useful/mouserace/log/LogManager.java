package com.dreamchen.useful.mouserace.log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import topevery.android.framework.utils.TextUtils;
import android.annotation.SuppressLint;

import com.dreamchen.useful.mouserace.PathManager;

/**
 * 日志管理
 * 
 * @author martin.zheng
 * 
 */
@SuppressLint("SimpleDateFormat")
public class LogManager
{
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");

	private static String getText()
	{
		Date date = new Date();
		String text = sdf.format(date);
		return text;
	}

	public static void writeLog(Throwable ex)
	{
		Writer result = new StringWriter();
		PrintWriter printWriter = new PrintWriter(result);
		StackTraceElement[] trace = ex.getStackTrace();
		StackTraceElement[] trace2 = new StackTraceElement[trace.length + 3];
		System.arraycopy(trace, 0, trace2, 0, trace.length);
		trace2[trace.length + 0] = new StackTraceElement("Android", "MODEL", android.os.Build.MODEL, -1);
		trace2[trace.length + 1] = new StackTraceElement("Android", "VERSION", android.os.Build.VERSION.RELEASE, -1);
		trace2[trace.length + 2] = new StackTraceElement("Android", "FINGERPRINT", android.os.Build.FINGERPRINT, -1);
		ex.setStackTrace(trace2);
		ex.printStackTrace(printWriter);
		String stacktrace = result.toString();
		printWriter.close();

		LogManager.writeLog(stacktrace);
	}

	public static void writeLog(String log)
	{
		CharSequence timestamp = getText();
		String filename = PathManager.dirError() + "/" + timestamp + ".txt";

		try
		{
			FileWriter fileWriter = new FileWriter(new File(filename), false);
			log = log.replace("\n\t", "\r\n");
			fileWriter.write(log);

			fileWriter.flush();
			fileWriter.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static String readLog(File file)
	{
		String result = "";

		try
		{
			if (file != null && file.exists())
			{
				InputStream instream = new FileInputStream(file);
				if (instream != null)
				{
					InputStreamReader inputreader = new InputStreamReader(instream, "utf8");
					BufferedReader buffreader = new BufferedReader(inputreader);
					String line;
					// 分行读取
					while ((line = buffreader.readLine()) != null)
					{
						result += line;
						result += "\r\n";
					}
					instream.close();
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

	public static void reportLog()
	{
		// String root = PathManager.dirError();
		// File dir = new File(root);
		// File[] files = dir.listFiles();
		// if (files != null && files.length > 0)
		// {
		// ReportLogList items = new ReportLogList();
		//
		// for (File file : files)
		// {
		// if (file != null && file.exists())
		// {
		// String content = readLog(file);
		// if (!TextUtils.isEmpty(content))
		// {
		// ReportLog item = new ReportLog();
		// item.content = content;
		// item.fileName = file.getName();
		// items.add(item);
		// }
		// }
		// }
		//
		// if (items.size() > 0)
		// {
		// if (reportLog(items))
		// {
		// for (File file : files)
		// {
		// PathManager.delete(file);
		// }
		// }
		// }
		// }
	}

	// public static boolean reportLog(ReportLogList items)
	// {
	// ReportLogRes result = null;
	//
	// try
	// {
	// ReportLogPara para = new ReportLogPara();
	// para.items = items;
	//
	// result = ServiceHandle.ReportLog(para);
	// }
	// catch (Exception e)
	// {
	// e.printStackTrace();
	// }
	// if (result != null && result.isSuccess)
	// {
	// return true;
	// }
	// return false;
	// }

	public static Object readObject(String path) throws Exception
	{
		if (!TextUtils.isEmpty(path))
		{
			return readObject(new File(path));
		}
		return null;
	}

	public static Object readObject(File file) throws Exception
	{
		Object obj = null;
		if (PathManager.exists(file))
		{
			ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(file));
			obj = objIn.readObject();
			objIn.close();
		}
		return obj;
	}

	public static void writeObject(Object obj, String path) throws Exception
	{
		if (obj != null && !TextUtils.isEmpty(path))
		{
			writeObject(obj, new File(path));
		}
	}

	public static void writeObject(Object obj, File file) throws Exception
	{
		ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(file));
		objOut.writeObject(obj);
		objOut.flush();
		objOut.close();
	}
}