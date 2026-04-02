package com.pbn.beers.ui.memberCenter;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.pbn.beers.R;
import com.pbn.beers.baseActivity.BluetoothBaseActivity;
import com.pbn.beers.bean.AdjustBean;
import com.pbn.beers.bean.DisplayParam;
import com.pbn.beers.ble.IBluetoothManager;
import com.pbn.beers.utils.Constant;
import com.pbn.beers.utils.MessageDialogUtil;
import com.pbn.beers.utils.WaitDialogUtil;
import com.kongzue.dialog.v3.MessageDialog;

public class BleDeviceAdjust extends BluetoothBaseActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ble_device_adjust);

		if(IBluetoothManager.getInstance().connect_init && IBluetoothManager.getInstance().isConnected()) {
			setDeviceDisplayParam();
		}
		else {
			useGenericOkMsgBox("請先與光譜儀做配對!");
		}
	}

	// region 光譜儀設定預設參數
	private void setDeviceDisplayParam() {
		IBluetoothManager.getInstance().displayParam = new DisplayParam();
		IBluetoothManager.getInstance().setOrder(Constant.SET_DEVICE_DISPLAY_PARAM);
		WaitDialogUtil.show(BleDeviceAdjust.this, getString(R.string.adjust_setting));
	}

	@Override
	public void onSetDeviceDisplayParam(byte state) {
		super.onSetDeviceDisplayParam(state);
		WaitDialogUtil.dismiss();
		if(state == 0x00) {
			whiteAdjust();
		}
		else {
			useGenericOkMsgBox("光譜儀初始設定失敗，請校準後在進行操作!!");
		}
	}
	// endregion

	// region 光譜儀校正
	private void whiteAdjust() {
		MessageDialog.show(BleDeviceAdjust.this, "", "", "").setCancelable(true)
				.setCustomView(R.layout.dialog_adust_device, (dialog, v) -> {
					((TextView) v.findViewById(R.id.adjust_device_tittle)).setText(getString(R.string.white_adjust));
					((TextView) v.findViewById(R.id.adjust_device_tip)).setText(getString(R.string.white_adjust_tip));
					v.findViewById(R.id.adjust_device_btn).setOnClickListener(v1 -> {
						WaitDialogUtil.show(BleDeviceAdjust.this, getString(R.string.adjusting_wait));
						IBluetoothManager.getInstance().setOrder(Constant.WHITE_ADJUST);
					});
				});
	}

	private void blackAdjust() {
		MessageDialog.show(BleDeviceAdjust.this, "", "", "").setCancelable(true)
				.setCustomView(R.layout.dialog_adust_device, (dialog, v) -> {
					((TextView) v.findViewById(R.id.adjust_device_tittle)).setText(getString(R.string.black_adjust));
					((TextView) v.findViewById(R.id.adjust_device_tip)).setText(getString(R.string.black_adjust_tip));
					v.findViewById(R.id.adjust_device_btn).setOnClickListener(v1 -> {
						WaitDialogUtil.show(BleDeviceAdjust.this, getString(R.string.adjusting_wait));
						IBluetoothManager.getInstance().setOrder(Constant.BLACK_ADJUST);
					});
				});
	}

	@Override
	public void onWhiteAdjust(AdjustBean bean) {
		super.onWhiteAdjust(bean);
		WaitDialogUtil.dismiss();
		if(bean.getAdjustState() == 0x00) {
			blackAdjust();
		}
		else {
			useGenericOkMsgBox(getString(R.string.adjust_fail));
		}
	}

	@Override
	public void onBlackAdjust(AdjustBean bean) {
		super.onBlackAdjust(bean);
		WaitDialogUtil.dismiss();
		if(bean.getAdjustState() == 0x00) {
			IBluetoothManager.getInstance().adjust_success = true;
			// BleDeviceSearch already finished itself before launching this activity,
			// so finish() here returns directly to MemberCenterFragment in MainActivity.
			finish();
			return;
		}
		IBluetoothManager.getInstance().adjust_success = false;
		useGenericOkMsgBox(getString(R.string.adjust_fail));
	}
	// endregion

	private void useGenericOkMsgBox(String msg) {
		MessageDialogUtil.okMsgBox(BleDeviceAdjust.this, "校準訊息", msg, BleDeviceAdjust.this::finish);
	}

	@Override public void onBackPressed() {
		if(!IBluetoothManager.getInstance().adjust_success) {
			useGenericOkMsgBox("請先校正光譜儀在進行操作!!");
		}
		else {
			super.onBackPressed();
		}
	}
}