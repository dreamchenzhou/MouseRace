package com.dreamchen.useful.mouserace.multipic;

import android.widget.ImageView;

import com.dreamchen.useful.mouserace.multipic.interfaces.LoadLocalInterface;
import com.dreamchen.useful.mouserace.multipic.interfaces.SwitchInterface;

public class BitmapLoader {
	
	/**
	 * 
	 * @param path 本地路径
	 * @param img 图片显示的view
	 * @param switcher 线程开关
	 * @param localInterface 回调接口
	 */
	public static void loadImage(String path,ImageView img,SwitchInterface switcher,LoadLocalInterface localInterface){
		DecodeTask decodeTask = new DecodeTask(img, path, switcher);
		decodeTask.setOnLoadLocalInterface(localInterface);
		decodeTask.execute();
	}
}
