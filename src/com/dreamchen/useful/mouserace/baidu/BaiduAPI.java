package com.dreamchen.useful.mouserace.baidu;

import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.baidu.mapapi.map.offline.MKOfflineMapListener;

/** BaiduAPI初始化 */
public class BaiduAPI
{
	/** 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext */
	public static void initialize(Context context)
	{
		SDKInitializer.initialize(context);
		importOfflineData(context);

		BaiduLocationAPI.initialize(context);
		// BaiduLocationAPI.start();
	}

	/**
	 * 导入离线地图数据
	 * 
	 * 没有可导入的离线数据或者已经导入过返回0，否则返回成功导入的离线数据个数(如导入深圳，广州，返回2)
	 * */
	private static int importOfflineData(Context context)
	{
		MKOfflineMap mOffline = new MKOfflineMap();
		mOffline.init(mMKOfflineMapListener);
		int importStatus = mOffline.importOfflineData();
		return importStatus;

		// int num = mOffline.importOfflineData();
		// String msg = "";
		// if (num == 0)
		// {
		// msg = "没有导入离线包，这可能是离线包放置位置不正确，或离线包已经导入过";
		// }
		// else
		// {
		// msg = String.format("成功导入 %s 个离线包，可以在下载管理查看", num);
		// }
		// Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	private static MKOfflineMapListener mMKOfflineMapListener = new MKOfflineMapListener()
	{
		@Override
		public void onGetOfflineMapState(int type, int state)
		{

		}
	};
}
