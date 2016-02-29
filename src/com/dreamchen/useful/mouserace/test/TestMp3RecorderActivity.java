package com.dreamchen.useful.mouserace.test;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;

import com.dreamchen.useful.mouserace.R;
import com.dreamchen.useful.mouserace.media.MP3Recorder;

public class TestMp3RecorderActivity  extends Activity implements OnClickListener{
	
	private File file = new File(Environment.getExternalStorageDirectory(),"test.mp3");
	
	private MP3Recorder mRecorder = new MP3Recorder(file);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_mp3);
		findViewById(R.id.btn_start).setOnClickListener(this);
		findViewById(R.id.btn_stop).setOnClickListener(this);
		findViewById(R.id.btn_play).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_start:
			try {
				mRecorder.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case R.id.btn_stop:
			mRecorder.stop();
			break;
		case R.id.btn_play:
			 Intent intent = new Intent(); 
			 intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			 intent.setAction(Intent.ACTION_VIEW);
			 intent.setData(Uri.fromFile(file));
	          intent.setType("audio/*"); 
	          startActivity(intent);
			break;
		default:
			break;
		}
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mRecorder.stop();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mRecorder.stop();
	}
	
	
}

