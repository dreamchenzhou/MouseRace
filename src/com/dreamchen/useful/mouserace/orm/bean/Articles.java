package com.dreamchen.useful.mouserace.orm.bean;

import android.os.Trace;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="tb_articles")
public class Articles {

	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(columnName ="name")
	private String name;
	
	@DatabaseField(columnName ="content")
	private String content;
	
	@DatabaseField(canBeNull = true,foreign = true,columnName ="user_id" ,foreignAutoRefresh =true)
	private User user;

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "用户名："+user.getName()+"  文件名："+name+"  文件内容："+ content;
	}
}
