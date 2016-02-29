package com.dreamchen.useful.mouserace.test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;

import com.dreamchen.useful.mouserace.R;
import com.dreamchen.useful.mouserace.utils.AbDateUtil;
import com.dreamchen.useful.mouserace.view.wheel.AbNumericWheelAdapter;
import com.dreamchen.useful.mouserace.view.wheel.AbWheelView;
import com.dreamchen.useful.mouserace.view.wheel.AbWheelView.AbOnWheelChangedListener;

public class TestWheelActivity extends Activity {
	private AbWheelView mWheelViewY;
	private AbWheelView mWheelViewM;
	private AbWheelView mWheelViewD;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_wheel);

		mWheelViewY = (AbWheelView) findViewById(R.id.wheel_view_year);
		mWheelViewM = (AbWheelView) findViewById(R.id.wheel_view_month);
		mWheelViewD = (AbWheelView) findViewById(R.id.wheel_view_day);
		// 初始化wheel_view
		// 设置"年"的显示数据
		int defaultDay = 0;
		int defaultMonth = 0;
		int defaultYear = 2015;
		final int startYear = 2004;

		int endYear = startYear + 100;
		// 添加大小月月份并将其转换为list,方便之后的判断
		String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
		String[] months_little = { "4", "6", "9", "11" };
		// 时间选择可以这样实现
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DATE);

		if (true) {
			defaultYear = year;
			defaultMonth = month;
			defaultDay = day;
		}

		final List<String> list_big = Arrays.asList(months_big);
		final List<String> list_little = Arrays.asList(months_little);

		mWheelViewY.setAdapter(new AbNumericWheelAdapter(startYear, endYear));
		mWheelViewY.setCyclic(true);// 可循环滚动
		mWheelViewY.setLabel("年"); // 添加文字
		mWheelViewY.setCurrentItem(defaultYear - startYear);// 初始化时显示的数据
		mWheelViewY.setValueTextSize(35);
		mWheelViewY.setLabelTextSize(35);
		mWheelViewY.setLabelTextColor(0x80000000);

		// 月
		mWheelViewM.setAdapter(new AbNumericWheelAdapter(1, 12));
		mWheelViewM.setCyclic(true);
		mWheelViewM.setLabel("月");
		mWheelViewM.setCurrentItem(defaultMonth - 1);
		mWheelViewM.setValueTextSize(35);
		mWheelViewM.setLabelTextSize(35);
		mWheelViewM.setLabelTextColor(0x80000000);
		// mWheelViewM.setCenterSelectDrawable(this.getResources().getDrawable(R.drawable.wheel_select));

		// 日
		// 判断大小月及是否闰年,用来确定"日"的数据
		if (list_big.contains(String.valueOf(month + 1))) {
			mWheelViewD.setAdapter(new AbNumericWheelAdapter(1, 31));
		} else if (list_little.contains(String.valueOf(month + 1))) {
			mWheelViewD.setAdapter(new AbNumericWheelAdapter(1, 30));
		} else {
			// 闰年
			if (AbDateUtil.isLeapYear(year)) {
				mWheelViewD.setAdapter(new AbNumericWheelAdapter(1, 29));
			} else {
				mWheelViewD.setAdapter(new AbNumericWheelAdapter(1, 28));
			}
		}
		mWheelViewD.setCyclic(true);
		mWheelViewD.setLabel("日");
		mWheelViewD.setCurrentItem(defaultDay - 1);
		mWheelViewD.setValueTextSize(35);
		mWheelViewD.setLabelTextSize(35);
		mWheelViewD.setLabelTextColor(0x80000000);
		// mWheelViewD.setCenterSelectDrawable(this.getResources().getDrawable(R.drawable.wheel_select));

		// 添加"年"监听
		AbOnWheelChangedListener wheelListener_year = new AbOnWheelChangedListener() {

			public void onChanged(AbWheelView wheel, int oldValue, int newValue) {
				int year_num = newValue + startYear;
				int mDIndex = mWheelViewM.getCurrentItem();
				// 判断大小月及是否闰年,用来确定"日"的数据
				if (list_big.contains(String.valueOf(mWheelViewM
						.getCurrentItem() + 1))) {
					mWheelViewD.setAdapter(new AbNumericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(mWheelViewM
						.getCurrentItem() + 1))) {
					mWheelViewD.setAdapter(new AbNumericWheelAdapter(1, 30));
				} else {
					if (AbDateUtil.isLeapYear(year_num))
						mWheelViewD
								.setAdapter(new AbNumericWheelAdapter(1, 29));
					else
						mWheelViewD
								.setAdapter(new AbNumericWheelAdapter(1, 28));
				}
				mWheelViewM.setCurrentItem(mDIndex);

			}
		};
		// 添加"月"监听
		AbOnWheelChangedListener wheelListener_month = new AbOnWheelChangedListener() {

			public void onChanged(AbWheelView wheel, int oldValue, int newValue) {
				int month_num = newValue + 1;
				// 判断大小月及是否闰年,用来确定"日"的数据
				if (list_big.contains(String.valueOf(month_num))) {
					mWheelViewD.setAdapter(new AbNumericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(month_num))) {
					mWheelViewD.setAdapter(new AbNumericWheelAdapter(1, 30));
				} else {
					int year_num = mWheelViewY.getCurrentItem() + startYear;
					if (AbDateUtil.isLeapYear(year_num))
						mWheelViewD
								.setAdapter(new AbNumericWheelAdapter(1, 29));
					else
						mWheelViewD
								.setAdapter(new AbNumericWheelAdapter(1, 28));
				}
				mWheelViewD.setCurrentItem(0);
			}
		};
		mWheelViewY.addChangingListener(wheelListener_year);
		mWheelViewM.addChangingListener(wheelListener_month);
	}
}
