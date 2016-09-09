package com.dreamchen.useful.mouserace.medialoader;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicLong;

import android.text.TextUtils;

/**
 * 
 * 文件缓存FILO
 * 改进，key加密
 */
public class FileCache {
	private static final Map<File, Long> mLastUseageDates = Collections
			.synchronizedMap(new HashMap<File, Long>());

	private static final AtomicLong mCacheSize = new AtomicLong();

	private static String mCachePath = null;

	private static long MAX_SIZE = 50 * 1024 * 1024;

	public static final long MIN_SIZE = 1*1024*1024;
	
	/**
	 * the unit of the {@linkplain maxCacheSize}is byte;
	 * 
	 * if you want the 10MB,then the {@linkplain maxCacheSize} = 10*1024*1024;
	 * 
	 * @param maxCacheSize
	 */
	public static void setMaxSize(long maxCacheSize) {
		MAX_SIZE = maxCacheSize;
	}

	public static void seCacheFile(String path) {
		mCachePath = path;
	}

	/**
	 * initial in Application
	 * @param cachePath
	 * @param maxSize
	 */
	public static void init(String cachePath,long maxSize){
		if(TextUtils.isEmpty(cachePath)){
			throw new NullPointerException();
		}
		mCachePath = cachePath;
		MAX_SIZE = maxSize;
		if(MAX_SIZE<MIN_SIZE){
			MAX_SIZE = MIN_SIZE;
		}
		calculateSizeAndFillUseageMap();
	}
	
	/**
	 * calculate the mount of {@link mCacheSize} 
	 * and initial the {@link mLastUseageDates}
	 */
	private static void calculateSizeAndFillUseageMap() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				File file = new File(mCachePath);
				if (!file.exists()) {
					file.mkdirs();
					return;
				} else {
					File files[] = file.listFiles();
					long currentSize = 0;
					for (int i = 0; i < files.length; i++) {
						File childFile = files[i];
						currentSize += childFile.length();
						mLastUseageDates.put(childFile,
								childFile.lastModified());
					}
					mCacheSize.set(currentSize);
				}
			}
		}).start();
	}

	/**
	 * add file
	 * @param path
	 */
	public static void add(String path) {
		if (TextUtils.isEmpty(path)) {
			return;
		}
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		long deltaSize = file.length();
		long currentSize = mCacheSize.get();
		while (deltaSize + currentSize > MAX_SIZE) {
			long freeSize = removeMostLongUsageFile();
			if (freeSize == -1) {
				break;
			}
			currentSize = mCacheSize.addAndGet(-freeSize);
		}
		mCacheSize.addAndGet(deltaSize);
		mLastUseageDates.put(file, file.lastModified());
	};

	private static long removeMostLongUsageFile() {
		if (mLastUseageDates.isEmpty()) {
			return -1;
		}
		long theMostLongUsageTime = 0l;
		File theMostLongFile = null;
		synchronized (mLastUseageDates) {
			int length = mLastUseageDates.size();
			Iterator<Entry<File, Long>> entryIterator = mLastUseageDates
					.entrySet().iterator();
			while (entryIterator.hasNext()) {
				Entry<File, Long> set = entryIterator.next();
				if (theMostLongFile == null) {
					theMostLongFile = set.getKey();
					theMostLongUsageTime = set.getValue();
				} else {
					File file = set.getKey();
					long time = set.getValue();
					if (time < theMostLongUsageTime) {
						theMostLongFile = file;
						theMostLongUsageTime = time;
					}
				}
			}

			if (theMostLongFile == null) {
				return theMostLongUsageTime;
			} else {
				if (theMostLongFile.exists()) {
					long delta = theMostLongFile.length();
					if (theMostLongFile.delete()) {
						mCacheSize.addAndGet(-delta);
						mLastUseageDates.remove(theMostLongFile);
					}
				} else {
					theMostLongUsageTime = 0l;
					mLastUseageDates.remove(theMostLongFile);
				}
			}
		}
		return theMostLongUsageTime;
	}
}
