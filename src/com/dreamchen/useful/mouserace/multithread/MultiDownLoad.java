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


public class MultiDownLoad extends FileBase{
	
	public static void DownFile(int threadCount,String uri,String toDir,String name,DownLoadInterface stateInterface){
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
				for(int i=0;i<threadCount;i++){
					//
					new DownLoadThread(uri, toDir, begin, end, i+1, stateInterface).start();
					begin = end;
					if(i==(threadCount-2)){
						end = begin+lastBlockLength;
					}else{
						end = begin+singleLength;
					}
				}
			}else{
				new SingleDownLoadThread(uri, toDir, name, stateInterface).start();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
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
