package com.dreamchen.useful.mouserace.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder {
	 private final SparseArray<View> mViews ;  
	 private View mConvertView = null;
	 private OnClickListener mOnItemClickListener;
	 private OnLongClickListener mOnLongClickListener;
	 
	 
	 private ViewHolder (Context context,ViewGroup parent,int layoutId,int position){
		 this.mViews = new SparseArray<View>();
		 this.mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
		 mConvertView.setTag(this);
	 }
	 
	 /**
	  * 拿到一个viewholder
	  * @param context
	  * @param convertView
	  * @param parent
	  * @param layoutId
	  * @param position
	  * @return
	  */
	 public static ViewHolder get(Context context,View convertView,ViewGroup parent,int layoutId,int position){
		 if(convertView==null){
			 return new ViewHolder(context, parent, layoutId, position);
		 }
		 return (ViewHolder) convertView.getTag();
	 }
	 
	 public <T extends View> T getView(int viewId){
		 View view = mViews.get(viewId);
		 if(view == null){
			 view = mConvertView.findViewById(viewId);
			 mViews.put(viewId, view);
		 }
		 return (T) view;
	 }
	 
	 public View getConvertView(){
		 return mConvertView;
	 }
	 /*****************TexView Begin********************/
	 public void setTxtText(int txtId,String txt){
		 TextView view = getView(txtId);
		 view.setText(txt);
	 }
	 
	 public void setTxtText(int txtId,int resId){
		 TextView view = getView(txtId);
		 view.setText(resId);
	 }
	 /*****************TexView End********************/
	 
	 /*****************ImageView Begin********************/
	 public void setButtonText(int txtId,String txt){
		 Button view = getView(txtId);
		 view.setText(txt);
	 }
	 
	 public void setButtonText(int txtId,int resId){
		 Button view = getView(txtId);
		 view.setText(resId);
	 }
	 /*****************Button End********************/
	 
	 /*****************ImageView Begin********************/
	 public void setImageRes(int imgId,int resId){
		 ImageView view = getView(imgId);
		 view.setImageResource(resId);
	 }
	 
	 public void setImageDrawable(int imgId,Drawable drawable){
		 ImageView view = getView(imgId);
		 view.setImageDrawable(drawable);
	 }
	 
	 public void setImageBitmap(int imgId,Bitmap bitmap){
		 ImageView view = getView(imgId);
		 view.setImageBitmap(bitmap);
	 }
	 /*****************ImageView End********************/
	 
	 /*****************listener Begin********************/
	 
	 public void setOnItemClickListener(OnClickListener clickListener){
		 mConvertView.setOnClickListener(clickListener);
	 }
	 
	 public void setOnLongClickListener(OnLongClickListener onLongClickListener){
		 mConvertView.setOnLongClickListener(onLongClickListener);
	 }
	 public void setClickListener(int viewId,OnClickListener clickListener){
		 View view = getView(viewId);
		 view.setOnClickListener(clickListener);
	 }
	 
	 public void setOnLongClickListener(int viewId,OnLongClickListener onLongClickListener){
		 View view  =getView(viewId);
		 view.setOnLongClickListener(onLongClickListener);
	 }
	 /*****************listener End********************/
}
