package com.dreamchen.useful.mouserace.test;

import android.os.Bundle;
import android.util.Log;

import com.dreamchen.useful.mouserace.R;
import com.dreamchen.useful.mouserace.base.BaseActivity;

public class TestThemeActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_layout_theme);
	}
	
	@Override
	public void onBackPressed() {
		// TODO 自动生成的方法存根
		super.onBackPressed();
		Log.e("dream", "onBackPressed");
	}
	
	@Override
	public void finish() {
		super.finish();
		Log.e("dream", "finish");
	}
	
	@Override
	protected void onDestroy() {
		// TODO 自动生成的方法存根
		super.onDestroy();
		Log.e("dream", "onDestroy");
	}
}
