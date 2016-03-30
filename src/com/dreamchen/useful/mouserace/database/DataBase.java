package com.dreamchen.useful.mouserace.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.dreamchen.useful.mouserace.PathManager;
import com.dreamchen.useful.mouserace.utils.LogUtils;

public class DataBase {
	private static SQLiteDatabase db;

	static {
		db = SQLiteDatabase.openOrCreateDatabase(PathManager.getDBPath()
				+ "mouserace.db", null);
	}

	public synchronized static void createTable(String sqlCreateTable) {
		try {
			db.execSQL(sqlCreateTable);
		} catch (Exception e) {
			LogUtils.Log_E(e.getMessage().toString());
		}
	}

	public synchronized static void excute(String sql) {
	public static void excute(String sql) {
		db.execSQL(sql);
	}
	
	public synchronized static void insert(String table,ContentValues values){
		db.insert(table, null, values);
	}
}
