package com.dreamchen.useful.mouserace.medialoader;

import java.io.File;

import topevery.android.framework.utils.TextUtils;
import android.annotation.SuppressLint;
import android.util.LruCache;

/**
 * 
 * 内存缓存 LRU
 * 
 * 改进，key加密
 */
@SuppressLint("NewApi")
public class FileRamCache extends LruCache<String, File> {

	private static FileRamCache fileRamCache;
	private static long MaxSize = Runtime.getRuntime().maxMemory() / 8;
	public static FileRamCache getInstance() {
		if (fileRamCache == null) {
			// Log.e("dream", "容器大小："+Runtime.getRuntime().maxMemory()/8);
			fileRamCache =new FileRamCache(
					(int) MaxSize);
		} 
		return fileRamCache;
		
	}

	/**
	 * Runtime.getRuntime().maxMemory()/8 图片占用程序可最大使用的内存的8分之一。
	 * 
	 * @param maxSize
	 */
	private FileRamCache(int maxSize) {
		super(maxSize);
	}
	
	@Override
	protected int sizeOf(String key, File file) {
		return (int) file.length();
	}

	public File getFile(String path) {
		if (TextUtils.isEmpty(path)) {
			return null;
		}
		File file = null;
		file = get(path);
		if (file == null) {
			// 清理坏缓存
			remove(path);
			return null;
		} else {
			put(path, file);
		}
		return file;
	}
}
