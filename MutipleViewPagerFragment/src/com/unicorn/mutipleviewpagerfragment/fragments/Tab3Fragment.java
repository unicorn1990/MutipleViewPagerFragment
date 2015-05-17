package com.unicorn.mutipleviewpagerfragment.fragments;

import com.unicorn.mutipleviewpagerfragment.R;
import com.unicorn.mutipleviewpagerfragment.subfragments.Tab3Sub1Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Tab3Fragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab3fragment, container, false);

		// return super.onCreateView(inflater, container, savedInstanceState);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		getActivity().getSupportFragmentManager().beginTransaction()
				.replace(R.id.emptyFrame, new Tab3Sub1Fragment()).commit();
	}

}
