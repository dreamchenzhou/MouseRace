package com.dreamchen.useful.mouserace.picture;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.dreamchen.useful.mouserace.R;
import com.dreamchen.useful.mouserace.base.BaseActivity;
import com.dreamchen.useful.mouserace.utils.PictureUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

/**
 * 本地获取图片裁剪
 * @author yong.chen
 *
 */
public class ActivityLocalPictureCrap extends BaseActivity implements
		OnClickListener {

	public static final int SELECT_PIC_KITKAT = 40;
	public static final int IMAGE_REQUEST_CODE = 41;
	public static final int CAMERA_REQUEST_CODE = 42;
	public static final int RESULT_REQUEST_CODE = 43;

	ImageView img;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_local_picture);
		findViewById(R.id.btn_start).setOnClickListener(this);
		img = (ImageView) findViewById(R.id.img);
	}

	@Override
	public void onClick(View arg0) {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("image/*");
		startActivityForResult(intent, IMAGE_REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case IMAGE_REQUEST_CODE:
				startPhotoZoom(data.getData());
				break;
			case RESULT_REQUEST_CODE:
				if (data != null) {
					Bitmap map = data.getParcelableExtra("data");
					img.setImageBitmap(map);
				}
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

	public DisplayImageOptions getCacheOptions(boolean cacheInSdcard,
			boolean cacheInMemory) {
		com.nostra13.universalimageloader.core.DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
		builder = builder.cacheInMemory(cacheInMemory);
		builder = builder.cacheOnDisk(cacheInSdcard);
		return builder.build();
	}
}
