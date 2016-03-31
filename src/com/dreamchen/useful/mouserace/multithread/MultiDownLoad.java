package com.dreamchen.useful.mouserace.multithread;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.util.Random;
import java.util.UUID;

import com.dreamchen.useful.mouserace.bean.ThreadBean;
import com.dreamchen.useful.mouserace.database.TaskBean;
import com.dreamchen.useful.mouserace.database.TaskTable;
import com.dreamchen.useful.mouserace.database.ThreadTable;


public class MultiDownLoad extends FileBase{
	
	public static void DownFile(int threadCount,String uri,String toDir,String desFileName,DownLoadInterface stateInterface){
		try {
			URL url = new URL(uri);
			URLConnection connection =  url.openConnection();
			long length = connection.getContentLength();
			if(length>10*1024*1024){
				//TODO 多线程
				if(threadCount>10){
					threadCount =10;
				}
				long singleLength = length/threadCount;
				long lastBlockLength = singleLength;
				if(singleLength*threadCount<length){
					lastBlockLength = length-(threadCount-1)*singleLength;
				}
				long begin =0;
				long end = singleLength;
				String taskId = UUID.randomUUID().toString();
				TaskBean taskBean = new TaskBean();
				taskBean.setUid(taskId);
				taskBean.setLeft_thread(threadCount);
				taskBean.setFinish(2);
				taskBean.setName(taskId);
				TaskTable.insert(taskBean);
				for(int i=0;i<threadCount;i++){
					String threadId = UUID.randomUUID().toString();
					ThreadBean threadBean = new ThreadBean();
					threadBean.setUid(threadId);
					threadBean.setTask_uid(taskId);
					threadBean.setUri(uri);
					threadBean.setName(threadId);
					threadBean.setBegin_index(begin);
					threadBean.setEnd_index(end);
					threadBean.setFile_path(toDir);
					threadBean.setFile_name(desFileName);
					threadBean.setTemp_file_path(toDir+File.separator+threadId);
					ThreadTable.insert(threadBean);
					new DownLoadThread(uri, toDir,threadId,
							threadId,desFileName,
							begin, end, stateInterface).start();
					begin = end;
					if(i==(threadCount-2)){
						end = begin+lastBlockLength;
					}else{
						end = begin+singleLength;
					}
				}
			}else{
				new SingleDownLoadThread(uri, toDir, desFileName, stateInterface).start();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * 单线程下载
	 */
	private static class SingleDownLoadThread extends Thread{
		private String uri;
		
		private String toDir;
		
		private String name;
		
		private StateInterface stateInterface;
		
		public SingleDownLoadThread(String uri,String toDir,String name,StateInterface stateInterface){
			this.uri = uri;
			this.toDir = toDir;
			this.name = name;
			this.stateInterface =stateInterface;
		}
		
		@Override
		public void run() {
			InputStream is = null;
			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;
			try {
				URL url = new URL(uri);
				URLConnection connection =  url.openConnection();
				long length = connection.getContentLength();
				// 单线程
				File dir = new File(toDir);
				if(!dir.exists()){
					dir.mkdirs();
				}
				File file = new File(dir+File.separator+name);
				if(!file.exists()){
					file.createNewFile();
				}
				ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE_MIDDLE);
				int byteCount= 0;
				connection.setDoInput(true);
				 is = connection.getInputStream();
				 bis = new BufferedInputStream(is);
				bos = new BufferedOutputStream(new FileOutputStream(file));
				while((byteCount=bis.read(buffer.array()))>0){
					bos.write(buffer.array());
					buffer.clear();
					stateInterface.setLength(byteCount);
				}
				bos.flush();
				stateInterface.setSuccess(true);
			}catch(Exception e){
				stateInterface.setSuccess(false);
				stateInterface.setMessage(e.getMessage());
			}finally{
				try {
					is.close();
					bos.close();
				} catch (Exception e2) {
				}
			}
		}
	}
}
