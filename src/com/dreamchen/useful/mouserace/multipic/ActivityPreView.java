package com.dreamchen.useful.mouserace.multipic;

import java.util.ArrayList;
import java.util.List;

import com.dreamchen.useful.mouserace.R;
import com.dreamchen.useful.mouserace.multipic.zoom.PhotoView;
import com.dreamchen.useful.mouserace.utils.BitmapUtils;
import com.dreamchen.useful.mouserace.utils.DisplayUtils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;

public class ActivityPreView extends Activity {
	
	private ViewPager mPager;
	private GalleryAdapter mAdapter;
	private List<String>pathList;  
	private Context mContext;
	private int screenWidth = 0;
	private int screenHeight = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preview);
		DisplayMetrics metrics = DisplayUtils.getDisplayMetrics();
		screenWidth = metrics.widthPixels;
		screenHeight = metrics.heightPixels;
		mContext = this;
		pathList= getIntent().getStringArrayListExtra("paths");
		mPager = (ViewPager) findViewById(R.id.view_pager);
		mAdapter = new GalleryAdapter(getViewByPath(pathList));
		mPager.setAdapter(mAdapter);
		
	}
	
	class GalleryAdapter extends PagerAdapter{

		private List<PhotoView> views;
		public GalleryAdapter(List<PhotoView>photoViews){
			this.views = photoViews;
		}
		@Override
		public int getCount() {
			return views==null?0:views.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(views.get(position));
			return views.get(position);
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		
	}

	private List<PhotoView>getViewByPath(List<String> pathList){
		List<PhotoView> views = new ArrayList<PhotoView>();
		if(pathList==null||pathList.size()==0){
			return views;
		}
		for (String path : pathList) {
			PhotoView photoView = new PhotoView(mContext);
			Bitmap bitmap = BitmapUtils.getPressedSolidBitmapByPath(path, 
					screenWidth-DisplayUtils.dip2px(mContext, 10), 
					520);
			photoView.setImageBitmap(bitmap);
			photoView.setScaleType(ScaleType.CENTER);
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
			photoView.setLayoutParams(params);
			views.add(photoView);
		}
		return views;
	}
}
