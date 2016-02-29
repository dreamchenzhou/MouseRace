package com.dreamchen.useful.mouserace.multipic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dreamchen.useful.mouserace.R;
import com.dreamchen.useful.mouserace.base.BaseActivity;
import com.dreamchen.useful.mouserace.multipic.ablum.ImageBucket;
import com.dreamchen.useful.mouserace.multipic.ablum.ImageItem;
import com.dreamchen.useful.mouserace.multipic.ablum.MediaHelper;
import com.dreamchen.useful.mouserace.multipic.interfaces.LoadLocalInterface;
import com.dreamchen.useful.mouserace.multipic.interfaces.SwitchInterface;
import com.dreamchen.useful.mouserace.test.TestMultiPicsActivity;
import com.dreamchen.useful.mouserace.utils.DisplayUtils;

public class ActivityChooseMultiPics extends BaseActivity implements
		OnClickListener, LoadLocalInterface,OnItemClickListener {
	private static final int 	REQUEST_CODE_PREVIEW = 10;
	
	private GridView mGridView;

	private AlbumAdapter mAdapter;

	private ActivityChooseMultiPics mContext;

	private List<ImageItem> mData;
	/**
	 * 最大能够选择的相片数量
	 */
	private int maxNum = 0;

	private int count = 0;

	private ArrayList<String> pathList = new ArrayList<String>();

	private List<Integer> positionList = new ArrayList<Integer>();

	private Button btn_right;

	private Button btn_buckets;
	
	private Button btn_preview;
	
	private SwitchInterface switcher;

	private View layout_bottom;

	private Stack<Integer> animStack = new Stack<Integer>();

	private boolean isAnim = false;

	private PopupWindow bucketWindow;
	
	private ListView popListView;
	
	private List<ImageBucket> buckets;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_album);
		switcher = new DecoderSwitch();
		switcher.on();
		maxNum = getIntent().getIntExtra("max", 0);
		mGridView = (GridView) findViewById(R.id.grid_album);
		layout_bottom = findViewById(R.id.layout_bottom);
		findViewById(R.id.btn_back).setOnClickListener(this);
		btn_buckets = (Button) findViewById(R.id.btn_buckets);
		btn_buckets.setOnClickListener(this);
		btn_right = (Button) findViewById(R.id.btn_right);
		btn_right.setOnClickListener(this);
		btn_preview = (Button) findViewById(R.id.btn_preview);
		btn_preview.setOnClickListener(this);
		mContext = this;
		MediaHelper.init(mContext);
		mData = MediaHelper.getAllImageList(true);
		buckets = MediaHelper.getListBuckets(false);
		mAdapter = new AlbumAdapter();
		mGridView.setAdapter(mAdapter);
	}

	class AlbumAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mData.size();
		}

		@Override
		public ImageItem getItem(int position) {
			return mData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.grid_item_album, null);
				viewHolder.img_pic = (ImageView) convertView
						.findViewById(R.id.img_pic);
				viewHolder.img_selector = (ImageButton) convertView
						.findViewById(R.id.img_selector);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			// 重新初事化
			{
				viewHolder.img_pic.setImageBitmap(null);
				viewHolder.img_selector.setSelected(false);
				viewHolder.img_selector.setVisibility(View.VISIBLE);
			}
			viewHolder.img_selector.setTag(position);
			viewHolder.img_selector.setOnClickListener(mContext);
			if (positionList.contains(position)) {
				viewHolder.img_selector.setSelected(true);
			} else {
				viewHolder.img_selector.setSelected(false);
			}
			viewHolder.img_pic.setTag(R.id.img_selector,
					viewHolder.img_selector);
			if (TextUtils.isEmpty(getItem(position).getThumbNail())) {
				// viewHolder.img_pic.setImageBitmap(BitmapCache.getInstance().
				// getBitmap(getItem(position).getPath()));
				viewHolder.img_pic.setTag(getItem(position).getPath());
				BitmapLoader.loadImage(getItem(position).getPath(),
						viewHolder.img_pic, switcher, mContext);
			} else {
				// viewHolder.img_pic.setImageBitmap(BitmapCache.getInstance().
				// getBitmap(getItem(position).getThumbNail()));
				viewHolder.img_pic.setTag(getItem(position).getThumbNail());
				BitmapLoader.loadImage(getItem(position).getThumbNail(),
						viewHolder.img_pic, switcher, mContext);
			}
			return convertView;
		}

		class ViewHolder {
			public ImageView img_pic;

			public ImageButton img_selector;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_selector:
			ImageButton view = (ImageButton) v;
			int position = (Integer) v.getTag();
			ImageItem item = mData.get(position);
			if (count >= maxNum) {
				if (view.isSelected()) {
					view.setSelected(false);
					count--;
					pathList.remove(item.getPath());
					positionList.remove(new Integer(position));
				} else {
					return;
				}
			} else {
				if (view.isSelected()) {
					view.setSelected(false);
					count--;
					pathList.remove(item.getPath());
					positionList.remove(new Integer(position));
				} else {
					view.setSelected(true);
					pathList.add(item.getPath());
					positionList.add(new Integer(position));
					count++;
				}
			}
			if (count > 0) {
				btn_right.setText(String.format("%s/%s确定", count, maxNum));
			}
			
			if(count<=0){
				btn_preview.setEnabled(false);
			}else{
				btn_preview.setEnabled(true);
				btn_preview.setText(String.format("(%s)预览", count));
			}
			break;

		case R.id.btn_buckets:
			initPopUpWindow();
			showPopWindow();
			break;
		case R.id.btn_preview:
			Intent preview_Intent = new Intent(this,ActivityPreView.class);
			preview_Intent.putStringArrayListExtra("paths", pathList);
			startActivityForResult(preview_Intent, REQUEST_CODE_PREVIEW);
			break;
		case R.id.btn_back:
			finish();
			break;
		case R.id.btn_right:
			Intent intent = new Intent(this, TestMultiPicsActivity.class);
			String[] temp = new String[pathList.size()];
			for (int i = 0; i < temp.length; i++) {
				temp[i] = pathList.get(i);
			}
			intent.putExtra("data", temp);
			setResult(RESULT_OK, intent);
			finish();
			break;
		default:
			break;
		}

	}

	@Override
	public void finish() {
		super.finish();
		switcher.off();
	}

	@Override
	public void onLoadLocalFailed(ImageView imageView) {
		ImageButton checkBox = (ImageButton) imageView
				.getTag(R.id.img_selector);
		checkBox.setVisibility(View.GONE);
	}

	@Override
	public void onLoadLocalSuccess(ImageView imageView) {

	}
	
	private void initPopUpWindow(){
		bucketWindow = new PopupWindow(this);
		View popView= LayoutInflater.from(this).inflate(R.layout.pop_list, null);
		bucketWindow.setContentView(popView);
		bucketWindow.setOutsideTouchable(true);
		bucketWindow.setFocusable(true);
		bucketWindow.update();
		popListView = (ListView) popView.findViewById(R.id.list_pop);
		popListView.setAdapter(new PopAdapter());
		popListView.setOnItemClickListener(this);
		bucketWindow.setWidth(DisplayUtils.dip2px(this, 100));
		bucketWindow.setHeight(DisplayUtils.dip2px(this, 200));
	}

	public void showPopWindow(){
//		int[] location = new int[2];
//		btn_buckets.getLocationOnScreen(location);
//		bucketWindow.showAtLocation(btn_buckets, Gravity.TOP, location[0], location[1]+DisplayUtils.dip2px(this, 48));
		bucketWindow.showAsDropDown(btn_buckets,DisplayUtils.dip2px(this, 10),0);
	}
	
	class PopAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return buckets.size();
		}

		@Override
		public ImageBucket getItem(int position) {
			return buckets.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View contentView, ViewGroup parent) {
			contentView = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, null);
			TextView txt_name = (TextView) contentView.findViewById(android.R.id.text1);
			txt_name.setText(getItem(position).getName());
			return contentView;
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		ImageBucket bucket = buckets.get(position);
		mData =bucket.getImageList();
		mAdapter.notifyDataSetChanged();
		bucketWindow.dismiss();
	}
	
}
