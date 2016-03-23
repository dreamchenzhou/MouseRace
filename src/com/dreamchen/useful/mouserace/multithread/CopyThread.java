package com.dreamchen.useful.mouserace.multithread;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public class CopyThread extends Thread{
	private RandomAccessFile fin = null;
	private RandomAccessFile fout =null;
	private long begin = 0;
	private long end =0;
	private StateInterface stateCallBack; 
	private int bufferSize = 1024;
	/**
	 * 每次传进来的 fin 可以是同一个对象，但是fout必须是同一个文件地址的不同对象！！！
	 * @param fin
	 * @param fout
	 * @param begin
	 * @param end
	 * @param stateCallBack
	 */
	public CopyThread(RandomAccessFile fin,RandomAccessFile fout,long begin,long end ,StateInterface stateCallBack) {
		this.fin = fin;
		this.fout = fout;
		this.begin = begin;
		this.end = end;
		this.stateCallBack = stateCallBack;
	}
	
	@Override
	public void run() {
		try {
			ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
			int total =0;
			int byteLength = 0;
			fin.skipBytes((int) begin);
			fout.skipBytes((int) begin);
			while((byteLength = fin.read(buffer.array()))>0){
				total+=byteLength;
				fout.write(buffer.array());
				buffer.clear();
			}
			stateCallBack.setLength(total);
			stateCallBack.setSuccessCount(1);
		} catch (Exception e) {
			e.printStackTrace();
			stateCallBack.setMessage(e.getMessage());
			stateCallBack.setSuccess(false);
		}finally{
			try {
				fin.close();
				fout.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
