package com.dreamchen.useful.mouserace.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class UdpReceiverServer extends Thread {

	private Handler mHandler;

	private String ip;
	public UdpReceiverServer(Handler handler,String ip) {
		this.mHandler = handler;
		this.ip = ip;
	}

	@Override
	public void run() {
		try {
			InetAddress address = InetAddress.getByName(ip);
//			MulticastSocket receiver = new MulticastSocket(10003);
			DatagramSocket receiver = new DatagramSocket(10003);
//			receiver.setBroadcast(true);
//			receiver.joinGroup(address);
			byte[] data = new byte[1024];
			DatagramPacket packet = null;
			while (true) {
				packet = new DatagramPacket(data, data.length);
				if (receiver != null) {
					receiver.receive(packet);
				}
				if (packet.getLength() != 0) {
					String string = new String(data, 0, packet.getLength());
					Log.e("dream", "receive data:" + string);
					Message msg = mHandler.obtainMessage(0, string);
					msg.sendToTarget();
				}

			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
