package com.dreamchen.useful.mouserace.view.viewpager;

import android.R.integer;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class IndexView  extends View{

	/**
	 * 圆点的半径(圆点之间的间距)
	 */
	private float pointRadius;
	
	/**
	 * view的宽度
	 */
	private float mWidth;
	
	/**
	 * view的高度
	 */
	private float mHeight;
	
	/**
	 * 背景的颜色
	 */
	private int  bgColor  = Color.parseColor("#44ffffff");
	
	/**
	 * 点的普通颜色
	 */
	private int pointNomalColor = Color.parseColor("#FFFFFF");
	
	/**
	 * 点的选中颜色
	 */
	private int pointSelectColor= Color.parseColor("#FF6600");
	
	/**
	 * 当前选中的index
	 */
	private int mCurrentIndex = 0;
	
	/**
	 * 总的点数
	 */
	private int pointCount = 6;
	
	/**
	 * mCurrentIndex 改变的监听器 
	 */
	private OnIndexChangeListener mListener;
	public IndexView(Context context) {
		super(context);
		init(context);
	}

	public IndexView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public IndexView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	
	private void init(Context context){
//		mWidth = getWidth();
//		pointRadius = mWidth/23;
//		mHeight = 3*pointRadius;
	}
	
	public void setTotalPoint(int totalPoin){
		this.pointCount = totalPoin;
		pointRadius =mWidth/(5+3*totalPoin);
		mHeight = 3*pointRadius;
		invalidate();
	}
	
	public void setCurrentIndex(int currentIndex){
		this.mCurrentIndex  = currentIndex;
		if(mListener!=null){
			mListener.onIndexChanged(mCurrentIndex);
		}
		invalidate();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		mWidth = MeasureSpec.getSize(widthMeasureSpec);
		pointRadius = mWidth/(5+3*pointCount);
		mHeight = 3*pointRadius;
		setMeasuredDimension((int)mWidth, (int)mHeight);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
//		drawBg(canvas);
		drawNormalPoint(canvas);
		drawSelectedPoint(canvas);
	}
	
	/**
	 * 画背景
	 * @param canvas
	 */
	private void drawBg(Canvas canvas){
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(bgColor);
		Rect r = new Rect(0, 0, (int)mWidth, (int)mHeight);
		RectF rect = new RectF(r);
		canvas.drawRoundRect(rect, 3, 3, paint);
	}
	
	private void drawNormalPoint(Canvas canvas){
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(pointNomalColor);
		float startX = 4*pointRadius;
		int y = (int) (1.5*pointRadius);
		for(int i=0;i<pointCount;i++){
				canvas.drawCircle(startX+3*pointRadius*i, y, pointRadius, paint);
		}
	}
	
	private void drawSelectedPoint(Canvas canvas){
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(pointSelectColor);
		float startX = 4*pointRadius;
		int y = (int) (1.5*pointRadius);
		canvas.drawCircle(startX+3*pointRadius*mCurrentIndex, y, pointRadius, paint);
	}
	
	public void setOnIndexChangeListener(OnIndexChangeListener listener){
		this.mListener = listener;
	}
	
	
	public interface OnIndexChangeListener{
		public void onIndexChanged(int currentIndex);
	}
	
 }
