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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dreamchen.useful.mouserace.PathManager;
import com.dreamchen.useful.mouserace.R;
import com.dreamchen.useful.mouserace.base.BaseActivity;
import com.dreamchen.useful.mouserace.multithread.StateInterface;
import com.dreamchen.useful.mouserace.utils.FileUtils;
import com.dreamchen.useful.mouserace.utils.LogUtils;
import com.dreamchen.useful.mouserace.utils.ToastUtils;

public class TestIOActivity extends BaseActivity implements OnClickListener {
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
		
		fromPath = PathManager.getPhotosPath()+File.separator+"test.mp4";
	}

	private long time = 0;
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_copy:
			toPath =PathManager.getPhotosTemp()+File.separator+System.currentTimeMillis();
			time = System.nanoTime();
			new CopyTask(fromPath, toPath).execute();
			break;
		case R.id.btn_multi_copy:

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
	private StateInterface stateInterface = new StateInterface() {
		
		@Override
		public void setSuccess(boolean isSuccess) {
			if(isSuccess)
			LogUtils.Log_E("下载成功");
			
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
		}
	};
	
	private Handler mHandler =new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				int count  =msg.arg1;
				if(count==5){
					ToastUtils.show(mContext, "下载成功！");
					LogUtils.Log_E("elapse time:"+(System.nanoTime()-time));
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
		private String from;
		private String to;
		private ProgressDialog pDialog;
		public CopyTask(String from,String to){
			this.from = from;
			this.to =to;
			pDialog = new ProgressDialog(mContext);
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog.show();
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			pDialog.dismiss();
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
			FileUtils.multi_copy(5, fromFile, toFile, stateInterface);
			return null;
		}
		
	}
}
