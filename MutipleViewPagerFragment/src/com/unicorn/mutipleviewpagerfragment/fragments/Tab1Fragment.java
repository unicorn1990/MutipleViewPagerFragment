package com.unicorn.mutipleviewpagerfragment.fragments;

import com.unicorn.mutipleviewpagerfragment.R;
import com.unicorn.mutipleviewpagerfragment.subfragments.Tab1Sub1Fragment;
import com.unicorn.mutipleviewpagerfragment.subfragments.Tab1Sub2Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Tab1Fragment extends Fragment {

	private ViewPager viewPagerBottom;
	private RadioGroup radioGroup;
	private RadioButton radBtn1;
	private RadioButton radBtn2;
	private String TAG="Tab1Fragment";
	private ViewPagerBottomAdapter viewPageAdapterBottom;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.tab1fragment, container, false);
		
		viewPagerBottom = (ViewPager) rootView
				.findViewById(R.id.viewPagerBottom);
		radioGroup = (RadioGroup) rootView.findViewById(R.id.tab2RadioGroup);
		radBtn1 = (RadioButton) rootView.findViewById(R.id.radBtn1);
		radBtn2 = (RadioButton) rootView.findViewById(R.id.radBtn2);
		// return super.onCreateView(inflater, container, savedInstanceState);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		viewPageAdapterBottom = new ViewPagerBottomAdapter(getChildFragmentManager());
		viewPagerBottom.setAdapter(viewPageAdapterBottom);

		viewPagerBottom
				.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						switch (position) {
						case 0:
							radBtn1.setChecked(true);
							radBtn2.setChecked(false);
							break;
						case 1:
							radBtn1.setChecked(false);
							radBtn2.setChecked(true);
							break;
						default:
							break;
						}
					}

					@Override
					public void onPageScrolled(int position,
							float positionOffset, int positionOffsetPixels) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onPageScrollStateChanged(int state) {
						// TODO Auto-generated method stub

					}
				});

		radBtn1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				viewPagerBottom.setCurrentItem(0);
			}
		});
		radBtn2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				viewPagerBottom.setCurrentItem(1);
			}
		});
		radioGroup.check(R.id.radBtn1);
	}
	
	private static class ViewPagerBottomAdapter extends
			FragmentStatePagerAdapter {

		private String[] fragmentNames = { Tab1Sub1Fragment.class.getName(),
				Tab1Sub2Fragment.class.getName() };

		public ViewPagerBottomAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {

			Fragment fragment = null;
			try {
				fragment = (Fragment) Class.forName(fragmentNames[arg0])
						.newInstance();
			} catch (java.lang.InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			return fragment;
		}

		@Override
		public int getCount() {
			return fragmentNames.length;
		}

	}

	@Override
	public void onStart() {
		super.onStart();
		Log.e(TAG, "onStart");
	}

	@Override
	public void onStop() {
		super.onStop();
		Log.e(TAG, "onStop");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.e(TAG, "onDestroy");
	}
}
