package com.dreamchen.useful.mouserace.multithread;

public interface StateInterface extends BaseInterface {
	
	/**
	 * 处理的长度
	 * @param length
	 */
	public void  setLength(long length);
	
	
	/**
	 * 线程成功的数量
	 * @param num
	 */
	public void setSuccessCount(int num);
}
