package com.dreamchen.useful.mouserace.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.dreamchen.useful.mouserace.PathManager;
import com.dreamchen.useful.mouserace.R;
import com.dreamchen.useful.mouserace.base.BaseActivity;
import com.dreamchen.useful.mouserace.bean.ThreadBean;
import com.dreamchen.useful.mouserace.database.DataBase;
import com.dreamchen.useful.mouserace.database.DataTrigger;
import com.dreamchen.useful.mouserace.database.TaskTable;
import com.dreamchen.useful.mouserace.database.ThreadTable;
import com.dreamchen.useful.mouserace.download.DownloadUtils;
import com.dreamchen.useful.mouserace.multithread.DownLoadInterface;
import com.dreamchen.useful.mouserace.multithread.DownLoadThread;
import com.dreamchen.useful.mouserace.multithread.MultiDownLoad;
import com.dreamchen.useful.mouserace.utils.FileUtils;
import com.dreamchen.useful.mouserace.utils.LogUtils;
import com.dreamchen.useful.mouserace.utils.ToastUtils;

/**
 * 
 * @现存bug:(1).有时候线程正常完成，apk没有拼接完整
 * (2).线程因网络状态不好，或者其他非人为pause的话，要重试5次，5次之后如果还不成功的话，把断点信息存入数据库，以便重新下载
 *
 */
public class TestMultiDownLoadActivity extends BaseActivity implements OnClickListener {
	private Button btn_copy;
	private Button btn_multi_copy;
	private Button btn_stop;
	private Button btn_pause;
	private Button btn_restart;
	private Button btn_clear;
	private Context mContext;
	private String fromPath = null;
	private String toPath = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_io);
		mContext = this;
		
		btn_copy = (Button) findViewById(R.id.btn_copy);
		btn_multi_copy = (Button) findViewById(R.id.btn_multi_copy);
		btn_pause = (Button) findViewById(R.id.btn_pause);
		btn_stop = (Button) findViewById(R.id.btn_stop);
		btn_restart = (Button) findViewById(R.id.btn_restart);
		btn_clear = (Button) findViewById(R.id.btn_clear);
		
		btn_copy.setOnClickListener(this);
		btn_multi_copy.setOnClickListener(this);
		btn_pause.setOnClickListener(this);
		btn_stop.setOnClickListener(this);
		btn_restart.setOnClickListener(this);
		btn_clear.setOnClickListener(this);
		
		fromPath = PathManager.getPhotosPath();
		toPath =PathManager.getPhotosTemp();
		//创建数据库表thread 和task
		DataBase.createTable(ThreadTable.CREATE_TABLE_SQL);
		DataBase.createTable(TaskTable.CREATE_TABLE_SQL);
		// 创建触发器
		DataBase.excute(DataTrigger.TRIGGER_AFTER_DELETE_TASK);
		DataBase.excute(DataTrigger.TRIGGER_AFTER_UPDATE_THREAD);
		
	}
	String uri ="http://gdown.baidu.com/data/wisegame/0258f036ba230587/shoujibaidu_16789000.apk";

	private long time = 0;
	
	private boolean stop = false;
	
	private boolean pause = false;
	private List<String> faiedThread = new ArrayList<String>();
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_copy:
			time = System.currentTimeMillis();
