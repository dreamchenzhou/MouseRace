package com.dreamchen.useful.mouserace.baidu;

import topevery.android.core.MsgBox;
import android.app.Activity;
import android.content.DialogInterface;
import android.widget.EditText;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

/** 根据经纬度获取地址信息，或者根据地址获取经纬度 */
public class BaiduGeoCoder implements OnGetGeoCoderResultListener
{
	public GeoCoder mSearch;

	private Activity mActivity;
	private EditText editText;

	public BaiduGeoCoder(Activity mActivity, EditText editText)
	{
		// 初始化搜索模块，注册事件监听
		mSearch = GeoCoder.newInstance();
		mSearch.setOnGetGeoCodeResultListener(this);
		this.mActivity = mActivity;
		this.editText = editText;
	}

	public void reverseGeoCode(double lat, double lng)
	{
		LatLng latLng = new LatLng(lat, lng);
		mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
	}

	public void reverseGeoCode(LatLng latLng)
	{
		mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
	}

	/** 经纬度信息 */
	@Override
	public void onGetGeoCodeResult(GeoCodeResult result)
	{
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR)
		{// 抱歉，未能找到结果
			return;
		}
		// LatLng latLng = result.getLocation();
	}

	/** 地址信息 */
	@Override
	public void onGetReverseGeoCodeResult(final ReverseGeoCodeResult result)
	{
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR)
		{// 抱歉，未能找到结果

			return;
		}
		// result.getAddress();

		if (!mActivity.isFinishing())
		{
			if (!editText.getText().toString().equals(""))
			{
				MsgBox.askYesNo(mActivity, "是否更换地址信息", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						editText.setText(result.getAddress());
					}
				}, null);
			}
			else
			{
				editText.setText(result.getAddress());
			}

		}
	}

	public void destroy()
	{
		mSearch.destroy();
	}
}
