package com.dreamchen.useful.mouserace.test;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.dreamchen.useful.mouserace.R;
import com.dreamchen.useful.mouserace.view.pullToReFresh.PullToRefreshBase;
import com.dreamchen.useful.mouserace.view.pullToReFresh.PullToRefreshBase.Mode;
import com.dreamchen.useful.mouserace.view.pullToReFresh.PullToRefreshBase.OnRefreshListener2;
import com.dreamchen.useful.mouserace.view.pullToReFresh.PullToRefreshListView;

public class TestPullToRefreshActivity  extends Activity implements OnRefreshListener2 {
	private PullToRefreshListView mList;
	
	private String [] strArray = new String[]{"唐伯虎点秋香","大话西游","月光宝盒","西游降魔"};
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_pulllist);
		mList = (PullToRefreshListView) findViewById(R.id.list);
		mList.setMode(Mode.BOTH);
		mList.setOnRefreshListener(this);
		ArrayAdapter<String> mAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strArray);
		mList.setAdapter(mAdapter);
	}

	
	class RefreshTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			return null;
		}
		
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO 自动生成的方法存根
			super.onPostExecute(result);
			strArray = new String[]{"夏洛特烦恼","功夫","唐伯虎点秋香","大话西游","月光宝盒","西游降魔"};
			ArrayAdapter<String> mAdapter= new ArrayAdapter<String>(TestPullToRefreshActivity.this, android.R.layout.simple_list_item_1, strArray);
			mList.setAdapter(mAdapter);
			mList.onRefreshComplete();
		}
	}


	@Override
	public void onPullDownToRefresh(PullToRefreshBase refreshView) {
		new RefreshTask().execute();
	}


	@Override
	public void onPullUpToRefresh(PullToRefreshBase refreshView) {
		new RefreshTask().execute();
		
	}
	
}
