package com.dreamchen.useful.mouserace.bean;

public class ThreadBean extends BaseBean{
	
	private String task_uid;
	
	private String uri;
	/**
	 * 每一块
	 * 远程的文件开始下载位置
	 */
	private long uri_begin_index;
	
	/**
	 * 每一块
	 * 本地文件的开始下载地址
	 */
	private long file_begin_index;
	/**
	 * 每一块
	 * 远程的文件终点下载位置
	 */
	private long uri_end_index;
	
	/**
	 * 每一块
	 * 本地的文件终点下载位置
	 */
	private long file_end_index;
	/**
	 * 已经下载的长度
	 */
	private long length;
	
	private long block_size;
	
	private String temp_file_path;
	
	private String file_path;
	
	private String file_name;
	/**
	 * 0 未完成，1完成, 2正在进行
	 */
	private int finish;

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public long getUri_begin_index() {
		return uri_begin_index;
	}

	public void setUri_begin_index(long uri_begin_index) {
		this.uri_begin_index = uri_begin_index;
	}

	public long getFile_begin_index() {
		return file_begin_index;
	}

	public void setFile_begin_index(long file_begin_index) {
		this.file_begin_index = file_begin_index;
	}

	public long getUri_end_index() {
		return uri_end_index;
	}

	public void setUri_end_index(long uri_end_index) {
		this.uri_end_index = uri_end_index;
	}

	public long getFile_end_index() {
		return file_end_index;
	}

	public void setFile_end_index(long file_end_index) {
		this.file_end_index = file_end_index;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public long getBlock_size() {
		return block_size;
	}

	public void setBlock_size(long block_size) {
		this.block_size = block_size;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public int getFinish() {
		return finish;
	}

	public void setFinish(int finish) {
		this.finish = finish;
	}

	public String getTask_uid() {
		return task_uid;
	}

	public void setTask_uid(String task_uid) {
		this.task_uid = task_uid;
	}

	public String getTemp_file_path() {
		return temp_file_path;
	}

	public void setTemp_file_path(String temp_file_path) {
		this.temp_file_path = temp_file_path;
	}
}
