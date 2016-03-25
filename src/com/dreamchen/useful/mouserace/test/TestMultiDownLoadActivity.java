package com.dreamchen.useful.mouserace.test;

import java.io.File;
import java.io.IOException;

import javax.security.auth.PrivateCredentialPermission;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dreamchen.useful.mouserace.PathManager;
import com.dreamchen.useful.mouserace.R;
import com.dreamchen.useful.mouserace.base.BaseActivity;
import com.dreamchen.useful.mouserace.download.DownloadUtils;
import com.dreamchen.useful.mouserace.multithread.CopyThread;
import com.dreamchen.useful.mouserace.multithread.DownLoadInterface;
import com.dreamchen.useful.mouserace.multithread.MultiDownLoad;
import com.dreamchen.useful.mouserace.multithread.StateInterface;
import com.dreamchen.useful.mouserace.utils.FileUtils;
import com.dreamchen.useful.mouserace.utils.LogUtils;
import com.dreamchen.useful.mouserace.utils.ToastUtils;

public class TestMultiDownLoadActivity extends BaseActivity implements OnClickListener {
	private Button btn_copy;
	private Button btn_multi_copy;
	private Button btn_stop;
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
		btn_stop = (Button) findViewById(R.id.btn_stop);
		btn_clear = (Button) findViewById(R.id.btn_clear);
		
		btn_copy.setOnClickListener(this);
		btn_multi_copy.setOnClickListener(this);
		btn_stop.setOnClickListener(this);
		btn_clear.setOnClickListener(this);
		
		fromPath = PathManager.getPhotosPath();
		toPath =PathManager.getPhotosTemp();
		
	}
	String uri ="http://gdown.baidu.com/data/wisegame/0258f036ba230587/shoujibaidu_16789000.apk";

	private long time = 0;
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
		case R.id.btn_stop:
			
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
		public void setId(int id) {
			
			
		}
		
		@Override
		public void setCoypFactor(String tempFile, long desBegin, long desEnd) {
			LogUtils.Log_E("begin index="+desBegin);
			new CopyTask(toPath+File.separator+tempFile, toPath+File.separator+"shoujibaidu_16789000.apk", desBegin).execute();
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
			FileUtils.copy(fromFile, toFile, begin, false);
			return null;
		}
		
	}
}
