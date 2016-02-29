package com.dreamchen.useful.mouserace.test;

import com.dreamchen.useful.mouserace.R;
import com.dreamchen.useful.mouserace.fragment.MainContentFragment;
import com.dreamchen.useful.mouserace.fragment.MenuFragment;
import com.dreamchen.useful.mouserace.view.slidemenu.SlidingMenu;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;

public class TestSlideActivity extends FragmentActivity implements OnClickListener{
	private SlidingMenu menu;
	private MenuFragment menuFragment;
	private MainContentFragment contentFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_slide);
		
		menuFragment = new MenuFragment();
		contentFragment = new MainContentFragment();
		
		// SlidingMenu的配置 left
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		
		// SlidingMenu的配置 right
//		menu = new SlidingMenu(this);
//		menu.setMode(SlidingMenu.RIGHT);
//		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
//		menu.setShadowWidthRes(R.dimen.shadow_width);
//		menu.setShadowDrawable(R.drawable.shadow_right);
//		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
//		menu.setFadeDegree(0.35f);
//		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		
		menu.setMenu(R.layout.slide_menu);
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, menuFragment).commit();
		getSupportFragmentManager().beginTransaction().replace(R.id.layout_content,contentFragment).commit();
		
		findViewById(R.id.btn_slide).setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_slide:
			if(menu.isMenuShowing()){
				menu.showContent();
			}else{
				menu.showMenu();
			}
			break;

		default:
			break;
		}
	}
}
