package com.dreamchen.useful.mouserace.bitmap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.text.TextUtils;
import android.util.Log;

public class BitmapUtils {
	public static void zoomBitmap() {

	}

	public static void shrinkBitmap() {

	}

	/**
	 * 缩放图片到固定大小，覆盖原图
	 * @param path 图片路径
	 * @param width
	 * @param height
	 * @throws Exception (oom)
	 */
	public static void reSizeBitmap(String path, int width, int height) throws Exception {
		if(TextUtils.isEmpty(path)){
			return ;
		}
		Bitmap temp = BitmapFactory.decodeFile(path);
		Matrix matrix = new Matrix();
		float scale1 = (float) height / (float) temp.getHeight();
		float scale2 = (float) width / (float) temp.getWidth();
		matrix.setScale(scale1, scale2);
		// 图片已经缩放，可以返回temp
		temp = Bitmap.createBitmap(temp, 0, 0, temp.getWidth(),
				temp.getHeight(), matrix, false);
		
		// 下面是覆盖原图，也可以选择去掉覆盖。
		OutputStream stream = null;
		try {
			// 覆盖原来的图片
			stream = new FileOutputStream(path);
			temp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
			stream.flush();
			stream.close();
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				stream.flush();
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	
	/**
	 * 根据图片大小，按比例缩放
	 * @param path
	 * @param width
	 * @param height
	 * @return
	 * @throws Exception (oom)
	 */
	public static Bitmap getBitmapByPath(String path,int width,int height) throws Exception{
		if(TextUtils.isEmpty(path)){
			return null;
		}
		Bitmap bitmap = null;
		BitmapFactory.Options opts = new Options();
		opts.inJustDecodeBounds = false;
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
		if("imge/png".equalsIgnoreCase(opts.outMimeType)){
			// png格式图片带透明通道
			opts.inPreferredConfig = Config.ARGB_8888;
		}else{
			// 其他图片格式不带透明通道，每个像素点不需要占用那么多字节
			opts.inPreferredConfig = Config.RGB_565;
		}
		try {
			bitmap = BitmapFactory.decodeStream(new FileInputStream(path), null, opts);
		} catch (Exception e) {
			throw e;
		}
		return bitmap;
	}
}
