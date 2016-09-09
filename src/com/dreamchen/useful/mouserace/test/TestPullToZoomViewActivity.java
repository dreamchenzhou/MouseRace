package com.dreamchen.useful.mouserace.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.dreamchen.useful.mouserace.R;
import com.dreamchen.useful.mouserace.view.pulltozoom.PullToZoomListViewEx;
import com.dreamchen.useful.mouserace.view.pulltozoom.PullToZoomScrollViewEx;

public class TestPullToZoomViewActivity extends Activity {
	PullToZoomScrollViewEx mScrollViewEx = null;
	PullToZoomListViewEx listView = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// zoom view
//		setContentView(R.layout.activity_test_zoomview);
//		mScrollViewEx = (PullToZoomScrollViewEx) findViewById(R.id.zoom_view);
//		
//		View header   = LayoutInflater.from(this).inflate(R.layout.layout_zoom_head, null);
//		View zoom  =LayoutInflater.from(this).inflate(R.layout.layout_zoom, null);
//		View container  =LayoutInflater.from(this).inflate(R.layout.layout_zoom_container, null);
//		mScrollViewEx.setHeaderView(header);
//		mScrollViewEx.setZoomView(zoom);
//		mScrollViewEx.setScrollContentView(container);
		
		
		// list zoomview
		setContentView(R.layout.activity_test_list_zoomview);
		 listView = (PullToZoomListViewEx) findViewById(R.id.listview);

	        String[] adapterData = new String[]{"Activity", "Service", "Content Provider", "Intent", "BroadcastReceiver", "ADT", "Sqlite3", "HttpClient",
	                "DDMS", "Android Studio", "Fragment", "Loader", "Activity", "Service", "Content Provider", "Intent",
	                "BroadcastReceiver", "ADT", "Sqlite3", "HttpClient", "Activity", "Service", "Content Provider", "Intent",
	                "BroadcastReceiver", "ADT", "Sqlite3", "HttpClient"};

	        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, adapterData));
	        listView.getPullRootView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	                Log.e("zhuwenwu", "position = " + position);
	            }
	        });

	        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	                Log.e("zhuwenwu", "position = " + position);
	            }
	        });

	        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
	        getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
	        int mScreenHeight = localDisplayMetrics.heightPixels;
	        int mScreenWidth = localDisplayMetrics.widthPixels;
	        AbsListView.LayoutParams localObject = new AbsListView.LayoutParams(mScreenWidth, (int) (9.0F * (mScreenWidth / 16.0F)));
	        listView.setHeaderLayoutParams(localObject);
	        listView.setParallax(false);
	        ImageView imageView = new ImageView(this);
	        imageView.setScaleType(ScaleType.FIT_XY);
	        imageView.setImageDrawable(getResources().getDrawable(R.drawable.g2));
	        listView.setHeaderView(imageView);
	}
}
