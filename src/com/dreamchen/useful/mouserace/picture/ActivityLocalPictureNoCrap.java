package com.dreamchen.useful.mouserace.picture;

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
import com.dreamchen.useful.mouserace.utils.BitmapUtils;
import com.dreamchen.useful.mouserace.utils.PictureUtils;

/**
 * 本地获取图片不裁剪
 * 
 * @author yong.chen
 *
 */
public class ActivityLocalPictureNoCrap extends BaseActivity implements
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
		Uri uri = data.getData();
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case IMAGE_REQUEST_CODE:
				if (uri == null) {
					Log.i("tag", "The uri is not exist.");
					return;
				}
				// api 20 android L
				if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
					String url = PictureUtils.getPath(this, uri);
					img.setImageBitmap(BitmapUtils.decodeBitmap(url));
//					img.setImageBitmap(BitmapFactory.decodeFile(url));
					Log.e("dream", "android L or Upper,api="+android.os.Build.VERSION.SDK_INT);
				} else {
					try {
//						method 1
						Uri originalUri = data.getData();
						Bitmap mapBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), originalUri);
						img.setImageBitmap(mapBitmap);
						
//						method 2
//						获取路径
//						String[] proj = { MediaStore.Images.Media.DATA };
//						Cursor cursor = managedQuery(originalUri, proj, null, null,
//								null);
//						int column_index = cursor
//								.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//						cursor.moveToFirst();
//						String path = cursor.getString(column_index);
//						if (!TextUtils.isEmpty(path)) {
//							img.setImageBitmap(BitmapFactory.decodeFile(path));
//						}
						
//						method 3
//						Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
//						img.setImageBitmap(bitmap);
					} catch (Exception e) {
					}
					Log.e("dream", "api="+android.os.Build.VERSION.SDK_INT);
				}
				break;
			}
		}
	}

}
