package com.pbn.beers.ble;

import android.content.Context;

import com.clj.fastble.data.BleDevice;
import com.pbn.beers.bean.AdjustBean;
import com.pbn.beers.bean.DisplayParam;
import com.pbn.beers.bean.StandardSampleDataBean;
import com.pbn.beers.bean.ToleranceBean;

/**
 * 目的是封裝廠商 SDK，更能關注色彩管理本身需要的方法。
 */
public abstract class IBluetoothManager
{
	private static IBluetoothManager mBleManager;

	public interface AdjustCallback {
		void onWhiteAdjust(AdjustBean bean);
		void onBlackAdjust(AdjustBean bean);
	}

	/** Fired once when the BLE GATT notification listener is ready. */
	public interface NotifyCallback {
		void onNotifySuccess();
		void onNotifyFailure(String reason);
	}

	private AdjustCallback adjustCallback;
	private NotifyCallback notifyCallback;

	public void setNotifyCallback(NotifyCallback cb) {
		this.notifyCallback = cb;
	}

	public void clearNotifyCallback() {
		this.notifyCallback = null;
	}

	protected void triggerNotifySuccess() {
		NotifyCallback cb = notifyCallback;
		notifyCallback = null; // one-shot
		if (cb != null) cb.onNotifySuccess();
	}

	protected void triggerNotifyFailure(String reason) {
		NotifyCallback cb = notifyCallback;
		notifyCallback = null;
		if (cb != null) cb.onNotifyFailure(reason);
	}
	
	/**
	 * 光譜儀連線成功，保持通訊狀態
	 */
	public boolean connect_init = false;
	public BleDevice connectDevice = null;
	
	public boolean adjust_success = false;
	
	public StandardSampleDataBean.StandardDataBean standardDataBean = null;
	public DisplayParam displayParam = null;
	public ToleranceBean toleranceBean = null;

	public void setBleDevice(BleDevice bleDevice) {
		this.connectDevice = bleDevice;
	}
	
	public static IBluetoothManager getInstance() {
		if (mBleManager == null) {
			mBleManager = new BluetoothManager();
		}
		return mBleManager;
	}

	public void setAdjustCallback(AdjustCallback adjustCallback) {
		this.adjustCallback = adjustCallback;
	}

	public void clearAdjustCallback(AdjustCallback adjustCallback) {
		if(this.adjustCallback == adjustCallback) {
			this.adjustCallback = null;
		}
	}

	protected void notifyWhiteAdjust(AdjustBean bean) {
		if(adjustCallback != null) {
			adjustCallback.onWhiteAdjust(bean);
		}
	}

	protected void notifyBlackAdjust(AdjustBean bean) {
		if(adjustCallback != null) {
			adjustCallback.onBlackAdjust(bean);
		}
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
