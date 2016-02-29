package com.dreamchen.useful.mouserace.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.dreamchen.useful.mouserace.global.ApplicationCore;

public class BitmapUtils {
	
	/**
	 * 解析超大图片可能会oom
	 * @param path
	 * @param width
	 * @param height
	 * @return
	 */
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
	
	/**
	 * 根据矩阵压缩：大于制定的尺寸，将被压缩成制定的尺寸，小于制定的尺寸，不压缩
	 * @param path
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap getPressedSolidBitmapByPath(String path,int width,int height){
		if(TextUtils.isEmpty(path)){
			return null;
		}
		Bitmap bitmap = null;
		BitmapFactory.Options opts = new Options();
		opts.inJustDecodeBounds = true;
		bitmap = BitmapFactory.decodeFile(path, opts);
		// 重新获取bitmap
		opts.inJustDecodeBounds = false;
		opts.inPurgeable = true;
		opts.inInputShareable = true;
		if ("image/png".equalsIgnoreCase(opts.outMimeType)) {
			// png格式图片带透明通道
			opts.inPreferredConfig = Config.ARGB_8888;
		} else {
			// 其他图片格式不带透明通道，每个像素点不需要占用那么多字节
			opts.inPreferredConfig = Config.RGB_565;
		}
		try {
			bitmap = BitmapFactory.decodeStream(new FileInputStream(path),
					null, opts);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Matrix matrix = new Matrix();
		float widthScale = 1;
		float heightScale = 1;
		if(opts.outWidth>width||opts.outHeight>height){
			widthScale = (width*1f)/(bitmap.getWidth()*1f);
			heightScale = (height*1f)/(bitmap.getHeight()*1f);
		}
		matrix.setScale(widthScale, heightScale);
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
	}
	
	/**
	 * decode出来的所有图片会是100k左右
	 * （麻麻再也不用担心解析图片oom了）
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
			// ARGB_8888 一个像素占4个字节
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
			if(bitmap!=null){
				int size = bitmap.getRowBytes()*bitmap.getHeight()/1024;
				Log.e("dream", "size="+size);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return bitmap;
	}
	
	/**
	 * 获取固定尺寸的bitmap
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap getSolideSizeBitmap(Bitmap bm,int width,int height){
		float widthScale = (width*1f)/(bm.getWidth()*1f);
		float heightScale = (height*1f)/(bm.getHeight()*1f);
		Matrix matrix = new Matrix();
		matrix.setScale(widthScale, heightScale);
		return Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, false);
	}
	
	/**
	 * 获取圆角图片
	 * @param bitmap
	 * @param radius，圆角半径的大小
	 * @return
	 */
	public static Bitmap getRoundCornerBitmap(Bitmap bitmap,int radius){
//		Canvas canvas = new Canvas(bitmap);
//		Rect r =new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
//		RectF rect = new RectF(r);
//		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
//		canvas.drawRoundRect(rect, 3f, 3f, paint);
////		canvas.drawBitmap(bitmap, 0, 0, paint);
//		return bitmap;
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888); 
		Canvas canvas = new Canvas(output); 
//		final int color = 0xff424242; 
		final Paint paint = new Paint(); 
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()); 
		final RectF rectF = new RectF(rect); 
		final float roundPx = radius; 
		paint.setAntiAlias(true); 
//		canvas.drawARGB(0, 0, 0, 0); 
//		paint.setColor(color); 
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint); 
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN)); 
		canvas.drawBitmap(bitmap, rect, rect, paint); 
		return output;
	}
	
	public static DisplayMetrics getDisplayMetrics(){
		DisplayMetrics metrics = new DisplayMetrics();
		WindowManager manager =(WindowManager) ApplicationCore.getInstance().getSystemService(Context.WINDOW_SERVICE);
		manager.getDefaultDisplay().getMetrics(metrics);
		return metrics;
	}
}
