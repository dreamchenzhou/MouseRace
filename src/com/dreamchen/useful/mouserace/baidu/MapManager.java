package com.dreamchen.useful.mouserace.baidu;

import topevery.android.framework.map.MapValue;
import topevery.android.framework.map.OnCompletedListener;
import android.content.Context;
import android.content.Intent;

import com.dreamchen.useful.mouserace.baidu.LocationTrans.TransPoint;

public class MapManager
{
	public static MapManager value = new MapManager();
	private OnCompletedListener onCompletedListener = null;

	public void show(Context context)
	{
		MapValue mapValue = new MapValue();
		show(context, mapValue);
	}

	public void show(Context context, MapValue mapValue)
	{
		Intent intent = new Intent();
		intent.putExtra("SelectResult", mapValue);
		// intent.setClass(context, MapActivity.class);
		intent.setClass(context, BaiduMapActivity.class);
		context.startActivity(intent);
	}

	public void show(Context context, MapValue mapValue, OnCompletedListener listener)
	{
		onCompletedListener = listener;
		show(context, mapValue);
	}

	public void setOnCompletedListener(OnCompletedListener listener)
	{
		this.onCompletedListener = listener;
	}

	public void onCompleted(MapValue mapValue)
	{
		if (this.onCompletedListener != null)
		{
			TransPoint transPoint = TransPoint.transBaidu(mapValue.latitude_bd, mapValue.longitude_bd);

			mapValue.latitude_qq = transPoint.latitude_qq;
			mapValue.longitude_qq = transPoint.longitude_qq;

			mapValue.geoY = transPoint.latitude;
			mapValue.geoX = transPoint.longitude;

			mapValue.absX = transPoint.absX;
			mapValue.absY = transPoint.absY;

			this.onCompletedListener.onCompleted(mapValue);
			this.onCompletedListener = null;
		}
	}
}
