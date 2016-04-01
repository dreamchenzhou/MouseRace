package com.dreamchen.useful.mouserace.bean;

import com.dreamchen.useful.mouserace.bean.BaseBean;

public class TaskBean extends BaseBean{

	/**
	 * 0 未完成，1完成, 2正在进行
	 */
	private int finish;
	
	private int left_thread;

	public int getFinish() {
		return finish;
	}

	public void setFinish(int finish) {
		this.finish = finish;
	}

	public int getLeft_thread() {
		return left_thread;
	}

	public void setLeft_thread(int left_thread) {
		this.left_thread = left_thread;
	}
	
}
