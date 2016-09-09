package com.dreamchen.useful.mouserace.fragment;

import java.util.ArrayList;
import java.util.List;

import com.baidu.a.a.a.b;
import com.dreamchen.useful.mouserace.R;

import android.R.fraction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CacheFragment extends Fragment {
	private String TAG = CacheFragment.class.getSimpleName();
	
	private String Title;
	public static CacheFragment getInstance(String name){
		CacheFragment fragment = new CacheFragment();
		Bundle bundle = new Bundle();
		bundle.putString("name", name);
		fragment.setArguments(bundle);
		return fragment;
	}
	
	public static List<CacheFragment> getInstances(String [] nameArray){
		List<CacheFragment> fragments = new ArrayList<CacheFragment>();
		for (int i = 0; i < nameArray.length; i++) {
			String string = nameArray[i];
			fragments.add(getInstance(string));
		}
		return fragments;
	}
	
	private View mView = null;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_cachefragment, null);
		return mView;
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		TextView txt_name =  (TextView) mView.findViewById(R.id.txt_name);
		Title  =getArguments().getString("name");
		txt_name.setText(Title);
	}
	
	@Override
	public void onStart() {
		// TODO 自动生成的方法存根
		super.onStart();
		log("onStart");
	}
	
	@Override
	public void onResume() {
		// TODO 自动生成的方法存根
		super.onResume();
		log("onResume");
	}
	
	@Override
	public void onPause() {
		// TODO 自动生成的方法存根
		super.onPause();
		log("onPause");
	}
	
	
	@Override
	public void onStop() {
		// TODO 自动生成的方法存根
		super.onStop();
		log("onStop");
	}
	@Override
	public void onDestroyView() {
		// TODO 自动生成的方法存根
		super.onDestroyView();
		log("onDestroyView");
	}
	
	@Override
	public void onDestroy() {
		// TODO 自动生成的方法存根
		super.onDestroy();
		log("onDestroy");
	}
	
	@Override
	public void onDetach() {
		// TODO 自动生成的方法存根
		super.onDetach();
		log("onDetach");
	}
	
	private void log(String method){
		if(!TextUtils.isEmpty(Title))
		Log.e(TAG, String.format("%s---%s", Title,method));
	}
	
}
