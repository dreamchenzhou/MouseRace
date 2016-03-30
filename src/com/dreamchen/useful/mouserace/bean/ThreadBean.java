package com.dreamchen.useful.mouserace.bean;

public class ThreadBean extends BaseBean{
	private String uri;
	
	private long begin_index;
	
	private long end_index;
	
	private long length;
	
	private long block_size;
	
	private String file_path;
	
	private String file_name;
	
	private boolean is_finished;

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

	public boolean isIs_finished() {
		return is_finished;
	}

	public void setIs_finished(boolean is_finished) {
		this.is_finished = is_finished;
	}

}
