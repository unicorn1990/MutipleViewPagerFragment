package com.unicorn.mutipleviewpagerfragment.subfragments;

import com.unicorn.mutipleviewpagerfragment.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Tab1Sub1Fragment extends Fragment {

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.tab1sub1fragment, container, false);
//		return super.onCreateView(inflater, container, savedInstanceState);
		return view;
	}

	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
}

