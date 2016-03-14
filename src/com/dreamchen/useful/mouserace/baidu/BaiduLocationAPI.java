package com.dreamchen.useful.mouserace.baidu;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

/***/
public class BaiduLocationAPI
{
	/**
	 * bd09ll（百度经纬度坐标）
	 * 
	 * bd09mc(bd09)（百度摩卡托坐标）
	 * 
	 * gcj02（国测局加密坐标）
	 * 
	 * wgs84（gps设备获取的坐标）标准经纬度
	 * */

	private static LocationClient mLocationClient;
	/** 表示获取不到设备gps或者网络gps */
	private static final double value_default = 4.9E-324;
	// private static double value_change = 0;

	private static ArrayList<BaiduLocationListener> listeners = new ArrayList<BaiduLocationAPI.BaiduLocationListener>();

	/** 初始化 */
	public static void initialize(Context context)
	{
		String tempcoor = "bd09ll";// bd09ll, bd09,gcj02

		mLocationClient = new LocationClient(context);
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		option.setCoorType(tempcoor);// 返回的定位结果是百度经纬度，默认值gcj02
		option.setScanSpan(1000);// 设置发起定位请求的间隔时间为1000ms
		option.setIsNeedAddress(true);
		option.setAddrType("all");
		option.setOpenGps(true);
		option.setNeedDeviceDirect(true);
		mLocationClient.setLocOption(option);
		mLocationClient.registerLocationListener(mBdLocationListener);
	}

	/** 启动定位服务 */
	public static void start()
	{
		mLocationClient.start();
	}

	/** 停止定位服务 */
	public static void stop()
	{
		mLocationClient.stop();
	}

	public static boolean isStarted()
	{
		return mLocationClient.isStarted();
	}

	/** 获取当前location */
	public static BDLocation getLocation()
	{
		BDLocation location = null;
		if (validate())
		{
			location = mLocationClient.getLastKnownLocation();
		}
		// 23.05, 113.11
		if (location != null)
		{
			// location.setLatitude(23.05);
			// location.setLongitude(113.11);

			// location.setLatitude(23.232297);
			// location.setLongitude(112.985248);// 乐平

			// location.setLatitude(22.991842);// 石湾
			// location.setLongitude(113.100149);
		}
		return location;

		// return null;
	}

	private static int locType = 0;

	/** 判断是否gps是否有效 */
	private static boolean validate()
	{
		// 判断getLocType BDLocation.TypeGpsLocation
		// BDLocation.TypeNetWorkLocation
		// 或者判断 getLongitude getLatitude，当获取不到设备gps或者网络gps时返回4.9E-324

		// return (value_change != 0 && value_change != value_default);

		return locType == BDLocation.TypeGpsLocation || locType == BDLocation.TypeNetWorkLocation;
	}

	private static BDLocationListener mBdLocationListener = new BDLocationListener()
	{
		@Override
		public void onReceiveLocation(BDLocation location)
		{
			// value_change = location.getLongitude();

			if (location != null)
			{
				locType = location.getLocType();
				if (validate())
				{
					onBDReceiveLocation(location);
				}
			}
			else
			{
				locType = 0;
			}
		}
	};

	private static void onBDReceiveLocation(BDLocation location)
	{
		if (validate())
		{
			for (BaiduLocationListener listener : listeners)
			{
				if (listener != null)
				{
					listener.onBDReceiveLocation(location);
				}
			}
		}
	}

	public static void addBaiduLocationListener(BaiduLocationListener listener)
	{
		if (listener != null && !listeners.contains(listener))
		{
			listeners.add(listener);
		}
	}

	public static void removeBaiduLocationListener(BaiduLocationListener listener)
	{
		if (listener != null && listeners.contains(listener))
		{
			listeners.remove(listener);
		}
	}

	public static interface BaiduLocationListener
	{
		public void onBDReceiveLocation(BDLocation location);
	}

	public static void logMsg(BDLocation location)
	{
		StringBuffer sb = new StringBuffer(256);
		sb.append("time : ");
		sb.append(location.getTime());
		sb.append("\nerror code : ");
		sb.append(location.getLocType());
		sb.append("\nlatitude : ");
		sb.append(location.getLatitude());
		sb.append("\nlontitude : ");
		sb.append(location.getLongitude());
		sb.append("\nradius : ");
		sb.append(location.getRadius());

		if (location.getLocType() == BDLocation.TypeGpsLocation)
		{
			sb.append("\nspeed : ");
			sb.append(location.getSpeed());
			sb.append("\nsatellite : ");
			sb.append(location.getSatelliteNumber());
			sb.append("\ndirection : ");
			sb.append("\naddr : ");
			sb.append(location.getAddrStr());
			sb.append(location.getDirection());
		}
		else if (location.getLocType() == BDLocation.TypeNetWorkLocation)
		{
			sb.append("\naddr : ");
			sb.append(location.getAddrStr());
			// 运营商信息
			sb.append("\noperationers : ");
			sb.append(location.getOperators());
		}
		Log.i("BaiduLocationApiDem", sb.toString());
	}
}
