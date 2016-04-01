package com.dreamchen.useful.mouserace.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import com.dreamchen.useful.mouserace.bean.TaskBean;
import com.dreamchen.useful.mouserace.utils.LogUtils;

public class TaskTable extends TableBase{
	public static final String TABLE_NAME="t_task";
	
	/**
	 * 0 未完成，1完成, 2正在进行
	 */
	public static final String COLUMN_IS_FINISHED ="finish";
	
	/**
	 * 未完成的线程
	 */
	public static final String COLUMN_LEFT_THREAD = "left_thread";
	
	public static final String CREATE_TABLE_SQL = CREATE_TABLE_PREFFIX+TABLE_NAME
			+ "("
			+ COLUMN_ID+" integer primary key autoincrement,"
			+ COLUMN_UID+" text unique,"
			+ COLUMN_NAME+" text, "
			+ COLUMN_IS_FINISHED+ " integer,"
			+ COLUMN_LEFT_THREAD+ " integer,"
			+ COLUMN_STATUES+" integer "
			+ ")";
	
	public synchronized static boolean insert(TaskBean bean){
		boolean result = true;
		try {
			ContentValues values = new ContentValues();
			values.put(COLUMN_UID, bean.getUid());
			values.put(COLUMN_NAME, bean.getName());
			values.put(COLUMN_IS_FINISHED, bean.getFinish());
			values.put(COLUMN_STATUES, bean.getStates());
			values.put(COLUMN_LEFT_THREAD, bean.getLeft_thread());
			DataBase.insert(TABLE_NAME, values);
		} catch (Exception e) {
			result = false;
			LogUtils.Log_E(e.toString());
		}
		
		return result;
	}
	
	public synchronized static boolean update(String whereClause,String []whereClauses,TaskBean bean){
		boolean result = true;
		try {
			ContentValues values = new ContentValues();
			if(!TextUtils.isEmpty(bean.getUid() ))
			values.put(COLUMN_UID, bean.getUid());
			if(!TextUtils.isEmpty(bean.getName() ))
			values.put(COLUMN_NAME, bean.getName());
			values.put(COLUMN_IS_FINISHED, bean.getFinish());
			values.put(COLUMN_STATUES, bean.getStates());
			values.put(COLUMN_LEFT_THREAD, bean.getLeft_thread());
			DataBase.update(TABLE_NAME, values, whereClause, whereClauses);
		} catch (Exception e) {
			result = false;
			LogUtils.Log_E(e.toString());
		}
		return result;
	}
	
	private static boolean singleInsert(TaskBean bean){
		boolean result = true;
		try {
			ContentValues values = new ContentValues();
			values.put(COLUMN_UID, bean.getUid());
			values.put(COLUMN_NAME, bean.getName());
			values.put(COLUMN_IS_FINISHED, bean.getFinish());
			values.put(COLUMN_STATUES, bean.getStates());
			values.put(COLUMN_LEFT_THREAD, bean.getLeft_thread());
			DataBase.insert(TABLE_NAME, values);
		} catch (Exception e) {
			result = false;
			LogUtils.Log_E(e.toString());
		}
		return result;
	}
	
	public synchronized static void insertList(List<TaskBean> beans){ 
		for (TaskBean taskBean : beans) {
			singleInsert(taskBean);
		}
	}
	
	public synchronized static List<TaskBean> query(String selection,String [] selectionArgs){
		List<TaskBean> result  =new ArrayList<TaskBean>();
		Cursor cursor = null;
		String []colums = new String[]{COLUMN_UID,COLUMN_NAME,COLUMN_IS_FINISHED,
				COLUMN_STATUES,COLUMN_LEFT_THREAD};
		try {
			cursor  = DataBase.query(TABLE_NAME,colums, selection, selectionArgs);
			cursor.moveToFirst();
			while (cursor.getCount()!=cursor.getPosition()) {
				TaskBean bean  =new TaskBean();
				bean.setUid(cursor.getString(cursor.getColumnIndex(COLUMN_UID)));	
				bean.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));	
				bean.setFinish(cursor.getInt(cursor.getColumnIndex(COLUMN_IS_FINISHED)));	
				bean.setStates(cursor.getInt(cursor.getColumnIndex(COLUMN_STATUES)));	
				bean.setLeft_thread(cursor.getInt(cursor.getColumnIndex(COLUMN_LEFT_THREAD)));	
				result.add(bean);
				cursor.moveToNext();
			}
		} catch (Exception e) {
			
		}finally{
			if(cursor!=null&&!cursor.isClosed()){
				cursor.close();
			}
		}
		return result;
	}
}
