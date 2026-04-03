package com.pbn.beers.ui.memberCenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.pbn.beers.R;
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



	/**
	 * 顯示語言選擇對話框
	 */
	public void showLanguageSelector() {
		if (getActivity() == null) return;

		LanguageManager.Language[] languages = {
				LanguageManager.Language.SYSTEM,
				LanguageManager.Language.TRADITIONAL_CHINESE,
				LanguageManager.Language.SIMPLIFIED_CHINESE,
				LanguageManager.Language.ENGLISH
		};

		String[] displayNames = new String[languages.length];
		for (int i = 0; i < languages.length; i++) {
			displayNames[i] = languages[i].displayName;
		}

		int currentIndex = 0;
		LanguageManager.Language current = LanguageManager.getSelectedLanguage(getActivity());
		for (int i = 0; i < languages.length; i++) {
			if (languages[i].code.equals(current.code)) {
				currentIndex = i;
				break;
			}
		}

		new AlertDialog.Builder(getActivity())
				.setTitle(R.string.member_language_title)
				.setSingleChoiceItems(displayNames, currentIndex, (dialog, which) -> {
					LanguageManager.setLanguage(getActivity(), languages[which]);
					dialog.dismiss();
					// Recreate to apply the new locale immediately
					getActivity().recreate();
				})
				.setNegativeButton(R.string.btn_cancel, (dialog, which) -> dialog.dismiss())
				.show();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;
	}
}