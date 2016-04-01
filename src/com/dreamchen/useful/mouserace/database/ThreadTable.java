package com.dreamchen.useful.mouserace.database;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import com.dreamchen.useful.mouserace.bean.ThreadBean;
import com.dreamchen.useful.mouserace.utils.LogUtils;

public class ThreadTable extends TableBase {
	
	public static final String TABLE_NAME="t_thread";
	
	public static final String COLUMN_TASK_UID ="task_uid";
	/**
	 * 下载路径
	 */
	public static final String COLUMN_URI ="uri";
	/**
	 * 远程文件开始位置
	 */
	public static final String COLUMN_URI_BEGIN_INDEX ="uri_begin_index";
	/**
	 * 本地文件开始位置
	 */
	public static final String COLUMN_FILE_BEGIN_INDEX ="file_begin_index";
	/**
	 * 远程结束位置
	 */
	public static final String COLUMN_URI_END_INDEX ="uri_end_index";
	
	/**
	 * 本地文件结束位置
	 */
	public static final String COLUMN_FILE_END_INDEX ="file_end_index";
	
	/**
	 * 已经下载的长度
	 */
	public static final String COLUMN_LENGTH ="length";
	/**
	 * 负责的块的大小
	 */
	public static final String COLUMN_BLOCK_SIZE ="block_size";
	/**
	 * 临时文件路径
	 */
	public static final String COLUMN_TEMP_FILE_PATH ="temp_file_path";
	/**
	 * 目标文件dir
	 */
	public static final String COLUMN_FILE_PATH ="file_path";
	/**
	 * 目标文件的名称（全名）
	 */
	public static final String COLUMN_FILE_NAME ="file_name";
	
	/**
	 * 0 未完成，1完成, 2正在进行
	 */
	public static final String COLUMN_IS_FINISHED ="finish";
	
	public static final String CREATE_TABLE_SQL = CREATE_TABLE_PREFFIX+TABLE_NAME
			+ "("
			+ COLUMN_ID+" integer primary key autoincrement,"
			+ COLUMN_UID+" text unique,"
			+ COLUMN_TASK_UID+" text, "
			+ COLUMN_NAME+" text, "
			+ COLUMN_URI+" text,"
			+ COLUMN_URI_BEGIN_INDEX+" long, "
			+ COLUMN_FILE_BEGIN_INDEX +" long, "
			+ COLUMN_URI_END_INDEX +" long, "
			+ COLUMN_FILE_END_INDEX +" long, "
			+ COLUMN_LENGTH+" long, "
			+ COLUMN_BLOCK_SIZE+ " long, "
			+ COLUMN_TEMP_FILE_PATH+ " text, "
			+ COLUMN_FILE_PATH+ " text, "
			+ COLUMN_FILE_NAME+ " text, "
			+ COLUMN_IS_FINISHED+ " integer, "
			+ COLUMN_STATUES+" integer"
			+ ")";
	
	public synchronized static boolean insert(ThreadBean bean){
		boolean result = true;
		try {
			ContentValues values = new ContentValues();
			values.put(COLUMN_UID, bean.getUid());
			values.put(COLUMN_TASK_UID, bean.getTask_uid());
			values.put(COLUMN_NAME, bean.getName());
			values.put(COLUMN_URI, bean.getUri() );
			values.put(COLUMN_URI_BEGIN_INDEX, bean.getUri_begin_index());
			values.put(COLUMN_FILE_BEGIN_INDEX, bean.getFile_begin_index());
			values.put(COLUMN_URI_END_INDEX, bean.getUri_end_index());
			values.put(COLUMN_FILE_END_INDEX, bean.getFile_end_index());
			values.put(COLUMN_LENGTH, bean.getLength());
			values.put(COLUMN_BLOCK_SIZE, bean.getBlock_size());
			values.put(COLUMN_FILE_NAME, bean.getFile_name());
			values.put(COLUMN_FILE_PATH, bean.getFile_path() );
			values.put(COLUMN_TEMP_FILE_PATH, bean.getTemp_file_path() );
			values.put(COLUMN_IS_FINISHED, bean.getFinish());
			values.put(COLUMN_STATUES, bean.getStates());
			DataBase.insert(TABLE_NAME, values);
		} catch (Exception e) {
			result = false;
			LogUtils.Log_E(e.toString());
		}
		
		return result;
	}
	
