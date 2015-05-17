package com.unicorn.mutipleviewpagerfragment;

import java.util.Arrays;
import java.util.List;

import com.jfeinstein.jazzyviewpager.JazzyViewPager;
import com.unicorn.mutipleviewpagerfragment.fragments.*;
import com.unicorn.mutipleviewpagerfragment.utils.Method;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.CheckBox;

public class MainActivity extends FragmentActivity {

	
	
	private JazzyViewPager viewPager;
	private PagerAdapter pagerAdapter;
	protected CheckBox checkBox1,checkBox2,checkBox3;

	private String[] fragmentNames = { Tab1Fragment.class.getName(), /*Tab2Fragment.class.getName(),*/
			Tab2Fragment.class.getName(), Tab3Fragment.class.getName() };

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		
		
		setContentView(R.layout.m_main_activity);

		checkBox1=(CheckBox) findViewById(R.id.checkBox1);
		checkBox2=(CheckBox) findViewById(R.id.checkBox2);
		checkBox3=(CheckBox) findViewById(R.id.checkBox3);
		

		initCheckBox();
		initViewPager();
		
	}
	
	private void initCheckBox() {

		checkBox1.setChecked(true);
		checkBox1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				viewPager.setCurrentItem(0);
				checkBox1.setChecked(true);
				checkBox2.setChecked(false);
				checkBox3.setChecked(false);

			}
		});
		checkBox2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				viewPager.setCurrentItem(1);
				checkBox1.setChecked(false);
				checkBox2.setChecked(true);
				checkBox3.setChecked(false);

			}
		});
		checkBox3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				viewPager.setCurrentItem(2);
				checkBox1.setChecked(false);
				checkBox2.setChecked(false);
				checkBox3.setChecked(true);

			}
		});
	}
	
	private void initViewPager() {
		viewPager = (JazzyViewPager) findViewById(R.id.viewPager);
//		viewPager.setTransitionEffect(TransitionEffect.ZoomIn);
		viewPager.setFadeEnabled(true);
		viewPager.setPageMargin((int) Method.dip2pix(getResources(), 10));
		pagerAdapter = new PagerAdapter(getSupportFragmentManager(), Arrays.asList(fragmentNames),viewPager);
		viewPager.setAdapter(pagerAdapter);
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			/**
			 * This method will be invoked when a new page becomes selected.
			 * Animation is not necessarily complete.
			 * 
			 * @param position
			 *            Position index of the new selected page.
			 */
			@Override
			public void onPageSelected(int position) {

				switch (position) {
				case 0:
					checkBox1.setChecked(true);
					checkBox2.setChecked(false);
					checkBox3.setChecked(false);

					break;
				case 1:
					checkBox1.setChecked(false);
					checkBox2.setChecked(true);
					checkBox3.setChecked(false);
					break;
				case 2:
					checkBox1.setChecked(false);
					checkBox2.setChecked(false);
					checkBox3.setChecked(true);
					break;
				default:
					break;
				}

			}

			/**
			 * This method will be invoked when the current page is scrolled,
			 * either as part of a programmatically initiated smooth scroll or a
			 * user initiated touch scroll.
			 * 
			 * @param position
			 *            Position index of the first page currently being
			 *            displayed. Page position+1 will be visible if
			 *            positionOffset is nonzero.
			 * @param positionOffset
			 *            Value from [0, 1) indicating the offset from the page
			 *            at position.
			 * @param positionOffsetPixels
			 *            Value in pixels indicating the offset from position.
			 */
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			}

			/**
			 * Called when the scroll state changes. Useful for discovering when
			 * the user begins dragging, when the pager is automatically
			 * settling to the current page, or when it is fully stopped/idle.
			 * 
			 * @param state
			 *            The new scroll state.
			 */
			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});
		// viewPager.setCurrentItem(0);
	}
	
	
	static class PagerAdapter extends FragmentStatePagerAdapter {

		private List<String> mFragmentNames;
		private JazzyViewPager jViewPager;

		// private FragmentManager fm;
		public PagerAdapter(FragmentManager fm, List<String> mFragmentNames,JazzyViewPager viewPager) {
			super(fm);
			// this.fm=fm;
			this.mFragmentNames = mFragmentNames;
			this.jViewPager=viewPager;
		}

		@Override
		public Fragment getItem(int arg0) {
			Fragment f=null;
			if (f == null) {
				try {
					f = (Fragment) Class.forName(mFragmentNames.get(arg0)).newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}

			}
			return f;
		}

		public Object instantiateItem(android.view.ViewGroup arg0, int position) {
			Object object=super.instantiateItem(arg0, position);
			
			jViewPager.setObjectForPosition(object, position);
			
			return object;
		};
		
		
		
		@Override
		public int getCount() {
			return mFragmentNames.size();
		}

	}
}
