package com.pbn.beers.ui.memberCenter;

import android.content.Intent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import com.pbn.beers.databinding.FragmentMemberCenterBinding;

/**
 * 個人中心
 */
public class MemberCenterFragment extends Fragment {
	private FragmentMemberCenterBinding binding;

	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		binding = FragmentMemberCenterBinding.inflate(inflater, container, false);
		binding.setFragment(this);

		return binding.getRoot();
	}

	/**
	 * 到藍芽設備搜畫面
	 */
	public void toBleSearchActivity() {
		Intent intent = new Intent(getActivity(), BleDeviceSearch.class);
		startActivity(intent);
	}


	/**
	 * 到光譜儀校準畫面
	 */
	public void toBleDeviceAdjust() {
		Intent intent = new Intent(getActivity(), BleDeviceAdjust.class);
		startActivity(intent);
	}



	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;
	}
}