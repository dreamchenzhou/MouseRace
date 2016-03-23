package com.dreamchen.useful.mouserace.multithread;

public interface StateInterface {
	/**
	 * 如果出错，会有出错的错误信息
	 * @param isSuccess
	 */
	public void setSuccess(boolean isSuccess);
	
	/**
	 * 处理的长度
	 * @param length
	 */
	public void  setLength(long length);
	
	/**
	 * 设置出错消息
	 * @param msg
	 */
	public void setMessage(String msg);
	
	/**
	 * 线程成功的数量
	 * @param num
	 */
	public void setSuccessCount(int num);
}
