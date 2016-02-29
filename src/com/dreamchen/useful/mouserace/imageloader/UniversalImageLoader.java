package com.dreamchen.useful.mouserace.imageloader;

import java.io.File;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.dreamchen.useful.mouserace.R;
import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class UniversalImageLoader
{

	/**
	 * 默认显示图片
	 */
	public static int defaultImageId = R.drawable.post_image_loding;
	public static int defaultImageFailId = R.drawable.post_image_loading_failed;
	public static int durationMillis = 500;
	public static int maxImageWidth = 100;
	public static int maxImageHeight = 100;
	public static int compressQuality = 75;

	public static void init(Context context)
	{
		init(context, null);
	}

	public static void init(Context context, String cacheDir)
	{
		com.nostra13.universalimageloader.core.ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(context);
		builder = builder.threadPriority(Thread.NORM_PRIORITY - 2);
		builder = builder.denyCacheImageMultipleSizesInMemory();
		builder = builder.diskCacheFileNameGenerator(new Md5FileNameGenerator());

		builder = builder.memoryCache(new LruMemoryCache(8 * 1024 * 1024));
		builder = builder.memoryCacheSize(8 * 1024 * 1024);
		builder = builder.diskCacheSize(8 * 1024 * 1024);
		builder = builder.diskCacheFileCount(64);

		builder = builder.tasksProcessingOrder(QueueProcessingType.LIFO);

		if (!TextUtils.isEmpty(cacheDir))
		{
			StorageUtils.getOwnCacheDirectory(context, cacheDir);

			builder = builder.diskCache(new UnlimitedDiskCache(new File(cacheDir)));
		}

		ImageLoaderConfiguration config = builder.build();
		ImageLoader.getInstance().init(config);
	}

	public static DisplayImageOptions getDisplayImageOptions(boolean cacheInMemory, boolean cacheOnDisk)
	{
		if (defaultImageFailId == 0)
		{
			defaultImageFailId = defaultImageId;
		}

		com.nostra13.universalimageloader.core.DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
		builder = builder.showImageForEmptyUri(defaultImageId);
		builder = builder.showImageOnFail(defaultImageFailId);
		builder = builder.showImageOnLoading(defaultImageId);
		builder = builder.cacheInMemory(cacheInMemory);
		builder = builder.cacheOnDisk(cacheOnDisk);

		builder = builder.displayer(new RoundedBitmapDisplayer(10));

		DisplayImageOptions mDisplayImageOptions = builder.build();
		return mDisplayImageOptions;
	}

	public static ImageLoader getImageLoader()
	{
		return ImageLoader.getInstance();
	}

	public static void onDestroy()
	{
		ImageLoader mImageLoader = getImageLoader();
		if (mImageLoader != null)
		{
			mImageLoader.clearMemoryCache();
			mImageLoader.clearDiskCache();
		}

		for (int i = 0; i < 3; i++)
		{
			System.gc();
		}
	}

	/**
	 * 获取缓存文件路径
	 * 
	 * @param uri
	 * @return
	 */
	public static String getCachePath(String uri)
	{
		DiskCache discCache = getImageLoader().getDiskCache();
		File imageFile = discCache.get(uri);
		String path = imageFile.getAbsolutePath();
		return path;
	}

	/**
	 * 获取缓存文件路径
	 * 
	 * @param uri
	 * @return
	 */
	public static File getCacheFile(String uri)
	{
		DiskCache discCache = getImageLoader().getDiskCache();
		File imageFile = discCache.get(uri);
		return imageFile;
	}

	public static void displayImage(String uri, ImageView imageView, boolean cacheInMemory, boolean cacheOnDisc)
	{
		DisplayImageOptions mDisplayImageOptions = getDisplayImageOptions(cacheInMemory, cacheOnDisc);

		getImageLoader().displayImage(uri, imageView, mDisplayImageOptions, null);
	}
	
	public static void displayImage(String uri,ImageView imageView){
		displayImage(uri, imageView, true, true);
	}
}
