package com.dreamchen.useful.mouserace.test;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.dreamchen.useful.mouserace.R;
import com.dreamchen.useful.mouserace.adapter.AbComFragmentPageAdapter;
import com.dreamchen.useful.mouserace.adapter.AbComFragmentStatePageAdapter;
import com.dreamchen.useful.mouserace.base.BaseActivity;
import com.dreamchen.useful.mouserace.fragment.CacheFragment;

public class TestCacheFragmentActivity extends BaseActivity {

	private ViewPager mViewPager;
	private Button btn_one, btn_two, btn_three;
	
	private List<CacheFragment> mDatas  = new ArrayList<CacheFragment>();
	private AbComFragmentPageAdapter<CacheFragment> mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cachefragment);
		findview();
		initDatas();
		init();
	}
	
	private void findview(){
		mViewPager = (ViewPager) findViewById(R.id.page);
		btn_one = (Button) findViewById(R.id.btn_fragment_one);
		btn_two = (Button) findViewById(R.id.btn_fragment_two);
		btn_three = (Button) findViewById(R.id.btn_fragment_three);
		
	}
	
	private void initDatas(){
		String [] array = new String [] {"one","two","three","four","five","six"};
		mDatas = CacheFragment.getInstances(array);
	}
	
	private void init(){
		btn_one.setOnClickListener(mClickListener);
		btn_two.setOnClickListener(mClickListener);
		btn_three.setOnClickListener(mClickListener);
		mAdapter = new AbComFragmentPageAdapter<CacheFragment>(getSupportFragmentManager(), mDatas);
		mViewPager.setOffscreenPageLimit(1);
		mViewPager.setAdapter(mAdapter);
	}

	private OnClickListener mClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_fragment_one:
				mViewPager.setCurrentItem(0);
				break;
			case R.id.btn_fragment_two:
				mViewPager.setCurrentItem(1);
				break;
			case R.id.btn_fragment_three:
				mViewPager.setCurrentItem(2);
				break;
			default:
				break;
			}
		}
	};
}
