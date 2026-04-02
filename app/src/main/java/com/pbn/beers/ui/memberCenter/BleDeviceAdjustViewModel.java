package com.pbn.beers.ui.memberCenter;

import androidx.lifecycle.ViewModel;

import com.pbn.beers.utils.Constant;
import com.pbn.beers.ble.IBluetoothManager;

public class BleDeviceAdjustViewModel extends ViewModel {

    public void onWhiteAdjustClick() {
        IBluetoothManager.getInstance().setOrder(Constant.WHITE_ADJUST);
    }

    public void onBlackAdjustClick() {
        IBluetoothManager.getInstance().setOrder(Constant.BLACK_ADJUST);
    }
}

