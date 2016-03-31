package com.dreamchen.useful.mouserace.bean;

public class ThreadBean extends BaseBean{
	
	private String task_uid;
	
	private String uri;
	
	private long begin_index;
	
	private long end_index;
	
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

	public long getBegin_index() {
		return begin_index;
	}

	public void setBegin_index(long begin_index) {
		this.begin_index = begin_index;
	}

	public long getEnd_index() {
		return end_index;
	}

	public void setEnd_index(long end_index) {
		this.end_index = end_index;
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
