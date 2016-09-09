package com.dreamchen.useful.mouserace.adapter;

import java.util.List;

import android.R.integer;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 
 * 普通快速adapter
 * @param <T>
 */
public abstract class ComAdaper <T>extends BaseAdapter {

	protected LayoutInflater mInflater;  
    protected Context mContext;  
    protected List<T> mDatas;  
    protected final int mItemLayoutId; 
    
    public ComAdaper(Context context,List<T> datas,int layoutId){
    	this.mContext = context;
    	this.mDatas = datas;
    	this.mItemLayoutId = layoutId;
    }
    
	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public T getItem(int positon) {
		return mDatas.get(positon);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder = getViewHolder(position, convertView, parent);
		convert(viewHolder, getItem(position),position);
		return viewHolder.getConvertView();
	}

	/**
	 * 所有的初始化在convert里面实现
	 * @param helper
	 * @param item
	 */
	public abstract void convert(ViewHolder helper, T item,int postion);
	
	
	private ViewHolder getViewHolder(int position, View convertView,  
            ViewGroup parent)  
    {  
        return ViewHolder.get(mContext, convertView, parent, mItemLayoutId,  
                position);  
    }  
}
