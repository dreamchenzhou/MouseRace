package com.dreamchen.useful.mouserace.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.dreamchen.useful.mouserace.R;

public class TextUtils {

	/**
	 * 特殊字符替换图片：/，@。。。
	 * @param context
	 * @param text
	 * @return
	 */
	public static SpannableString getTextIconText(Context context,String text){
		SpannableString spannable = new SpannableString(text);
		
		Drawable img_drawable = context.getResources().getDrawable(R.drawable.ic_launcher);  
		img_drawable.setBounds(0, 0, img_drawable.getIntrinsicWidth(), img_drawable.getIntrinsicHeight());  
        ImageSpan span_img = new ImageSpan(img_drawable, ImageSpan.ALIGN_BASELINE); 
        
        Drawable audio_drawable = context.getResources().getDrawable(R.drawable.ic_launcher);  
        audio_drawable.setBounds(0, 0, audio_drawable.getIntrinsicWidth(), audio_drawable.getIntrinsicHeight());  
        ImageSpan span_audio = new ImageSpan(audio_drawable, ImageSpan.ALIGN_BASELINE); 
        
        Drawable video_drawable = context.getResources().getDrawable(R.drawable.ic_launcher);  
        video_drawable.setBounds(0, 0, video_drawable.getIntrinsicWidth(), video_drawable.getIntrinsicHeight());  
        ImageSpan span_video = new ImageSpan(video_drawable, ImageSpan.ALIGN_BASELINE); 
        
		Pattern pattern = Pattern.compile("/img");
		Matcher matcher = pattern.matcher(text); 
		while (matcher.find()) { 
			//找到指定的字符后 setSpan的参数分别为（指定的图片，字符的开始位置，字符的结束位置 
			spannable.setSpan(span_img, 
					matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); 
		}
		matcher.reset();
		pattern = Pattern.compile("/audio");
		matcher = pattern.matcher(text); 
		while (matcher.find()) { 
			//找到指定的字符后 setSpan的参数分别为（指定的图片，字符的开始位置，字符的结束位置 
			spannable.setSpan(span_audio, 
					matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); 
		}
		
		matcher.reset();
		pattern = Pattern.compile("/video");
		matcher = pattern.matcher(text); 
		while (matcher.find()) { 
			//找到指定的字符后 setSpan的参数分别为（指定的图片，字符的开始位置，字符的结束位置 
			spannable.setSpan(span_video, 
					matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); 
		}
		return spannable;
	}
}
