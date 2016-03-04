package com.dreamchen.useful.mouserace.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class UdpSendService {
	public static void send(String ip){
		try {
			DatagramSocket sender =new DatagramSocket();
			sender.setBroadcast(true);
			InetAddress addr  = InetAddress.getByName(ip);
			
			byte [] data = "hello,udp".getBytes();
			DatagramPacket packet =new 
					DatagramPacket(data, data.length, addr,10003);
			sender.send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
