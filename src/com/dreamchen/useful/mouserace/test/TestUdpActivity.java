package com.dreamchen.useful.mouserace.test;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.dreamchen.useful.mouserace.R;
import com.dreamchen.useful.mouserace.base.BaseActivity;
import com.dreamchen.useful.mouserace.udp.UdpReceiverServer;
import com.dreamchen.useful.mouserace.udp.UdpSendService;

public class TestUdpActivity extends BaseActivity implements OnClickListener{
	
	UdpReceiverServer receicer;
	String ip;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_udp);
		findViewById(R.id.btn_send).setOnClickListener(this);
		findViewById(R.id.btn_stop).setOnClickListener(this);
		//获取wifi服务  
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);  
        //判断wifi是否开启  
        if (!wifiManager.isWifiEnabled()) {  
        wifiManager.setWifiEnabled(true);    
        }  
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();       
        int ipAddress = wifiInfo.getIpAddress();   
        ip = intToIp(ipAddress);
		receicer = new UdpReceiverServer(mHandler,ip);
	}

	 private String intToIp(int i) {       
         
         return (i & 0xFF ) + "." +       
       ((i >> 8 ) & 0xFF) + "." +       
       ((i >> 16 ) & 0xFF) + "." +       
       ( i >> 24 & 0xFF) ;  
    }  
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_send:
			new Thread(){
				public void run() {
					UdpSendService.send(ip);
				};
			}.start();
			if(receicer == null||!receicer.isAlive()){
				receicer = new UdpReceiverServer(mHandler,ip);
				receicer.start();
			}
			break;
		case R.id.btn_stop:
			receicer.interrupt();
		break;

		default:
			break;
		}
	}
	
	private Handler mHandler =new Handler(){
		public void handleMessage(android.os.Message msg) {
			Toast.makeText(TestUdpActivity.this, msg.obj.toString(), 1000).show();
		};
	};
}
