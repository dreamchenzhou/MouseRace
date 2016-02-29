package com.dreamchen.useful.mouserace.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.dreamchen.useful.mouserace.R;
import com.dreamchen.useful.mouserace.multipic.ActivityChooseMultiPics;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
/**
 * 
 * 一次获取多张图片
 */
public class TestMultiPicsActivity extends Activity implements OnClickListener {
	private GridView mGridView;
	
	private String [] pathArray;
	
	private static final int REQUEST_CODE_GET_MULTI_IMAGES = 11;
	
	private Context mContext;
	private DisplayAdapter mAdapter;
	
	private int maxNum = 5;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_multipic_display);
		mGridView = (GridView) findViewById(R.id.grid_display);
		mContext = this;
		mAdapter = new DisplayAdapter();
		mGridView.setAdapter(mAdapter);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK){
		switch (requestCode) {
		case REQUEST_CODE_GET_MULTI_IMAGES:
			try {
				pathArray = (String[]) data.getSerializableExtra("data");
			} catch (Exception e) {
				Log.e("dream", e.toString());
			}
			mAdapter.notifyDataSetChanged();
			break;

		default:
			break;
		}
		}
	}
	
	class DisplayAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			if(pathArray==null||pathArray.length==0){
				return 1;
			}
			return pathArray.length;
		}

		@Override
		public String getItem(int position) {
			if(pathArray!=null){
				return pathArray[position];
			}
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View contentView, ViewGroup parent) {
			if(contentView == null){
				contentView = LayoutInflater.from(mContext).inflate(R.layout.grid_item_test_album, null);
			}
			ImageView img= (ImageView) contentView.findViewById(R.id.img_pic_display);
			if((pathArray==null||pathArray.length ==0)&&(position==0)){
				img.setImageResource(R.drawable.icon_add_picture);
				img.setOnClickListener(TestMultiPicsActivity.this);
			}else if(pathArray.length-1==position){
				img.setImageResource(R.drawable.icon_add_picture);
				img.setOnClickListener(TestMultiPicsActivity.this);
			}else{
				String path = pathArray[position];
				File file= new File(path);
				FileInputStream fis = null;
				try {
					fis = new FileInputStream(file);
					img.setImageBitmap(BitmapFactory.decodeStream(fis));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return contentView;
		}
		
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.img_pic_display:
			Intent intent = new Intent(this,ActivityChooseMultiPics.class);
			if(pathArray==null||pathArray.length==0){
				intent.putExtra("max", maxNum);
			}else{
				intent.putExtra("max", maxNum-pathArray.length);
			}
			startActivityForResult(intent, REQUEST_CODE_GET_MULTI_IMAGES);
			break;

		default:
			break;
		}
	}
}
