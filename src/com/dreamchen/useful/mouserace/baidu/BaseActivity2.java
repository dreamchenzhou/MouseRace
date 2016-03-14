package com.dreamchen.useful.mouserace.baidu;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.ab.activity.AbActivity;
import com.ab.view.titlebar.AbBottomBar;
import com.ab.view.titlebar.AbTitleBar;
import com.dreamchen.useful.mouserace.R;

public class BaseActivity2 extends AbActivity
{
	public AbTitleBar mAbTitleBar = null;
	public AbBottomBar mAbBottomBar = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		onCreate();
	}

	@Override
	public void setContentView(int layoutResID)
	{
		// super.setContentView(layoutResID);
		setAbContentView(layoutResID);
	}

	private void onCreate()
	{
		mAbTitleBar = this.getTitleBar();
		mAbBottomBar = this.getBottomBar();
		if (mAbTitleBar != null)
		{
			if (!isShowTitleBar())
			{
				mAbTitleBar.setVisibility(View.GONE);
			}

			mAbTitleBar.setTitleText(getTitle().toString());
			mAbTitleBar.setLogo(R.drawable.button_selector_back);
			mAbTitleBar.setTitleBarBackground(R.drawable.top_bar);
			mAbTitleBar.setTitleTextMargin(10, 0, 0, 0);
			mAbTitleBar.setLogoLine(R.drawable.line);

			mAbTitleBar.getLogoView().setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					onBackPressed();
				}
			});

			// ViewGroup.LayoutParams paramsM = mAbTitleBar.getLayoutParams();
			// if (paramsM != null)
			// {
			// paramsM.height = 65;
			// }
		}
	}

	@Override
	public void setTitle(CharSequence title)
	{
		super.setTitle(title);
		if (mAbTitleBar != null)
		{
			if (!TextUtils.isEmpty(title))
			{
				mAbTitleBar.setTitleText(title.toString());
			}
			else
			{
				mAbTitleBar.setTitleSmallText("");
			}
		}
	}

	public void hideTitleBar()
	{
		if (mAbTitleBar != null)
		{
			mAbTitleBar.setVisibility(View.GONE);
		}
	}

	public boolean isShowTitleBar()
	{
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case android.R.id.home:
				finish();
				break;
			default:
				break;
		}
		return true;
	}

	public InputMethodManager getInputMethodManager()
	{
		InputMethodManager manager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
		return manager;
	}

	public void hideSoftInput(EditText editText)
	{
		InputMethodManager manager = getInputMethodManager();
		if (manager != null && editText != null)
		{
			manager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
		}
	}

	public void hideSoftInput()
	{
		View view = getWindow().peekDecorView();
		if (view != null)
		{
			InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	public void hideSoftInput(InputMethodManager manager, EditText editText)
	{
		if (manager != null && editText != null)
		{
			manager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
		}
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}

	@Override
	public void finish()
	{
		hideSoftInput();
		super.finish();
	}

	public View addRightView(boolean isClear, int resource, int id)
	{
		if (isClear)
		{
			mAbTitleBar.clearRightView();
		}
		View parent = mInflater.inflate(resource, null);
		View child = parent.findViewById(id);
		mAbTitleBar.addRightView(parent);
		return child;
	}

	public View addRightLayout(int resource)
	{
		View view = mInflater.inflate(resource, null);
		mAbTitleBar.addRightView(view);
		return view;
	}
}
