package com.dreamchen.useful.mouserace.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import com.dreamchen.useful.mouserace.multithread.CopyThread;
import com.dreamchen.useful.mouserace.multithread.StateInterface;

public class FileUtils {
	
	/**
	 * 普通复制,带输出流缓存，比不带缓存快
	 * @param from
	 * @param to
	 * @param deleteSrc
	 */
	public static void copy_normal(File from ,File to,boolean deleteSrc){
		if(from==null||to ==null){
			return ;
		}
		long time  = System.nanoTime();
		FileInputStream fis = null;
		FileOutputStream fos =null;
		BufferedOutputStream bos = null;
		ByteBuffer buffer = ByteBuffer.allocate(256);
		byte [] bytes=new byte[256];
		try {
			fis=new FileInputStream(from);
			fos =new FileOutputStream(to);
			// 输出缓存
			bos =new BufferedOutputStream(fos);
			int byteCounts = 0;
			while((byteCounts=fis.read(bytes))>0){
				bos.write(bytes);
			}
			bos.flush();
			LogUtils.Log_E(String.format("elapsed time=%s", System.nanoTime()-time));
			if(deleteSrc){
				from.delete();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				fis.close();
				fos.close();
				bos.close();
			} catch (Exception e2) {
			}
		}
	}
	
	/**
	 * 高速复制 比方法{@link copy_normal}高出10倍（1.5m的图片） 
	 * @param from
	 * @param to
	 * @param deleteSrc
	 */
	public static void copy_chanel(File from ,File to,boolean deleteSrc){
		if(from==null||to ==null){
			return ;
		}
		long time = System.nanoTime();
		FileInputStream fis = null;
		FileOutputStream fos = null;
		FileChannel fc_in = null;
		FileChannel fc_out = null;
		try {
			fis = new FileInputStream(from);
			fos = new FileOutputStream(to);
			fc_in = fis.getChannel();
			fc_out = fos.getChannel();
			fc_in.transferTo(0, from.length(), fc_out);
			LogUtils.Log_E(String.format("elapsed time=%s", System.nanoTime()-time));
			if(deleteSrc){
				from.delete();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				fis.close();
				fos.close();
				fc_in.close();
				fc_out.close();
			} catch (Exception e2) {
			}
		}
	}
	
	public static void copy(File from ,File to,long begin,boolean deleteSrc){
		if(from==null||to ==null){
			return ;
		}
		long time = System.nanoTime();
		FileInputStream fis = null;
		RandomAccessFile fout = null;
		try {
			fis = new FileInputStream(from);
			fout = new RandomAccessFile(to, "rw");
			fout.seek(begin);
			ByteBuffer buffer = ByteBuffer.allocate(256);
			int byteCount  = 0;
			while((byteCount = fis.read(buffer.array()))>0){
				fout.write(buffer.array());
				buffer.clear();
			}
			LogUtils.Log_E(String.format("elapsed time=%s", System.nanoTime()-time));
			if(deleteSrc){
				from.delete();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				fis.close();
				fout.close();
			} catch (Exception e2) {
			}
		}
	}
	
	/**
	 * 线程数不能大于10
	 * @param threadCount
	 * @param fromFile
	 * @param toFile
	 * @return
	 */
	public static boolean  multi_copy(int threadCount,File fromFile,File toFile,StateInterface stateCallBack){
		if(fromFile==null||!fromFile.exists()){
			return false;
		}
		if(!toFile.exists()){
			try {
				toFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(threadCount>10){
			new IllegalArgumentException("the thread number more than 10 !");
		}
		
		long length = fromFile.length();
		long single_length = length/threadCount;
		//最后一块的大小
		long last_length =single_length;
		if(single_length*threadCount<length){
			last_length +=length-single_length*threadCount;
		}
		
		RandomAccessFile accessIn  = null;
		RandomAccessFile accessOut = null;
		long begin = 0;
		long end = single_length;
		try {
			for(int i=0;i<threadCount;i++){
				accessIn = new RandomAccessFile(fromFile,"r");
				accessOut = new RandomAccessFile(toFile,"rw");
				new CopyThread(accessIn,accessOut,begin,end,stateCallBack).start();
				begin = end;
				// 最后一块的大小
				if(i==threadCount-2){
					end += last_length;
				}else{
					end += single_length;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
}
