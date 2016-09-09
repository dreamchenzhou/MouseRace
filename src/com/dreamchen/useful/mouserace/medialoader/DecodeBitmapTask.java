package com.dreamchen.useful.mouserace.medialoader;

import java.io.File;
import java.io.FileNotFoundException;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

public class DecodeBitmapTask extends AsyncTask<File, Void, Bitmap> {

	private ImageView mImageView;
	public DecodeBitmapTask(ImageView imgView){
		mImageView = imgView;
	}
	@Override
	protected Bitmap doInBackground(File... params) {
		Bitmap bitmap = null;
		try {
			bitmap  = BitmapUtils.getBitmapByPath(params[0], 300, 300);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		if(result!=null){
			mImageView.setImageBitmap(result);
		}
		super.onPostExecute(result);
	}
	
	
	
	
}
