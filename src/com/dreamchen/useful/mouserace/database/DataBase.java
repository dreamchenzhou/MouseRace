package com.dreamchen.useful.mouserace.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dreamchen.useful.mouserace.PathManager;
import com.dreamchen.useful.mouserace.utils.LogUtils;

public class DataBase {
	private static SQLiteDatabase db;

	static {
		db = SQLiteDatabase.openOrCreateDatabase(PathManager.getDBPath()
				+ "mouserace.db", null);
	}

	public static void createTable(String sqlCreateTable) {
		try {
			db.execSQL(sqlCreateTable);
		} catch (Exception e) {
			LogUtils.Log_E(e.getMessage().toString());
		}
	}

	public static void excute(String sql) {
		db.execSQL(sql);
	}
	
	public static void insert(String table,ContentValues values){
		db.insert(table, null, values);
	}
	
	public static Cursor query(String table,String [] colums,String selection,String [] selectionArgs){
		return db.query(table, colums, selection, selectionArgs, null, null, null);
	}
	
	public static Cursor rawQuery(String sql,String []selectionArgs ){
		return db.rawQuery(sql, selectionArgs);
	}
	
	public static void update(String table,ContentValues values,String whereClause,String [] whereClauses){
		db.update(table, values, whereClause, whereClauses);
	}
}
