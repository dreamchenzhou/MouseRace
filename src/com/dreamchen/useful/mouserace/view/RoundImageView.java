package com.dreamchen.useful.mouserace.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Bitmap.Config;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import com.dreamchen.useful.mouserace.R;
import com.dreamchen.useful.mouserace.utils.LogUtils;

@SuppressLint({ "Recycle", "DrawAllocation" }) 
public class RoundImageView extends ImageView {

	private  int mCornerRadius = 10;
	
	private  int mCornerAngle = 8;
	
	private int mRadius = 30;
	
	/**
	 * mType 0 圆角
	 * mType 1 圆形
	 */
	private  int mType = 0;
	
	private Paint mPaint;
	
	private Shader mShader;
	
	public static final int ROUND_TYPE_ROUND = 0;
	
	public static final int ROUND_TYPE_CIRCLE = 1;	
	
	private int minWidth;
	
	public RoundImageView(Context context) {
		super(context);
	}

	public RoundImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray array =  context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
		mCornerRadius = array.getDimensionPixelSize(R.styleable.RoundImageView_corner_radius, 
				(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mCornerRadius, getResources().getDisplayMetrics()));
		mCornerAngle = array.getDimensionPixelSize(R.styleable.RoundImageView_corner_angle,
				(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mCornerRadius, getResources().getDisplayMetrics()));
	
		mType = array.getInt(R.styleable.RoundImageView_type, ROUND_TYPE_ROUND);
	
		array.recycle();
		
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	}

	public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray array =  context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
		mCornerRadius = array.getDimensionPixelSize(R.styleable.RoundImageView_corner_radius, 
				(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mCornerRadius, getResources().getDisplayMetrics()));
		mCornerAngle = array.getDimensionPixelSize(R.styleable.RoundImageView_corner_angle,
				(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mCornerRadius, getResources().getDisplayMetrics()));
	
		mType = array.getInt(R.styleable.RoundImageView_type, ROUND_TYPE_ROUND);
	
		array.recycle();
		
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	}

	public int getmCornerRadius() {
		return mCornerRadius;
	}

	public void setmCornerRadius(int mCornerRadius) {
		this.mCornerRadius = mCornerRadius;
		invalidate();
	}

	public int getmCornerAngle() {
		return mCornerAngle;
	}

	public void setmCornerAngle(int mCornerAngle) {
		this.mCornerAngle = mCornerAngle;
		invalidate();
	}

	public int getmType() {
		return mType;
	}

	public void setmType(int mType) {
		this.mType = mType;
		invalidate();
	}

	public Shader getmShader() {
		return mShader;
	}

	public void setmShader(Shader mShader) {
		this.mShader = mShader;
		invalidate();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width  =MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		
		LogUtils.Log_E("width:"+width);
		LogUtils.Log_E("height:"+height);
		LogUtils.Log_E("raw_measured_width:"+getMeasuredWidth());
		LogUtils.Log_E("SuggestedMinimumHeight:"+getSuggestedMinimumHeight());
		if(mType==ROUND_TYPE_CIRCLE){
			minWidth = Math.min(getMeasuredHeight(), getMeasuredWidth());
			mRadius = minWidth;
			setMeasuredDimension(minWidth, minWidth);
		}
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		if(mShader==null){
			setDefaultShader();
		}
		mPaint.setShader(mShader);
		canvas.save();
		if(mType ==ROUND_TYPE_CIRCLE){
			canvas.drawCircle(getWidth()/2, getHeight()/2, getWidth()/2, mPaint);
		}else if(mType == ROUND_TYPE_ROUND){
			Rect rect = new Rect(0, 0, getWidth(), getHeight());
			RectF rectF = new RectF(rect);
			canvas.drawRoundRect(rectF, mCornerRadius, mCornerRadius, mPaint);
		}
		canvas.restore();
	}
	
	private void setDefaultShader(){
		Bitmap bitmap = drawToBitmap(getDrawable());
		mShader = new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP);
		Matrix matrix = new Matrix();
		float scale = 1f;
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		if(mType==ROUND_TYPE_CIRCLE){
			scale = minWidth*1f/Math.min(width, height);
		}else if(mType ==ROUND_TYPE_ROUND){
			// 如果图片的宽或者高与view的宽高不匹配，计算出需要缩放的比例；
			// 缩放后的图片的宽高，一定要大于我们view的宽高；所以我们这里取大值； 
			scale =Math.max(getWidth()/width, getHeight()/height);
		}
		matrix.setScale(scale, scale);
		mShader.setLocalMatrix(matrix);
	}
	
	private Bitmap drawToBitmap(Drawable drawable){
		if(drawable instanceof BitmapDrawable){
			return ((BitmapDrawable) drawable).getBitmap();
		}else{
			int width = drawable.getIntrinsicWidth();
			int height = drawable.getIntrinsicHeight();
			Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_4444);
			Canvas canvas = new Canvas(bitmap);
			drawable.draw(canvas);
			return bitmap;
		}
	}
	
	
	private static final String STATE_INSTANCE = "state_instance";  
    private static final String STATE_TYPE = "state_type";  
    private static final String STATE_CORNER_RADIUS = "state_corner_radius";  
	/**
	 * 保存数据
	 */
	@Override
	protected Parcelable onSaveInstanceState() {
		Bundle bundle = new Bundle();
		bundle.putParcelable(STATE_INSTANCE, super.onSaveInstanceState());
		bundle.putInt(STATE_TYPE, mType);
		bundle.putInt(STATE_CORNER_RADIUS, mCornerRadius);
		return bundle;
	}
	
	/**
	 * 回复数据
	 */
	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		if(state instanceof Bundle){
			Bundle bundle = (Bundle) state;
			state = bundle.getParcelable(STATE_INSTANCE);
			mType = bundle.getInt(STATE_TYPE);
			mCornerRadius = bundle.getInt(STATE_CORNER_RADIUS);
			super.onRestoreInstanceState(state);
		}else{
			super.onRestoreInstanceState(state);
		}
	}
	
}

