package com.dreamchen.useful.mouserace.medialoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamchen.useful.mouserace.R;
import com.dreamchen.useful.mouserace.attach.AttachInfo;

@SuppressLint("NewApi") public class MediaLoader {

	private  ImageView mImageView;
	private TextView txt_title;
	private String path; // 本地路径
	private String url; // 网络路径
	private int type;
	private List<OnDownFinishedCallBack> callBacks =new ArrayList<MediaLoader.OnDownFinishedCallBack>();
	
	public MediaLoader(){
	}
	
	public void setTextView(TextView title){
		txt_title = title;
	}
	
	public  void display(int attachType,String attachPath,String fileName,ImageView img){
		this.mImageView = img;
		AttachInfo info = new AttachInfo();
		info.Type = (attachType);
		info.Uri = (attachPath);
		info.Name = (fileName);
		type = attachType;
		url = attachPath;
		if(txt_title!=null){
			txt_title.setText(info.Name);
		}
		// 串行执行下载
		new MyDownLoadTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, info);
	}
	
	
	private class MyDownLoadTask extends DownLoadTask{
		
		@Override
		public void onTaskPre() {
			if(type ==0){
				mImageView.setImageResource(R.drawable.post_image_loding);
			}else if(type ==1){
				mImageView.setImageResource(R.drawable.load_audio_process);
			}else if(type ==2){
				mImageView.setImageResource(R.drawable.load_video_process);
			}else {
				mImageView.setImageResource(R.drawable.load_file_process);
			}
		}
		
		@Override
		public void onTaskPost(File result) {
			if(type == 0){
				if(result==null){
					mImageView.setImageResource(R.drawable.post_image_loading_failed);
				}else{
					new DecodeBitmapTask(mImageView).execute(result);
//					try {
//						mImageView.setImageBitmap(BitmapUtils.getBitmapByPath(result, 300, 300));
//					} catch (FileNotFoundException e) {
//						e.printStackTrace();
//					}
				}
//				Log.e("dream", "容器中图片大小："+mCache.size());
			}else if(type == 1){
				mImageView.setImageResource(R.drawable.audio);
			}else if(type == 2){
				mImageView.setImageResource(R.drawable.video);
			}else{
				mImageView.setImageResource(R.drawable.load_audio_process);
			}
			//
			// 接口回调
			perform(result);
		}
		
		@Override
		public void onTaskFailed() {
			if(type ==0){
				mImageView.setImageResource(R.drawable.post_image_loading_failed);
			}else if(type ==1){
				mImageView.setImageResource(R.drawable.load_audio_failed);
			}else if(type ==2){
				mImageView.setImageResource(R.drawable.load_video_failed);
			}else {
				mImageView.setImageResource(R.drawable.load_file_failed);
			}
		}
	};

	public void addDownLoadCall(OnDownFinishedCallBack callBack){
		this.callBacks.add(callBack);
	}
	
	public void removeDownLoadCallBack(OnDownFinishedCallBack callBack){
		if(this.callBacks.contains(callBack))
		this.callBacks.remove(callBack);
	}
	
	public interface OnDownFinishedCallBack{
		public void downFinished(File file);
	}
	
	private void perform(File file){
		for (OnDownFinishedCallBack callBack  : callBacks) {
			callBack.downFinished(file);
		}
	}
	
}
