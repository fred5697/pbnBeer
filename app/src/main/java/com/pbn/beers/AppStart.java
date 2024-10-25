package com.pbn.beers;

import android.app.Application;

import com.clj.fastble.BleManager;
import com.pbn.beers.ble.IBluetoothManager;
import com.kongzue.dialog.util.DialogSettings;
import com.kongzue.dialog.util.TextInfo;

public class AppStart extends Application
{
	@Override public void onCreate() {
		super.onCreate();
		
		IBluetoothManager.getInstance().init(this);
		BleManager.getInstance().init(this);
		BleManager.getInstance()
				.enableLog(true)
				.setReConnectCount(3, 5000)
				.setSplitWriteNum(244)
				.setConnectOverTime(30000)
				.setOperateTimeout(40000);
		
		DialogSettings.cancelable = false;
		DialogSettings.buttonTextInfo = new TextInfo().setFontColor(R.color.purple_500);
		DialogSettings.init();
	}
	
}
