package com.pbn.beers.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.fragment.app.FragmentActivity;

import com.pbn.beers.bean.MeasureBean;
import com.pbn.beers.bean.ReadMeasureDataBean;
import com.pbn.beers.bean.ReadRgbMeasureDataBean;
import com.pbn.beers.utils.Constant;

import java.io.Serializable;
import java.util.Objects;

/**
 * 光譜儀 => notify => sand broadcast => receiver: SpectrometerBroadcast
 */
public class SpectrometerBroadcast
{
	private final onBroadcastReceived mOnReceived;
	private final Spectrometer mSpectrometer;
	private final FragmentActivity mFragmentActivity;
	
	
	public SpectrometerBroadcast(FragmentActivity fragmentActivity, onBroadcastReceived mOnReceived) {
		this.mOnReceived = mOnReceived;
		this.mSpectrometer = new Spectrometer();
		
		final IntentFilter filter = new IntentFilter();
		filter.addAction(Constant.MEASURE);
		filter.addAction(Constant.READ_MEASURE_DATA);
		filter.addAction(Constant.READ_RGB_MEASURE_DATA);
		mFragmentActivity = fragmentActivity;
		mFragmentActivity.registerReceiver(mSpectrometer, filter);
	}
	
	/**
	 * 解除接收廣播
	 */
	public void unregisterReceiver(){
		mFragmentActivity.unregisterReceiver(mSpectrometer);
	}
	
	/**
	 * 接收對應的廣播事件
	 */
	private class Spectrometer extends BroadcastReceiver
	{
		
		@Override public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			Serializable data = intent.getSerializableExtra("data");
			byte state = intent.getByteExtra("state", (byte) 0x00);
			switch(Objects.requireNonNull(action)) {
				case Constant.MEASURE:
					mOnReceived.onMeasure((MeasureBean) data);
					break;
				case Constant.READ_MEASURE_DATA:
					mOnReceived.onReadMeasureData((ReadMeasureDataBean) data);
					break;
				case Constant.READ_RGB_MEASURE_DATA:
					mOnReceived.onReadRgbMeasureData((ReadRgbMeasureDataBean) data);
					break;
			}
		}
	}
	
	public interface onBroadcastReceived
	{
		/**
		 * 所有量測動作的第一步，成功時才能去讀取光譜儀中的紀錄。
		 * @param data MeasureBean
		 */
		void onMeasure(MeasureBean data);
		
		/**
		 * 取得光譜測量訊息
		 * @param data 取得測量訊息
		 */
		void onReadMeasureData(ReadMeasureDataBean data);
		
		/**
		 * 取得 RGB 測量訊息，但目前 RGB 資訊異常。但可以確認機器上光源與角度的設定。
		 * @param data 取得測量訊息
		 */
		void onReadRgbMeasureData(ReadRgbMeasureDataBean data);
	}
}
