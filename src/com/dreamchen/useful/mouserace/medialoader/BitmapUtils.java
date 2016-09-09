package com.dreamchen.useful.mouserace.medialoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.dreamchen.useful.mouserace.global.ApplicationCore;

public class BitmapUtils {
	public static Bitmap getBitmapByPath(String path,int width,int height){
		if(TextUtils.isEmpty(path)){
			return null;
		}
		Bitmap bitmap = null;
		BitmapFactory.Options opts = new Options();
		opts.inJustDecodeBounds = true;
		bitmap = BitmapFactory.decodeFile(path, opts);
		int sample = 1;
		if(opts.outWidth>width||opts.outHeight>height){
			float scaleX= opts.outWidth*1f/(width*1f);
			float scaleY= opts.outHeight*1f/(height *1f);
			float scale= Math.max(scaleX, scaleY);
			sample = Math.round(scale);
		}
		opts.inSampleSize = sample;
		opts.inJustDecodeBounds = false;
		opts.inPurgeable = true;
		opts.inInputShareable = true;
		if("image/png".equalsIgnoreCase(opts.outMimeType)){
			// png格式图片带透明通道
			opts.inPreferredConfig = Config.ARGB_8888;
		}else{
			// 其他图片格式不带透明通道，每个像素点不需要占用那么多字节
			opts.inPreferredConfig = Config.RGB_565;
		}
		try {
			bitmap = BitmapFactory.decodeStream(new FileInputStream(path), null, opts);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return bitmap;
	}
	
	public static Bitmap getBitmapByPath(File file,int width,int height) throws FileNotFoundException{
		if(file ==null||!file.exists()){
			return null;
		}
		Bitmap bitmap = null;
		BitmapFactory.Options opts = new Options();
		opts.inJustDecodeBounds = true;
		bitmap = BitmapFactory.decodeStream(new FileInputStream(file), null, opts);
		int sample = 1;
		if(opts.outWidth>width||opts.outHeight>height){
			float scaleX= opts.outWidth*1f/(width*1f);
			float scaleY= opts.outHeight*1f/(height *1f);
			float scale= Math.max(scaleX, scaleY);
			sample = Math.round(scale);
		}
		opts.inSampleSize = sample;
		opts.inJustDecodeBounds = false;
		opts.inPurgeable = true;
		opts.inInputShareable = true;
		if("image/png".equalsIgnoreCase(opts.outMimeType)){
			// png格式图片带透明通道
			opts.inPreferredConfig = Config.ARGB_8888;
		}else{
			// 其他图片格式不带透明通道，每个像素点不需要占用那么多字节
			opts.inPreferredConfig = Config.RGB_565;
		}
		try {
			bitmap = BitmapFactory.decodeStream(new FileInputStream(file), null, opts);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return bitmap;
	}
	
	/**
	 * decode出来的所有图片不大于100k,实际上会是100k左右
	 * @param path
	 * @return
	 */
	public static Bitmap decodeBitmap(String path) {
		Bitmap bitmap = null;
		if(TextUtils.isEmpty(path)){
			return null;
		}
		BitmapFactory.Options opts = new Options();
		opts.inJustDecodeBounds = true;
		bitmap = BitmapFactory.decodeFile(path, opts);
		int sample = 1;
		opts.inJustDecodeBounds = false;
		opts.inPurgeable = true;
		opts.inInputShareable = true;
		// 每个像素点所占用的字节
		int countPerPix = 0;
		if("image/png".equalsIgnoreCase(opts.outMimeType)){
			// png格式图片带透明通道
			opts.inPreferredConfig = Config.ARGB_8888;
			// ARGB_8888 一个像素占4个自己
			countPerPix = 4;
		}else{
			// 其他图片格式不带透明通道，每个像素点不需要占用那么多字节
			opts.inPreferredConfig = Config.RGB_565;
			// 占两个字节
			countPerPix = 2;
		}
		int width = opts.outWidth;
		int height = opts.outHeight;
		// 压缩图片使他小于100k
		while((width/sample)*(height/sample)*countPerPix>100*1024){
			sample++;
		}
		opts.inSampleSize =sample;
		try {
			bitmap = BitmapFactory.decodeStream(new FileInputStream(path), null, opts);
			int size = bitmap.getRowBytes()*bitmap.getHeight()/1024;
			Log.e("dream", "size="+size);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return bitmap;
	}
	public static DisplayMetrics getDisplayMetrics(){
		DisplayMetrics metrics = new DisplayMetrics();
		WindowManager manager =(WindowManager) ApplicationCore.getInstance().getSystemService(Context.WINDOW_SERVICE);
		manager.getDefaultDisplay().getMetrics(metrics);
		return metrics;
	}
}
