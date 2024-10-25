package com.pbn.beers.ble;

import android.content.Context;

import com.clj.fastble.data.BleDevice;
import com.pbn.beers.bean.DisplayParam;
import com.pbn.beers.bean.StandardSampleDataBean;
import com.pbn.beers.bean.ToleranceBean;

/**
 * 目的是封裝廠商 SDK，更能關注色彩管理本身需要的方法。
 */
public abstract class IBluetoothManager
{
	private static IBluetoothManager mBleManager;
	
	/**
	 * 光譜儀連線成功，保持通訊狀態
	 */
	public boolean connect_init = false;
	public BleDevice connectDevice = null;
	
	public boolean adjust_success = false;
	
	public StandardSampleDataBean.StandardDataBean standardDataBean = null;
	public DisplayParam displayParam = null;
	public ToleranceBean toleranceBean = null;
	
	public static IBluetoothManager getInstance() {
		if (mBleManager == null) {
			mBleManager = new BluetoothManager();
		}
		return mBleManager;
	}
	
	public abstract void init(Context context);
	
	/**
	 * 連接成功後設置通知 (When the connection is successful, set the notification)
	 * 做第一次的訊息交換
	 */
	public abstract void setNotify();
	
	/**
	 * 是否還與光譜儀保持通訊?
	 * @return 是否還與光譜儀保持通訊?
	 */
	public abstract boolean isConnected();
	
	/**
	 * 傳送訊息給光譜儀。
	 * @param order 請問需要什麼單?
	 */
	public abstract void setOrder(String order);
}
