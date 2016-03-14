package com.dreamchen.useful.mouserace.baidu;

import javax.microedition.khronos.opengles.GL10;

import topevery.android.framework.map.MapValue;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap.OnMapDrawFrameCallback;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.model.LatLng;
import com.dreamchen.useful.mouserace.R;

/** 百度地图应用Activity */
public class BaiduMapActivity extends BaiduMapActivityBase implements OnMapDrawFrameCallback, OnMapStatusChangeListener,
OnClickListener
{
	private MapValue selectResult = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// 显示级别到最大
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(18));
				
		selectResult = (MapValue) getIntent().getSerializableExtra("SelectResult");
		if (selectResult != null)
		{
			if (selectResult.latitude_bd > 0 && selectResult.longitude_bd > 0)
			{
				mBaiduOverlaySelect.mark(selectResult.latitude_bd, selectResult.longitude_bd);

				if (selectResult.tag != null)
				{
					String str = selectResult.tag.toString();
					if (str.equalsIgnoreCase("路线导航"))
					{
						BDLocation location = BaiduLocationAPI.getLocation();
						if (location != null)
						{
							LatLng begin = new LatLng(location.getLatitude(), location.getLongitude());
							LatLng end = new LatLng(selectResult.latitude_bd, selectResult.longitude_bd);

						}
						else
						{
							Toast.makeText(this, "获取不到当前位置", Toast.LENGTH_SHORT).show();
							moveTo(selectResult.latitude_bd, selectResult.longitude_bd);
						}
					}
					else
					{
						moveTo(selectResult.latitude_bd, selectResult.longitude_bd);
					}
				}
				else
				{
					moveTo(selectResult.latitude_bd, selectResult.longitude_bd);
				}
			}
		}

		mBaiduMap.setOnMapStatusChangeListener(this);
		mBaiduMap.setOnMapDrawFrameCallback(this);
		
		// 重新初始化titltebar
//		mAbTitleBar.removeAllViews();
//		mAbTitleBar.setVisibility(View.VISIBLE);
//		mAbTitleBar.setBackgroundResource(R.drawable.main_title_bar_bg);
//		View view = LayoutInflater.from(this).inflate(R.layout.titile_bar_normal, null);
//		view.findViewById(R.id.btn_left).setOnClickListener(this);
//		Button btn_right = (Button) view.findViewById(R.id.btn_right);
//		btn_right.setText("确定");
//		btn_right.setOnClickListener(this);
//		TextView title = (TextView) view.findViewById(R.id.txt_title);
//		title.setText("地图浏览");
//		mAbTitleBar.addView(view, 0);
	}

	@Override
	public void finish()
	{
		super.finish();
	}

	// 返回接口回调数据
	private void OnCallBack(){
		if (mBaiduOverlaySelect.isChanged)
		{
			LatLng latLng = mBaiduOverlaySelect.latLng;
			selectResult = new MapValue();
			selectResult.longitude_bd = latLng.longitude;
			selectResult.latitude_bd = latLng.latitude;

			selectResult.partCode = mBaiduOverlaySelect.f_OBJCODE;
			selectResult.posDesc = mBaiduOverlaySelect.f_OBJPOS;
			selectResult.partId = mBaiduOverlaySelect.f_guid;
			MapManager.value.onCompleted(selectResult);
		}
		finish();
	}
	
	@Override
	public void onMapStatusChange(MapStatus arg0)
	{

	}

	@Override
	public void onMapStatusChangeFinish(MapStatus status)
	{
	}

	@Override
	public void onMapStatusChangeStart(MapStatus arg0)
	{

	}

	@Override
	public void onMapDrawFrame(GL10 arg0, MapStatus arg1)
	{

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.btn_right:
			OnCallBack();
			break;
		default:
			break;
		}
		
	}
}
