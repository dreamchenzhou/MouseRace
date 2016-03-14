package com.dreamchen.useful.mouserace.baidu;

import java.util.UUID;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMyLocationClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.dreamchen.useful.mouserace.R;

/**
 * 打点标记，地图上选择位置
 * */
public class BaiduOverlaySelect implements OnMarkerClickListener, OnMapClickListener, OnMyLocationClickListener
{
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private BitmapDescriptor mBitmap;
	private Marker mMarker;

	// private MapActivityBase mContext;

	/** 当前gps位置 */
	private LatLng locationLatLng;

	public LatLng latLng;

	public String f_OBJCODE;
	public String f_OBJPOS;
	public UUID f_guid;

	public boolean isChanged = false;

	public BaiduOverlaySelect(MapView mMapView)
	{
		this.mMapView = mMapView;
		this.mBaiduMap = this.mMapView.getMap();
		mBitmap = BitmapDescriptorFactory.fromResource(R.drawable.baidu_pt_mark);
		mBaiduMap.setOnMapClickListener(this);
		mBaiduMap.setOnMyLocationClickListener(this);
		mBaiduMap.setOnMarkerClickListener(this);
	}

	/** 当前gps位置 */
	public void setLocationLatLng(LatLng latLng)
	{
		this.locationLatLng = latLng;
	}

	public void mark(double lat, double lng)
	{
		LatLng latLng = new LatLng(lat, lng);
		mark(latLng);
	}

	public void mark(LatLng latLng)
	{
		this.latLng = latLng;

		if (mMarker == null)
		{
			MarkerOptions ooA = new MarkerOptions();
			ooA = ooA.position(latLng);
			ooA = ooA.icon(mBitmap);
			ooA = ooA.zIndex(100);
			ooA = ooA.draggable(false);

			mMarker = (Marker) (mBaiduMap.addOverlay(ooA));
		}
		else
		{
			mMarker.setPosition(latLng);
		}
		// mContext.moveTo(latLng);
	}

	/** 退出时释放 */
	public void onDestroy()
	{
		if (mBitmap != null)
		{
			mBitmap.recycle();
		}
		if (mMarker != null)
		{
			mMarker.remove();
		}
	}

	@Override
	public boolean onMyLocationClick()
	{
		if (locationLatLng != null)
		{
			mark(locationLatLng);
			isChanged = true;
		}
		return false;
	}

	@Override
	public void onMapClick(LatLng latLng)
	{
		mark(latLng);
		isChanged = true;
	}

	@Override
	public boolean onMapPoiClick(MapPoi poi)
	{
		mark(poi.getPosition());
		isChanged = true;
		return false;
	}

	@Override
	public boolean onMarkerClick(Marker arg0)
	{
		mark(arg0.getPosition());
		isChanged = true;
		return false;
	}
}
