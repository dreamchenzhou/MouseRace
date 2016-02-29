package com.dreamchen.useful.mouserace.picture;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.dreamchen.useful.mouserace.PathManager;
import com.dreamchen.useful.mouserace.R;
import com.dreamchen.useful.mouserace.base.BaseActivity;
import com.dreamchen.useful.mouserace.utils.BitmapUtils;
import com.dreamchen.useful.mouserace.utils.PictureUtils;

/**
 * 相机获取图片裁剪
 * @author yong.chen
 *
 */
public class ActivityTakePictureCrap extends BaseActivity implements
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
				startPhotoZoom(Uri.fromFile(file));
				break;
				
			case RESULT_REQUEST_CODE:
				if (data != null) {
					Bundle bundle = data.getExtras();
					Bitmap map = bundle.getParcelable("data");
					img.setImageBitmap(map);
				}
				break;
			default:
				break;
			}
		}
	}
	
	/**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		if (uri == null) {
			Log.i("tag", "The uri is not exist.");
			return;
		}

		Intent intent = new Intent("com.android.camera.action.CROP");
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
			String url = PictureUtils.getPath(this, uri);
			intent.setDataAndType(Uri.fromFile(new File(url)), "image/*");
		} else {
			intent.setDataAndType(uri, "image/*");
		}

		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 200);
		intent.putExtra("outputY", 200);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, RESULT_REQUEST_CODE);
	}
}
