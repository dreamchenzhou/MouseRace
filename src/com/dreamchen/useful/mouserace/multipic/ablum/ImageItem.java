package com.dreamchen.useful.mouserace.multipic.ablum;

import java.io.Serializable;

/**
 * 
 * 	图片信息
 */
public class ImageItem implements Serializable{
	
	/**
	 * 图片的id
	 */
	private int id;
	
	/**
	 * 图片的名称
	 */
	private String name;
	
	/**
	 * 图片的缩略图
	 */
	private String thumbNail;
	
	/**
	 * 图片的物理路径
	 */
	private String path;

	/**
	 * 图片大小
	 */
	private long size;
	
	/**
	 * 创建日期
	 */
	private String date;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getThumbNail() {
		return thumbNail;
	}

	public void setThumbNail(String thumbNail) {
		this.thumbNail = thumbNail;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
}
