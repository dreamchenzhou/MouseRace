package com.dreamchen.useful.mouserace.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
/**
 * 改进：可以设置缓存实例的个数，不一定每一个都要销毁
 * 
 * 会左右预先加offsreenslimit的个数，不销毁（缓存）的个数为左右offsreenslimit的个数,其余的fragment实例全部销毁！！！
 * 
 *
 * @param <T>
 */
public class AbComFragmentStatePageAdapter<T extends Fragment> extends FragmentStatePagerAdapter {
	
	private List<T> mDatas = new ArrayList<T>();
	
	public AbComFragmentStatePageAdapter(FragmentManager fm,List<T> mList) {
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
