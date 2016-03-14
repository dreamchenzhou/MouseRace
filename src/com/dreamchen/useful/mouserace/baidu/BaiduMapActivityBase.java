package com.dreamchen.useful.mouserace.baidu;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.dreamchen.useful.mouserace.R;

/** 百度地图应用Activity基类 */
public abstract class BaiduMapActivityBase extends BaseActivity2
{
	private boolean is_first = true;
	private LatLng default_latLatLng = new LatLng(23.05, 113.11);

	/** 放大(inBtn) 缩小(outBtn) 按钮 */
	public ImageButton inBtn, outBtn;
	public MapView mMapView;
	public BaiduMap mBaiduMap;
	public UiSettings mUiSettings;

	/** 打点标记，地图上选择位置 */
	public BaiduOverlaySelect mBaiduOverlaySelect;

	/** 是否开启定位图层 */
	public boolean isMyLocationEnabled = true;
	/** 当前位置图标 isMyLocationEnabled为true时有效，即开启百度自带定位图层时有效 */
	public BitmapDescriptor mCurrentMarker;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_baidu_map);

		// PartUtils.initLib(this);

		inBtn = (ImageButton) this.findViewById(R.id.inBtn);
		outBtn = (ImageButton) this.findViewById(R.id.outBtn);
		mMapView = (MapView) this.findViewById(R.id.bmapView);
		mMapView.showZoomControls(false);// 不显示自带的缩放按钮，自定义
		mBaiduMap = mMapView.getMap();

		mBaiduMap.setBuildingsEnabled(false);// 设置显示楼体,true显示，false不显示
		mBaiduMap.setTrafficEnabled(false);// 不显示交通图
		// mMapView.removeViewAt(1);// 隐藏百度logo

		// 监听地图状态变化，手势放大缩小才能监听到，点击放大缩小按钮监听不到
		mBaiduMap.setOnMapStatusChangeListener(onMapStatusChangeListener);

		mUiSettings = mBaiduMap.getUiSettings();
		mUiSettings.setCompassEnabled(true);// 显示指南针

		// 开启定位图层，用百度自带的显示当前位置，
		// 可以自定义图层显示当前位置，自定义时mBaiduMap.setMyLocationEnabled(false);
		mBaiduMap.setMyLocationEnabled(isMyLocationEnabled);
		// 定义当前位置图标，为null则显示百度自带图标
		// mCurrentMarker =
		// BitmapDescriptorFactory.fromResource(R.drawable.baidu_location_mark);
		mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(LocationMode.NORMAL, true, mCurrentMarker));

		mBaiduOverlaySelect = new BaiduOverlaySelect(mMapView);

		// 默认显示到13级别
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(13));

		showDefaultLocation();

	}

	public void showDefaultLocation()
	{
		BDLocation location = null;
		location = BaiduLocationAPI.getLocation();
		if (location != null)
		{
			showLocation();
		}
		else
		{
			moveTo(default_latLatLng);
		}
	}

	/** 点击按钮定位到当前位置 */
	public void myLocation(View view)
	{
		showLoactionDelayed();
	}

	/** 点击按钮放大地图 */
	public void onZoomin(View view)
	{
		float zoom = mBaiduMap.getMapStatus().zoom;
		zoom = zoom + 1;
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(zoom));
		controlZoomShow();
	}

	/** 点击按钮缩小地图 */
	public void onZoomout(View view)
	{
		float zoom = mBaiduMap.getMapStatus().zoom;
		zoom = zoom - 1;
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(zoom));

		controlZoomShow();
	}

	/** 延迟显示当前位置 */
	public void showLoactionDelayed()
	{
		mMapView.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				if (!isFinishing())
				{
					showLocation();
				}
			}
		}, 500);
	}

	/** 显示当前位置 */
	public void showLocation()
	{
		BDLocation location = BaiduLocationAPI.getLocation();
		if (location != null)
		{
			LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

			MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
			// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude()).longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);
			mBaiduOverlaySelect.setLocationLatLng(latLng);
			moveTo(latLng);
		}
		else
		{
			if (is_first)
			{
				// is_first = false;
				// MyLocationData locData = new
				// MyLocationData.Builder().accuracy(location.getRadius())
				// // 此处设置开发者获取到的方向信息，顺时针0-360
				// .direction(100).latitude(default_latLatLng.getLatitude()).longitude(default_latLatLng.getLongitude()).build();
				// mBaiduMap.setMyLocationData(locData);
				// mBaiduOverlaySelect.setLocationLatLng(default_latLatLng);
				// moveTo(default_latLatLng);
			}
		}
	}

	/** 移动到点 */
	public void moveTo(double lat, double lng)
	{
		LatLng ll = new LatLng(lat, lng);
		moveTo(ll);
	}

	/** 移动到点 */
	public void moveTo(BDLocation location)
	{
		LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
		moveTo(ll);
	}

	/** 移动到点 */
	public void moveTo(LatLng latLng)
	{
		MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);
		mBaiduMap.animateMapStatus(u);
	}

	/**
	 * 1普通地图，2卫星图
	 * */
	public void setMapType(int mapType)
	{
		if (mapType == 1)
		{
			mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		}
		else if (mapType == 2)
		{
			mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
		}
	}

	/**
	 * true显示交通图，false不显示交通图
	 * */
	public void setTraffic(boolean enabled)
	{
		mBaiduMap.setTrafficEnabled(enabled);
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		// activity 暂停时同时暂停地图控件
		mMapView.onPause();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		// activity 恢复时同时恢复地图控件
		mMapView.onResume();
	}

	@Override
	public void onDestroy()
	{
		if (mCurrentMarker != null)
		{
			mCurrentMarker.recycle();
		}

		mBaiduOverlaySelect.onDestroy();

		super.onDestroy();
		// activity 销毁时同时销毁地图控件
		mMapView.onDestroy();
	}

	/**
	 * 根据地图状(大小级别)态判断放大缩小按钮是否可用
	 * 
	 * 手势放大缩小才能监听到，点击放大缩小按钮监听不到
	 * 
	 * 当前为最大时，放大按钮不可用，缩小按钮可用
	 * 
	 * 当前为最小时，放大按钮可用，缩小按钮不可用
	 * 
	 * */
	public void controlZoomShow()
	{
		float maxZoomLevel = mBaiduMap.getMaxZoomLevel();
		float minZoomLevel = mBaiduMap.getMinZoomLevel();

		float zoom = mBaiduMap.getMapStatus().zoom;
		// 如果当前状态大于等于地图的最大状态，则放大按钮则失效
		if (zoom >= maxZoomLevel)
		{
			inBtn.setEnabled(false);
		}
		else
		{
			inBtn.setEnabled(true);
		}

		// 如果当前状态小于等于地图的最小状态，则缩小按钮失效
		if (zoom <= minZoomLevel)
		{
			outBtn.setEnabled(false);
		}
		else
		{
			outBtn.setEnabled(true);
		}
	}

	/** 地图状态改变相关接口实现 */
	BaiduMap.OnMapStatusChangeListener onMapStatusChangeListener = new BaiduMap.OnMapStatusChangeListener()
	{
		/**
		 * 手势操作地图，设置地图状态等操作导致地图状态开始改变。
		 * 
		 * @param status
		 *            地图状态改变开始时的地图状态
		 */
		@Override
		public void onMapStatusChange(MapStatus arg0)
		{

		}

		/**
		 * 地图状态变化结束
		 * 
		 * @param status
		 *            地图状态改变结束时的地图状态
		 */
		@Override
		public void onMapStatusChangeFinish(MapStatus arg0)
		{
			controlZoomShow();
		}

		/**
		 * 地图状态变化中
		 * 
		 * @param status
		 *            当前地图状态
		 */
		@Override
		public void onMapStatusChangeStart(MapStatus arg0)
		{

		}
	};
}
