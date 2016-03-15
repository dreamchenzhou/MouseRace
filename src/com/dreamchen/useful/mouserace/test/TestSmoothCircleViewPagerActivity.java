package com.dreamchen.useful.mouserace.test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.dreamchen.useful.mouserace.R;
import com.dreamchen.useful.mouserace.base.BaseActivity;
import com.dreamchen.useful.mouserace.view.viewpager.FixedSpeedScroller;
import com.dreamchen.useful.mouserace.view.viewpager.IndexView;
import com.dreamchen.useful.mouserace.view.viewpager.IndexView.OnIndexChangeListener;
import com.dreamchen.useful.mouserace.view.viewpager.SmoothTransformer;

public class TestSmoothCircleViewPagerActivity extends BaseActivity implements OnIndexChangeListener,OnPageChangeListener,OnTouchListener{
	private ViewPager mViewPager;
	
	private IndexView mIndexView;
	
	private int PageSize = 6;

	private int mCurrentIndex = 0;
	
	private Timer circleTimer;
	
	private CircleTask circleTask;

	private Timer demonTimer;
	
	private boolean isCirCleStoped = false;
	
	private boolean AllowRun = true;
	
	private FixedSpeedScroller  mScroller;
	
	private List<ImageView>imagList =new ArrayList<ImageView>();
	
	private int [] imgIds = new int[]{R.drawable.g1,R.drawable.g2,R.drawable.g3,R.drawable.g4,R.drawable.g5};
	
	private Context mContext;
	
	private PictureAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_viewpager);
		mViewPager = (ViewPager) findViewById(R.id.viewpager_circle);
		mIndexView = (IndexView) findViewById(R.id.indexview);
		mContext = this;
		init();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 关闭任务
		AllowRun = false;
		if(circleTimer!=null){
			circleTimer.cancel();
		}
		if(demonTimer!=null){
			demonTimer.cancel();
		}
	}
	
	private void init(){
		mViewPager.setPageTransformer(false, new SmoothTransformer());
		mViewPager.setOnTouchListener(this);
		mIndexView.setOnIndexChangeListener(this);
		mViewPager.setOnPageChangeListener(this);
		/* 主要代码段 */
		try {
			Field mField = ViewPager.class.getDeclaredField("mScroller");
			mField.setAccessible(true);
			// 设置加速度
			// ，通过改变FixedSpeedScroller这个类中的mDuration来改变动画时间（如mScroller.setmDuration(mMyDuration);）
			mScroller = new FixedSpeedScroller(mViewPager.getContext(),
					new AccelerateInterpolator());
			mField.set(mViewPager, mScroller);
			mScroller.setmDuration(500);
		} catch (Exception e) {
			e.printStackTrace();
		}
		initViewpager();
	}
	
	private void initViewpager(){
		mCurrentIndex = 0;
		if(circleTimer!=null){
			circleTimer.cancel();
		}
		if(circleTask!=null){
			circleTask.cancel();
		}
		if(mAdapter!=null){
			mAdapter.removeAll();
		}
		if(imgIds.length>0){
			loadData();
			mAdapter = new PictureAdapter();
			mViewPager.setAdapter(mAdapter);
			mViewPager.setCurrentItem(mCurrentIndex,true);
			if(imgIds.length>1){
				mIndexView.setCurrentIndex(mCurrentIndex);
				mIndexView.setTotalPoint(imgIds.length);
				PageSize = imgIds.length;
				// 启动图片循环例子
				circleTimer = new Timer();
				circleTask = new CircleTask();
				circleTimer.schedule(circleTask, 1000);
			}else{
				mIndexView.setVisibility(View.GONE);
			}
		}else{
			//TODO setViewPage gone or invisible
			
		}
	}
	
	private void loadData(){
		for(int i = 0;i<imgIds.length;i++){
			ImageView img= new ImageView(mContext);
			img.setImageResource(imgIds[i]);
			imagList.add(img);
		}
	}
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void onPageSelected(int currentIndex) {
		// TODO 自动生成的方法存根
		mIndexView.setCurrentIndex(currentIndex);
		mCurrentIndex = currentIndex;
	}

	@Override
	public void onIndexChanged(int currentIndex) {
		mViewPager.setCurrentItem(currentIndex,true);
	}
	
	
	class CircleTask extends TimerTask {

		@Override
		public void run() {
			int count =0;
			while(count<PageSize&&AllowRun){
				synchronized (this) {
					if(isCirCleStoped){
						try {
							wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				if (mCurrentIndex >= (PageSize - 1)) {
					mHandler.sendEmptyMessage(1);
					break;
				} else {
					mCurrentIndex++;
					Message msg = mHandler.obtainMessage(0, mCurrentIndex, -1);
					mHandler.sendMessage(msg);
				}
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			count = 0;
		}

		public synchronized void continues(){
			notifyAll();
		}
	}
	
	/**
	 * 
	 * 唤醒循环任务
	 */
	class DemoTask extends TimerTask{
		@Override
		public void run() {
			if(circleTask!=null&&AllowRun){
				isCirCleStoped = false;
				circleTask.continues();
			}
		}
	}
	
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				mIndexView.setCurrentIndex(msg.arg1);
				break;
			case 1:
				initViewpager();
				break;
			default:
				break;
			}
		}
	};
	
	@Override
	public boolean onTouch(View arg0, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			
		case MotionEvent.ACTION_MOVE:
			if (!isCirCleStoped) {
				isCirCleStoped = true;
			}
			if (demonTimer != null) {
				demonTimer.cancel();
				demonTimer = new Timer();
				demonTimer.schedule(new DemoTask(), 5000);
			} else {
				demonTimer = new Timer();
				demonTimer.schedule(new DemoTask(), 5000);
			}
			
			break;

		default:
			break;
		}
		return false;
	}
	
	class PictureAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			return imagList.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object obj) {
			return view == obj;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(imagList.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(imagList.get(position));
			return imagList.get(position);
		}
		
		private void removeAll(){
			imagList.clear();
			notifyDataSetChanged();
		}
		
		
	}
	
	
}
