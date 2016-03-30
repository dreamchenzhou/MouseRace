package com.dreamchen.useful.mouserace.database;

public class ThreadTable extends TableBase {
	
	public static final String TABLE_NAME="t_thread";
	
	public static final String COLUMN_URI ="uri";
	
	
	public static final String COLUMN_BEGIN_INDEX ="begin_index";
	
	public static final String COLUMN_END_INDEX ="end_index";
	
	public static final String COLUMN_LENGTH ="length";
	
	public static final String COLUMN_BLOCK_SIZE ="block_size";
	
	public static final String COLUMN_FILE_PATH ="file_path";
	
	public static final String COLUMN_FILE_NAME ="file_name";
	
	public static final String COLUMN_IS_FINISHED ="is_finished";
	
	public static final String CREATE_TABLE_SQL = CREATE_TABLE_PREFFIX+TABLE_NAME
			+ "("
			+ COLUMN_ID+""
			+ COLUMN_UID+""
			+ COLUMN_NAME+""
			+ ""
			+ ""
			+ ""
			+ ""
			+ ")";
}
