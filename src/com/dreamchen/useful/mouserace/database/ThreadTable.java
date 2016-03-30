package com.dreamchen.useful.mouserace.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;

import com.dreamchen.useful.mouserace.bean.ThreadBean;
import com.dreamchen.useful.mouserace.utils.LogUtils;

public class ThreadTable extends TableBase {
	
	public static final String TABLE_NAME="t_thread";
	
	public static final String COLUMN_URI =" uri ";
	
	
	public static final String COLUMN_BEGIN_INDEX =" begin_index ";
	
	public static final String COLUMN_END_INDEX =" end_index ";
	
	public static final String COLUMN_LENGTH =" length ";
	
	public static final String COLUMN_BLOCK_SIZE =" block_size ";
	
	public static final String COLUMN_FILE_PATH =" file_path ";
	
	public static final String COLUMN_FILE_NAME =" file_name ";
	
	public static final String COLUMN_IS_FINISHED =" is_finished ";
	
	public static final String CREATE_TABLE_SQL = CREATE_TABLE_PREFFIX+TABLE_NAME
			+ "("
			+ COLUMN_ID+" integer primary key autoincrement"
			+ COLUMN_UID+" text "
			+ COLUMN_NAME+" text "
			+ COLUMN_URI+" text "
			+ COLUMN_BEGIN_INDEX+" long "
			+ COLUMN_END_INDEX +" long "
			+ COLUMN_LENGTH+" long "
			+ COLUMN_BLOCK_SIZE+ " long "
			+ COLUMN_FILE_PATH+ " text "
			+ COLUMN_FILE_NAME+ " text "
			+ COLUMN_IS_FINISHED+ "integer"
			+ ")";
	
	public synchronized static boolean Insert(ThreadBean bean){
		boolean result = true;
		try {
			ContentValues values = new ContentValues();
			values.put(COLUMN_UID, bean.getUid());
			values.put(COLUMN_NAME, bean.getName());
			values.put(COLUMN_URI, bean.getUri() );
			values.put(COLUMN_BEGIN_INDEX, bean.getBegin_index());
			values.put(COLUMN_END_INDEX, bean.getEnd_index());
			values.put(COLUMN_LENGTH, bean.getLength());
			values.put(COLUMN_BLOCK_SIZE, bean.getBlock_size());
			values.put(COLUMN_FILE_NAME, bean.getFile_name());
			values.put(COLUMN_FILE_PATH, bean.getFile_path() );
			values.put(COLUMN_IS_FINISHED, bean.isIs_finished());
			values.put(COLUMN_STATUES, bean.getStates());
			DataBase.insert(TABLE_NAME, values);
		} catch (Exception e) {
			result = false;
			LogUtils.Log_E(e.toString());
		}
		
		return result;
	}
	
	private static boolean singleInsert(ThreadBean bean){
		boolean result = true;
		try {
			ContentValues values = new ContentValues();
			values.put(COLUMN_UID, bean.getUid());
			values.put(COLUMN_NAME, bean.getName());
			values.put(COLUMN_URI, bean.getUri() );
			values.put(COLUMN_BEGIN_INDEX, bean.getBegin_index());
			values.put(COLUMN_END_INDEX, bean.getEnd_index());
			values.put(COLUMN_LENGTH, bean.getLength());
			values.put(COLUMN_BLOCK_SIZE, bean.getBlock_size());
			values.put(COLUMN_FILE_NAME, bean.getFile_name());
			values.put(COLUMN_FILE_PATH, bean.getFile_path() );
			values.put(COLUMN_IS_FINISHED, bean.isIs_finished());
			values.put(COLUMN_STATUES, bean.getStates());
			DataBase.insert(TABLE_NAME, values);
		} catch (Exception e) {
			result = false;
			LogUtils.Log_E(e.toString());
		}
		
		return result;
	}
	
	private synchronized static void insertList(List<ThreadBean> beans){ 
		for (ThreadBean threadBean : beans) {
			singleInsert(threadBean);
		}
	}
	
	public synchronized static List<ThreadBean> query(String selection,String [] selectionArgs){
		List<ThreadBean> result  =new ArrayList<ThreadBean>();
		Cursor cursor = null;
		try {
			cursor  = DataBase.query(TABLE_NAME, selection, selectionArgs);
			//TODO
			
		} catch (Exception e) {
			
		}finally{
			if(cursor!=null&&!cursor.isClosed()){
				cursor.close();
			}
		}
		
		return result;
	}
}
