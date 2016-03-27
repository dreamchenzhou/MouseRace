package com.dreamchen.useful.mouserace.database;

public class Table {
	public static final String TABLE_NAME_TEST = "test";
	
	
	public static final String TABLE_TEST_COLUME_ID = "id";
	
	public static final String TABLE_TEST_COLUME_NAME = "name";
	
	public static final String TABLE_TEST_COLUME_CONTENT = "content";
	
	public static final String TABLE_TEST_COLUME_COUNT = "count";
	
	public static final String CREATE_TABLE_TEST="create table if not exists "
			+ TABLE_NAME_TEST+"("
			+ TABLE_TEST_COLUME_ID+" integer primary key autoincrement, "
			+ TABLE_TEST_COLUME_NAME+" text,"
			+ TABLE_TEST_COLUME_CONTENT+" text,"
			+ TABLE_TEST_COLUME_COUNT+" integer"+
			")";
	
}
