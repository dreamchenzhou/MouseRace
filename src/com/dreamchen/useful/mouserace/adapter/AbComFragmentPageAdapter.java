package com.dreamchen.useful.mouserace.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
/**
 * （改进：可以设置缓存实例的个数，多余的销毁）
 * 会左右预先加offsreenslimit的个数，不销毁fragment的实例，只是回收view
 * @author yong.chen
 *
 * @param <T>
 */
public class AbComFragmentPageAdapter<T extends Fragment> extends FragmentPagerAdapter {
	
	private List<T> mDatas = new ArrayList<T>();
	
	public AbComFragmentPageAdapter(FragmentManager fm,List<T> mList) {
		super(fm);
		mDatas = mList;
	}

	@Override
	public Fragment getItem(int position) {
		return mDatas.get(position);
	}
	
	@Override
	public int getCount() {
		return mDatas.size();
	}

}
