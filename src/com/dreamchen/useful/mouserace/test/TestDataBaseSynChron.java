package com.dreamchen.useful.mouserace.test;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.dreamchen.useful.mouserace.R;
import com.dreamchen.useful.mouserace.base.BaseActivity;
import com.dreamchen.useful.mouserace.database.DataBase;
import com.dreamchen.useful.mouserace.database.Table;
import com.dreamchen.useful.mouserace.database.Test;
import com.dreamchen.useful.mouserace.database.TestDB;
import com.dreamchen.useful.mouserace.utils.LogUtils;

public class TestDataBaseSynChron extends BaseActivity implements OnClickListener {
	
	private List<Test> datas  = new ArrayList<Test>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initData(100);
		setContentView(R.layout.activity_test_syn_databse);
		findViewById(R.id.btn_syn).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_syn:
			for (int i = 0; i < datas.size(); i++) {
				final Test test = datas.get(i);
				final int temp  = i;
				new Thread(){
					
					public void run() {
						this.setName(""+temp);
						TestDB.insert(test);
					};
				}.start();
			}
			break;

		default:
			break;
		}
		
	}
	
	private void initData(int length){
		for(int i =0;i<length;i++){
			Test test = new Test();
			test.setName("test"+(i+1));
			test.setContent("content"+(i+1));
			test.setCount(i+1);
			datas.add(test);
		}
	}
}
