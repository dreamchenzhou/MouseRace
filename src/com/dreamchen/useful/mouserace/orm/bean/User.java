package com.dreamchen.useful.mouserace.orm.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName="tb_user")
public class User {

	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(columnName="name")
	private String name;
	
	@DatabaseField(columnName="age")
	private int age;
	
	@DatabaseField(columnName="sex")
	private byte sex;

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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public byte getSex() {
		return sex;
	}

	public void setSex(byte sex) {
		this.sex = sex;
	}
	
	
	@Override
	public String toString() {
		return name;
	}
}
