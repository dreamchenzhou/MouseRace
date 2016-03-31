package com.dreamchen.useful.mouserace.multithread;

public interface DownLoadInterface extends StateInterface{
	public void setCoypFactor(String tempFile,String desFile,long desBegin,long desEnd);
	/***
	 * 断点续传
	 * @param tempFile
	 * @param downLength
	 * @param beging
	 * @param end
	 */
	public void setInterrupt(String tempFile,String threadId,String threadName,long downLength,long begin,long end);
	
	public void setFailedThreadId(String id);
	
	public boolean isPause();
	
	public boolean isStop();
}
