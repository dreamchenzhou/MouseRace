package com.dreamchen.useful.mouserace.medialoader;

import java.io.File;
import java.util.Date;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.dreamchen.useful.mouserace.PathManager;
import com.dreamchen.useful.mouserace.attach.AttachInfo;
import com.dreamchen.useful.mouserace.global.ApplicationCore;
import com.dreamchen.useful.mouserace.utils.SpUtils;
/**
 * 
 *
 */
public abstract class DownLoadTask extends AsyncTask<AttachInfo, Void, File> {


	@Override
	protected File doInBackground(AttachInfo... params) {
		AttachInfo info = params[0];
		String url = info.getUri();
		String fileName = info.getName();
		int type = info.getType();
		if(TextUtils.isEmpty(url)){
			return null;
		}
		File file = null;
		String path = "";
		try {
			// ram 内存
			file = FileRamCache.getInstance().getFile(url);
			// sdcard
			String sdcardPath = SpUtils.getCachePath(ApplicationCore.getInstance(), url);
			if(file!=null){
				return file;
			}else if(!TextUtils.isEmpty(sdcardPath)&&new File(sdcardPath).exists()&&new File(sdcardPath).length()!=0){
				// 获取sd卡缓存
				File sdcardFile =new File(sdcardPath);
				cacheRam(sdcardFile, url);
				return sdcardFile;
			}else{ // 下载
				if(TextUtils.isEmpty(fileName)){
					fileName = String.valueOf(new Date().getTime());
				}
				String dir = PathManager.getMediaPath();
				File downFile = new File(dir, fileName);
				if (!downFile.exists()) {
					downFile.createNewFile();
				}
				path = DownUtils.DownLoadMedia(url, downFile.getAbsolutePath());
				cache(path, url);
				return downFile;
			}
		} catch (Exception e) {
			e.printStackTrace();
			onTaskFailed();
		}
		return file;
	}

	
	private void cacheRam(File file,String key){
		if(file!=null&&file.exists()){
			if(FileRamCache.getInstance().maxSize()>file.length()){
				FileRamCache.getInstance().put(key, file);
			}
		}
	}
	
	private void cache(String path,String key){
		SpUtils.putCachePath(ApplicationCore.getInstance(), key, path);
		FileCache.add(path);
		File file = new File(path);
		cacheRam(file, key);
	}
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		onTaskPre();
	}

	@Override
	protected void onPostExecute(File result) {
		super.onPostExecute(result);
		onTaskPost(result);
	}

	public abstract void onTaskPre();

	public abstract void onTaskPost(File result);

	public abstract void onTaskFailed();
}
