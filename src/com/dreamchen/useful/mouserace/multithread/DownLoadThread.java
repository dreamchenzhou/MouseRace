package com.dreamchen.useful.mouserace.multithread;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.UUID;

import com.dreamchen.useful.mouserace.utils.LogUtils;

import android.util.Log;

public class DownLoadThread extends Thread {
	
	private String uri;
	
	private String toDir;
	
	private long begin;
	
	private long end;
	
	private long hasDown;
	
	private DownLoadInterface stateInterface;
	private String threadId;
	private String threadName;
	private String desFileName;
	/**
	 * 
	 * @param uri 下载文件的uri
	 * @param toDir 目的路径文件的dir
	 * @param threadId 线程的id
	 * @param threadName 线程的名称
	 * @param desFileName 目的文件的全名，不包含路径！！！
	 * @param begin 下载的开始位置，或者临时文件拷贝到目的文件的开始位置
	 * @param end 下载的结束位置，或者临时文件拷贝到目的文件的结束位置
	 * @param stateInterface 回调接口
	 */
	public DownLoadThread(String uri,String toDir,String threadId,String threadName,String desFileName,long hasDown,long begin, long end,DownLoadInterface stateInterface){
		this.uri = uri;
		this.toDir = toDir;
		this.threadId =threadId;
		this.threadName = threadName;
		this.desFileName = desFileName;
		this.begin = begin;
		this.end =end;
		this.hasDown = hasDown;
		this.stateInterface =stateInterface;
	}
	
	@Override
	public void run() {
		RandomAccessFile file_out = null;
		FileChannel channel_out = null;
		InputStream is= null;
		File file = null;
		long byteCount  =0;
		long totalCount = 0;
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
			file =new File(toDir+File.separator+threadName);
			if(!file.exists()){
				file.createNewFile();
			}
			is  =connection.getInputStream();
			file_out = new RandomAccessFile(file, "rw");
//			file_out.seek(hasDown);
			channel_out = file_out.getChannel();
			ByteBuffer buffer  = ByteBuffer.allocate(1024);
			while((byteCount = is.read(buffer.array()))>0){
				// 暂停等待
				if(stateInterface.isPause()){
					while (stateInterface.isPause()) {
						Thread.sleep(1000);
					}
				}
				// 停止下载
				if(stateInterface.isStop()){
					break;
				}
				channel_out.write(buffer,hasDown);
				hasDown+=byteCount;
				totalCount+=byteCount;
				buffer.clear();
 			}
			if(!stateInterface.isStop()){
				stateInterface.setLength(totalCount);
				LogUtils.Log_E("filesize:"+file.length());
				stateInterface.setId(threadId);
				stateInterface.setCoypFactor(file.getAbsolutePath(),toDir+File.separator+desFileName, begin, end);
				stateInterface.setSuccessCount(1);
			}else{
				stateInterface.setMessage("线程被停止！！！");
				stateInterface.setSuccess(false);
			}
		} catch (Exception e) {
			stateInterface.setMessage(e.getMessage());
			stateInterface.setSuccess(false);
			if(!stateInterface.isStop()){
				stateInterface.setInterrupt(file.getAbsolutePath(),threadName,threadName, totalCount, begin+totalCount, end);
				stateInterface.setFailedThreadId(threadId);
			}
		}finally{
			try {
				is.close();
				channel_out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
