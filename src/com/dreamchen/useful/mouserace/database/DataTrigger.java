package com.dreamchen.useful.mouserace.database;

public class DataTrigger {
	/**
	 * key word "new" is: the row after inserted or updated
	 * key word "old" is :the row after deleted ,or before update.
	 */
	/**
	 * 删除一项任务的触发器
	 */
	public static final String TRIGGER_AFTER_DELETE_TASK =
			" create trigger if not exists trigger_delete_on_task after delete on "+
					TaskTable.TABLE_NAME +
					" begin " +
						"delete from " +ThreadTable.TABLE_NAME+" where task_uid = old.uid; "+
					" end ";
	
	/**
	 * 完成一项thread的触发器
	 */
	public static final String TRIGGER_AFTER_UPDATE_THREAD =
			" create trigger if not exists trigger_update_on_thread after update on "+
					ThreadTable.TABLE_NAME +
					" begin " +
						" update " +TaskTable.TABLE_NAME+" set left_thread= left_thread-1  where new.task_uid = uid and new.finish = 1; "+
					" end ";
}
