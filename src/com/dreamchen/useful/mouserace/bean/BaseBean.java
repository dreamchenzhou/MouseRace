package com.dreamchen.useful.mouserace.bean;

import java.io.Serializable;

import android.R.integer;

public class BaseBean implements Serializable {
	private int id;
	
	private String name;
	
	private String uid;
	
	private int states;

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

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public int getStates() {
		return states;
	}

	public void setStates(int states) {
		this.states = states;
	}
	
}
