package com.dreamchen.useful.mouserace.database;

import com.dreamchen.useful.mouserace.utils.LogUtils;

import android.content.ContentValues;
import android.util.Log;

public class TestDB {
	
	private static Object syc_object = new Object();
	
	static{
		DataBase.createTable(Table.CREATE_TABLE_TEST);
	}
	

	public static boolean insert(Test test){
		boolean isSuccess = true;
		LogUtils.Log_E("WaitThread:"+Thread.currentThread().getName());
//		synchronized (syc_object) {
//			try {
//				Thread.sleep(3000);
//			} catch (InterruptedException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
			LogUtils.Log_E("runThread---"+
		    Thread.currentThread().getName()+"-----currentTime---"+
					System.currentTimeMillis()+"-----");
			ContentValues values = new ContentValues();
			values.put(Table.TABLE_TEST_COLUME_NAME, test.getName());
			values.put(Table.TABLE_TEST_COLUME_CONTENT, test.getContent());
			values.put(Table.TABLE_TEST_COLUME_COUNT, test.getCount());
			try {
				DataBase.insert(Table.TABLE_NAME_TEST, values);
			} catch (Exception e) {
				isSuccess = false;
			}
//		}
		return isSuccess;
	}
}
