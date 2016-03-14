package com.dreamchen.useful.mouserace.baidu;

import topevery.android.gps.coords.FSTransRegions;
import topevery.android.gps.coords.PointDElement;

/**
 * WGS-84 与 BD-09 相互转换
 * */
public class LocationTrans
{
	private static final double PI = 3.14159265358979324;
	private static final double x_pi = 3.14159265358979324 * 3000.0 / 180.0;

	public static void test()
	{
		// TransPoint tt;
		// TransPoint value_84 = new TransPoint();
		// value_84.longitude = 114.062619444444;
		// value_84.latitude = 22.5514638888889;
		//
		// TransPoint bd = WGS84Bd09(value_84.latitude, value_84.longitude);
		//
		// // TransPoint value_bd = new TransPoint();
		// // value_bd.longitude = 114.07429029133971;
		// // value_bd.latitude = 22.554438537395803;
		//
		// tt = Bd09WGS84(bd.latitude, bd.longitude);

		TransPoint tt = new TransPoint();
		tt.latitude = 22.543437;
		tt.longitude = 113.990833;

		TransPoint bd = Bd09WGS84(tt.latitude, tt.longitude);
	}

	/** WGS-84先转换为GCJ-02，再转换为BD-09 */
	public static TransPoint WGS84Bd09(double wgsLat, double wgsLon)
	{
		/** WGS-84先转换为GCJ-02 */
		TransPoint gcjPoint = gcj_encrypt(wgsLat, wgsLon);
		/** 转换为BD-09 */
		gcjPoint = bd_encrypt(gcjPoint.latitude, gcjPoint.longitude);
		return gcjPoint;
	}

	/** BD-09先转换为GCJ-02，再转换为WGS-84 */
	public static TransPoint Bd09WGS84(double bdLat, double bdLon)
	{
		/** BD-09先转换为GCJ-02 */
		TransPoint gcjPoint = bd_decrypt(bdLat, bdLon);
		/** 再转换为WGS-84 */
		gcjPoint = gcj_decrypt(gcjPoint.latitude, gcjPoint.longitude);
		return gcjPoint;
	}

	/** WGS-84 to GCJ-02 */
	private static TransPoint gcj_encrypt(double wgsLat, double wgsLon)
	{
		TransPoint result = delta(wgsLat, wgsLon);
		result.latitude = result.latitude + wgsLat;
		result.longitude = result.longitude + wgsLon;
		return result;
	}

	/** GCJ-02 to BD-09 **/
	public static TransPoint bd_encrypt(double gcjLat, double gcjLon)
	{
		double x = gcjLon, y = gcjLat;
		double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
		double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);

		TransPoint result = new TransPoint();

		result.longitude = z * Math.cos(theta) + 0.0065;
		result.latitude = z * Math.sin(theta) + 0.006;
		return result;
	}

	/** BD-09 to GCJ-02 */
	public static TransPoint bd_decrypt(double bdLat, double bdLon)
	{
		double x = bdLon - 0.0065, y = bdLat - 0.006;
		double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
		double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
		double gcjLon = z * Math.cos(theta);
		double gcjLat = z * Math.sin(theta);

		TransPoint result = new TransPoint();
		result.longitude = gcjLon;
		result.latitude = gcjLat;
		return result;
	}

	/** GCJ-02 to WGS-84 */
	private static TransPoint gcj_decrypt(double gcjLat, double gcjLon)
	{
		TransPoint result = delta(gcjLat, gcjLon);
		result.latitude = gcjLat - result.latitude;
		result.longitude = gcjLon - result.longitude;
		return result;
	}

	private static TransPoint delta(double lat, double lon)
	{
		double a = 6378245.0; // a: 卫星椭球坐标投影到平面地图坐标系的投影因子。
		double ee = 0.00669342162296594323; // ee: 椭球的偏心率。
		double dLat = transformLat(lon - 105.0, lat - 35.0);
		double dLon = transformLon(lon - 105.0, lat - 35.0);
		double radLat = lat / 180.0 * PI;
		double magic = Math.sin(radLat);
		magic = 1 - ee * magic * magic;
		double sqrtMagic = Math.sqrt(magic);
		dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * PI);
		dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * PI);

		TransPoint result = new TransPoint();
		result.latitude = dLat;
		result.longitude = dLon;
		return result;
	}

	private static double transformLat(double x, double y)
	{
		double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(y * PI) + 40.0 * Math.sin(y / 3.0 * PI)) * 2.0 / 3.0;
		ret += (160.0 * Math.sin(y / 12.0 * PI) + 320 * Math.sin(y * PI / 30.0)) * 2.0 / 3.0;
		return ret;
	}

	private static double transformLon(double x, double y)
	{
		double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(x * PI) + 40.0 * Math.sin(x / 3.0 * PI)) * 2.0 / 3.0;
		ret += (150.0 * Math.sin(x / 12.0 * PI) + 300.0 * Math.sin(x / 30.0 * PI)) * 2.0 / 3.0;
		return ret;
	}

	/** 是否在中国范围内 */
	public static Boolean outOfChina(double lat, double lon)
	{
		if (lon < 72.004 || lon > 137.8347)
			return true;
		if (lat < 0.8293 || lat > 55.8271)
			return true;
		return false;
	}

	public static class TransPoint
	{
		/** 经度 geoX */
		public double longitude;
		/** 纬度 geoY */
		public double latitude;

		/** 经度 qqX */
		public double longitude_qq;
		/** 纬度 qqY */
		public double latitude_qq;

		/** 经度 baiduX */
		public double longitude_bd;
		/** 纬度 baiduY */
		public double latitude_bd;

		/** 绝对坐标 */
		public double absX;
		/** 绝对坐标 */
		public double absY;

		private final static FSTransRegions coordTrans = new FSTransRegions();

		public static TransPoint transBaidu(double latitude_bd, double longitude_bd)
		{
			TransPoint pt = new TransPoint();

			TransPoint pt_84, pt_qq;
			pt_84 = LocationTrans.Bd09WGS84(latitude_bd, longitude_bd);// 把百度经纬度转换为标准经纬度
			pt_qq = LocationTrans.bd_decrypt(latitude_bd, longitude_bd);// 把百度经纬度转换为火星坐标系

			/** 把标准经纬度转换为绝对坐标 */
			PointDElement pointLongLat = new PointDElement(pt_84.longitude, pt_84.latitude);
			PointDElement ptAreaCoor = coordTrans.LonLanToXY(pointLongLat);

			pt.absX = ptAreaCoor.x;
			pt.absY = ptAreaCoor.y;

			pt.longitude = pt_84.longitude;
			pt.latitude = pt_84.latitude;

			pt.longitude_qq = pt_qq.longitude;
			pt.latitude_qq = pt_qq.latitude;

			pt.longitude_bd = longitude_bd;
			pt.latitude_bd = latitude_bd;

			return pt;
		}
	}
}
