package com.dreamchen.useful.mouserace.utils;

import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

public class AnimUtils {
	public void loadZoomInBackground() {
		// Scaling
		Animation scale = new ScaleAnimation(1, 1.5f, 1, 1.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		scale.setDuration(6000);
		// Moving up
		Animation slideUp = new TranslateAnimation(1, 1.5f, 1, 1.5f);
		slideUp.setDuration(6000);
		AnimationSet animSet = new AnimationSet(true);
		animSet.setFillEnabled(true);
		scale.setRepeatCount(Animation.INFINITE);
		scale.setRepeatMode(Animation.REVERSE);
		slideUp.setRepeatCount(Animation.INFINITE);
		slideUp.setRepeatMode(Animation.REVERSE);
		animSet.addAnimation(scale);
		animSet.addAnimation(slideUp);
//		imgBackground.startAnimation(animSet);
	}
}
