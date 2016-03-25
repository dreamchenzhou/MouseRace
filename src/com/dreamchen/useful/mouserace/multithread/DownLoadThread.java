package com.dreamchen.useful.mouserace.multithread;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.util.UUID;

import com.dreamchen.useful.mouserace.utils.LogUtils;

import android.util.Log;

public class DownLoadThread extends Thread {
	
	private String uri;
	
	private String toDir;
	
	private long begin;
	
	private long end;
	
	private DownLoadInterface stateInterface;
	private int threadId;
	private String threadName;
	public DownLoadThread(String uri,String toDir,long begin, long end,int threadId,DownLoadInterface stateInterface){
		this.uri = uri;
		this.toDir = toDir;
		this.begin = begin;
		this.end =end;
		this.threadId =threadId;
		this.stateInterface =stateInterface;
		threadName =UUID.randomUUID().toString();
	}
	
	@Override
	public void run() {
		BufferedOutputStream bos  =null;
		InputStream is= null;
		try {
			URL url =new URL(uri);
			URLConnection connection =url.openConnection();
			connection.setAllowUserInteraction(true);
			connection.setRequestProperty("Range", "bytes="+begin+"-"+end);
			connection.setDoInput(true);
			File dir = new File(toDir);
			if(!dir.exists()){
				dir.mkdirs();
			}
			File file =new File(toDir+File.separator+threadName);
			if(!file.exists()){
				file.createNewFile();
			}
			is  =connection.getInputStream();
			bos= new BufferedOutputStream(new FileOutputStream(file));
			ByteBuffer buffer  = ByteBuffer.allocate(1024);
			long byteCount  =0;
			long totalCount = 0;
			while((byteCount = is.read(buffer.array()))>0){
				bos.write(buffer.array(),0,(int) byteCount);
				totalCount+=byteCount;
				bos.flush();
				buffer.clear();
 			}
			stateInterface.setLength(totalCount);
			LogUtils.Log_E("filesize:"+file.length());
			stateInterface.setId(threadId);
			stateInterface.setCoypFactor(threadName, begin, end);
			stateInterface.setSuccessCount(1);
		} catch (Exception e) {
			stateInterface.setMessage(e.getMessage());
			stateInterface.setSuccess(false);
		}finally{
			try {
				is.close();
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