//			new CopyTask(fromPath, toPath).execute();
			new Thread(){
				public void run() {
					MultiDownLoad.DownFile(5, uri, toPath, "shoujibaidu_16789000.apk", stateInterface);
				};
			}.start();
			break;
		case R.id.btn_multi_copy:
			LogUtils.Log_E("single thread download begin time:"
					+ System.currentTimeMillis());
			new Thread() {
				public void run() {
					try {
						DownloadUtils.DownLoad(uri, fromPath + File.separator
								+ "shoujibaidu_16789000.apk");
					} catch (Exception e) {
						e.printStackTrace();
					}
				};
			}.start();

			break;
		case R.id.btn_pause:
			pause = true;
			break;
		case R.id.btn_stop:
			stop = true;
			break;
		case R.id.btn_restart:
			for (String id : faiedThread) {
				List<ThreadBean> threadBeans = ThreadTable.query("uid=?", new String[]{id});
				if(!threadBeans.isEmpty()){
					ThreadBean bean = threadBeans.get(0);
					new DownLoadThread(
							bean.getUri(), bean.getFile_path(), id, id, bean.getFile_name(), 
							bean.getLength(),bean.getUri_begin_index(), 
							bean.getUri_end_index(),bean.getFile_begin_index(),
							bean.getFile_end_index(), stateInterface).start();
				}
			}
			faiedThread.clear();
			break;
		case R.id.btn_clear:
			File toFile =new File(toPath);
			if(toFile.exists()){
				toFile.delete();
			}
			break;

		default:
			break;
		}

	}

	private long totalLength = 0;
	private int threadSuccessCount = 0;
	
	private boolean Pause(){
		return pause;
	}
	
	private boolean Stop(){
		return stop;
	}
	
	private DownLoadInterface stateInterface = new DownLoadInterface() {
		
		@Override
		public void setSuccess(boolean isSuccess) {
			if(isSuccess)
			LogUtils.Log_E("下载成功");
//			ToastUtils.show(mContext, "下载成功");
		}
		
		@Override
		public void setMessage(String msg) {
			LogUtils.Log_E("错误信息："+msg);
		}
		
		@Override
		public void setLength(long length) {
			totalLength+=length;
			LogUtils.Log_E("当前下载长度"+String.valueOf(totalLength));
		}

		@Override
		public void setSuccessCount(int num) {
			threadSuccessCount +=num;
			mHandler.obtainMessage(0, threadSuccessCount, -1).sendToTarget();
			LogUtils.Log_E("");
		}
		
		@Override
		public void setId(String id) {
			
		}
		
		@Override
		public boolean isPause() {
			return Pause();
		}

		@Override
		public boolean isStop() {
			return Stop();
		}

		@Override
		public void setInterrupt(String tempFile, String threadId,
				String threadName, long downLength, long uriBegin, long uriEnd,long fileBegin,long fileEnd) {
			ThreadBean threadBean = new ThreadBean();
			threadBean.setUid(threadId);
			threadBean.setName(threadId);
			threadBean.setLength(downLength);
			threadBean.setTemp_file_path(tempFile);
			threadBean.setUri_begin_index(uriBegin);
			threadBean.setUri_end_index(uriEnd);
			threadBean.setFile_begin_index(fileBegin);
			threadBean.setFile_end_index(fileEnd);
			threadBean.setFinish(0);
			ThreadTable.update(" uid =? ", new String []{threadId}, threadBean);
		}

		@Override
		public void setCoypFactor(String tempFile, String desFile,
				long desBegin, long desEnd) {
			new CopyTask(tempFile, desFile, desBegin).execute();
		}

		@Override
		public void setFailedThreadId(String id) {
			faiedThread.add(id);
			LogUtils.Log_E("失败的线程id:"+id);
		}
		
	};
	
	private Handler mHandler =new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				int count  =msg.arg1;
				if(count==5){
					ToastUtils.show(mContext, "下载成功！");
					LogUtils.Log_E("total elapse time:"+(System.currentTimeMillis()-time));
					count=0;
					threadSuccessCount = 0;
				}
				break;

			default:
				break;
			}
		};
	};
	
	class CopyTask extends AsyncTask<Void, Void, Void>{
		private String from =null;
		private String to =null;
		private long begin = 0;
		public CopyTask(String from,String to,long begin ){
			this.from = from;
			this.to =to;
			this.begin =begin;
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			ToastUtils.show(mContext, "复制成功");
		}

		@Override
		protected Void doInBackground(Void... params) {
			if(TextUtils.isEmpty(from)||TextUtils.isEmpty(to)){
				return null;
			}
			File fromFile  = new File(from);
			File toFile  = new File(to);
			if(!fromFile.exists()){
				return null;
			}
			if(!toFile.exists()){
				try {
					toFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			FileUtils.copy(fromFile, toFile, begin, true);
			return null;
		}
		
	}
}
