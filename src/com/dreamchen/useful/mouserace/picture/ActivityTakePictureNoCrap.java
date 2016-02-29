package com.dreamchen.useful.mouserace.picture;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode.VmPolicy;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.dreamchen.useful.mouserace.PathManager;
import com.dreamchen.useful.mouserace.R;
import com.dreamchen.useful.mouserace.base.BaseActivity;
import com.dreamchen.useful.mouserace.utils.BitmapUtils;

/**
 * 相机获取图片不裁剪
 * @author yong.chen
 *
 */
public class ActivityTakePictureNoCrap extends BaseActivity implements
		OnClickListener {

	public static final int SELECT_PIC_KITKAT = 40;
	public static final int IMAGE_REQUEST_CODE = 41;
	public static final int CAMERA_REQUEST_CODE = 42;
	public static final int RESULT_REQUEST_CODE = 43;

	ImageView img;

	private File file;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_local_picture);
		findViewById(R.id.btn_start).setOnClickListener(this);
		img = (ImageView) findViewById(R.id.img);
		file = new File(PathManager.getImageFileByTime());
	}

	@Override
	public void onClick(View arg0) {
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0); 
		 intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
		startActivityForResult(intent, CAMERA_REQUEST_CODE); 
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		Uri uri=null;
		if(resultCode == Activity.RESULT_OK){
			switch (requestCode) {
			case CAMERA_REQUEST_CODE:
				try {
					img.setImageBitmap(BitmapUtils.decodeBitmap(file.getPath()));
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
//					this.finish();
				}
				break;
				
			default:
				break;
			}
		}
	}
}
