package com.dreamchen.useful.mouserace.test;

import com.dreamchen.useful.mouserace.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Interpolator;
import android.widget.ImageView;

public class TestAlphaActivity extends Activity {
	ImageView imageView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_alpha);
		imageView = (ImageView) findViewById(R.id.img_test);
		findViewById(R.id.btn_test).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setAlphaAnim();
			}
		});
	}
	
	private void setAlphaAnim(){
		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 255);
		alphaAnimation.setInterpolator(new AccelerateInterpolator());
		alphaAnimation.setDuration(10000);
		imageView.setAnimation(alphaAnimation);
		imageView.startAnimation(alphaAnimation);
	}
}	
