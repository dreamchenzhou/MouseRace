package com.dreamchen.useful.mouserace.test;

import com.dreamchen.useful.mouserace.R;
import com.dreamchen.useful.mouserace.view.lockview.LStyleActivity;
import com.dreamchen.useful.mouserace.view.lockview.NormalActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class TestLockViewActivity extends Activity implements OnClickListener{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_lockview);
		findViewById(R.id.btn_style).setOnClickListener(this);
		findViewById(R.id.btn_normal).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_style:
				Intent intentStyle= new Intent(this, LStyleActivity.class);
				startActivity(intentStyle);
				break;
			case R.id.btn_normal:
				Intent intentNormal= new Intent(this, NormalActivity.class);
				startActivity(intentNormal);
				break;

			default:
				break;
			}
		
	}
}