	public synchronized static boolean update(String whereClause,String []whereClauses,ThreadBean bean){
		boolean result = true;
		try {
			ContentValues values = new ContentValues();
			if(!TextUtils.isEmpty(bean.getUid() ))
			values.put(COLUMN_UID, bean.getUid());
			if(!TextUtils.isEmpty(bean.getUid() ))
				values.put(COLUMN_TASK_UID, bean.getTask_uid());
			if(!TextUtils.isEmpty(bean.getName() ))
			values.put(COLUMN_NAME, bean.getName());
			if(!TextUtils.isEmpty(bean.getUri() ))
			values.put(COLUMN_URI, bean.getUri() );
			if(bean.getUri_begin_index()>0)
			values.put(COLUMN_URI_BEGIN_INDEX, bean.getUri_begin_index());
			if(bean.getFile_begin_index()>0)
				values.put(COLUMN_FILE_BEGIN_INDEX, bean.getFile_begin_index());
			if(bean.getUri_end_index()>0)
			values.put(COLUMN_URI_END_INDEX, bean.getUri_end_index());
			if(bean.getFile_end_index()>0)
				values.put(COLUMN_FILE_END_INDEX, bean.getFile_end_index());
			if(bean.getLength()>0)
			values.put(COLUMN_LENGTH, bean.getLength());
			if(bean.getBlock_size()>0)
			values.put(COLUMN_BLOCK_SIZE, bean.getBlock_size());
			if(!TextUtils.isEmpty(bean.getFile_name() ))
				values.put(COLUMN_FILE_NAME, bean.getFile_name());
			if (!TextUtils.isEmpty(bean.getTemp_file_path()))
				values.put(COLUMN_TEMP_FILE_PATH, bean.getTemp_file_path());
			if(!TextUtils.isEmpty(bean.getFile_path() ))
			values.put(COLUMN_FILE_PATH, bean.getFile_path() );
			values.put(COLUMN_IS_FINISHED, bean.getFinish());
			if(bean.getStates()>0)
			values.put(COLUMN_STATUES, bean.getStates());
			DataBase.update(TABLE_NAME, values, whereClause, whereClauses);
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
			values.put(COLUMN_TASK_UID, bean.getTask_uid());
			values.put(COLUMN_NAME, bean.getName());
			values.put(COLUMN_URI, bean.getUri() );
			values.put(COLUMN_URI_BEGIN_INDEX, bean.getUri_begin_index());
			values.put(COLUMN_FILE_BEGIN_INDEX, bean.getFile_begin_index());
			values.put(COLUMN_URI_END_INDEX, bean.getUri_end_index());
			values.put(COLUMN_FILE_END_INDEX, bean.getFile_end_index());
			values.put(COLUMN_LENGTH, bean.getLength());
			values.put(COLUMN_BLOCK_SIZE, bean.getBlock_size());
			values.put(COLUMN_FILE_NAME, bean.getFile_name());
			values.put(COLUMN_FILE_PATH, bean.getFile_path() );
			values.put(COLUMN_TEMP_FILE_PATH, bean.getTemp_file_path() );
			values.put(COLUMN_IS_FINISHED, bean.getFinish());
			values.put(COLUMN_STATUES, bean.getStates());
			DataBase.insert(TABLE_NAME, values);
		} catch (Exception e) {
			result = false;
			LogUtils.Log_E(e.toString());
		}
		return result;
	}
	
	public synchronized static void insertList(List<ThreadBean> beans){ 
		for (ThreadBean threadBean : beans) {
			singleInsert(threadBean);
		}
	}
	
	public synchronized static List<ThreadBean> query(String selection,String [] selectionArgs){
		List<ThreadBean> result  =new ArrayList<ThreadBean>();
		Cursor cursor = null;
		String sql ="select *  from t_thread where uid =?";
		try {
			cursor  = DataBase.rawQuery(sql, selectionArgs);
			cursor.moveToFirst();
			while (cursor.getCount()!=cursor.getPosition()) {
				ThreadBean bean  =new ThreadBean();
				bean.setUid(cursor.getString(cursor.getColumnIndex(COLUMN_UID)));	
				bean.setTask_uid(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_UID)));	
				bean.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));	
				bean.setUri(cursor.getString(cursor.getColumnIndex(COLUMN_URI)));	
				
				bean.setUri_begin_index(cursor.getLong(cursor.getColumnIndex(COLUMN_URI_BEGIN_INDEX)));
				bean.setFile_begin_index(cursor.getLong(cursor.getColumnIndex(COLUMN_FILE_BEGIN_INDEX)));
				bean.setUri_end_index(cursor.getLong(cursor.getColumnIndex(COLUMN_URI_END_INDEX)));
				bean.setFile_end_index(cursor.getLong(cursor.getColumnIndex(COLUMN_FILE_END_INDEX)));
				bean.setLength(cursor.getLong(cursor.getColumnIndex(COLUMN_LENGTH)));
				bean.setBlock_size(cursor.getLong(cursor.getColumnIndex(COLUMN_BLOCK_SIZE)));
				
				bean.setTemp_file_path(cursor.getString(cursor.getColumnIndex(COLUMN_TEMP_FILE_PATH)));
				bean.setFile_name(cursor.getString(cursor.getColumnIndex(COLUMN_FILE_NAME)));	
				bean.setFile_path(cursor.getString(cursor.getColumnIndex(COLUMN_FILE_PATH)));	
				bean.setFinish(cursor.getInt(cursor.getColumnIndex(COLUMN_IS_FINISHED)));	
				bean.setStates(cursor.getInt(cursor.getColumnIndex(COLUMN_STATUES)));	
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
