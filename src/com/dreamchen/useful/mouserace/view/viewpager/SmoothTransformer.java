package com.dreamchen.useful.mouserace.view.viewpager;

import android.annotation.SuppressLint;
import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;

@SuppressLint("NewApi") public class SmoothTransformer implements PageTransformer {

	private static final float MIN_SCALE = 0.75f;  
	
	@Override
	public void transformPage(View view, float position) {
		int pageWidth = view.getWidth();  
		  
        if (position > 1) {  // [+Infinity,1)  
            // This page is way off-screen to the left.  
            view.setAlpha(0);  
  
        } else if (position>0&&position <= 1) { // [1,0)  
            // Use the default slide transition when moving to the left page  
            view.setAlpha(1-position);  
            view.setTranslationX(pageWidth*position);  
            float scaleFactor = MIN_SCALE  
                    + (1 - MIN_SCALE) * (1 - position);  
            view.setScaleX(scaleFactor);  
            view.setScaleY(scaleFactor);  
        } else if (position>-1&&position <= 0) { // [0,-1)
            // Fade the page out.  
            view.setAlpha(1 - Math.abs(position));  
  
            // Counteract the default slide transition  
            view.setTranslationX(0);
  
            // Scale the page down (between MIN_SCALE and 1)  
            float scaleFactor = MIN_SCALE  
                    + (1 - MIN_SCALE) * (1 - Math.abs(position));  
            view.setScaleX(scaleFactor);  
            view.setScaleY(scaleFactor);  
  
        } else { // (-1,-Infinity]
            // This page is way off-screen to the right.  
            view.setAlpha(0);  
        }  
	}

}
