package com.pbn.beers.baseActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pbn.beers.bean.AdjustBean;
import com.pbn.beers.bean.DeviceInfoBean;
import com.pbn.beers.bean.DeviceInfoStruct;
import com.pbn.beers.bean.MeasureBean;
import com.pbn.beers.bean.ReadLabMeasureDataBean;
import com.pbn.beers.bean.ReadMeasureDataBean;
import com.pbn.beers.bean.ReadRgbMeasureDataBean;
import com.pbn.beers.bean.StandardSampleDataBean;
import com.pbn.beers.utils.Constant;

import java.io.Serializable;
import java.util.Objects;

/**
 * 接收廣播訊息
 */
public abstract class BluetoothBaseActivity extends AppCompatActivity
{
	private PlaybackStatus mPlaybackStatus;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPlaybackStatus = new PlaybackStatus();
		registerReceiver();
	}
	
	private void registerReceiver() {
		
		final IntentFilter filter = new IntentFilter();
		filter.addAction(Constant.BLACK_ADJUST);
		filter.addAction(Constant.WHITE_ADJUST);
		filter.addAction(Constant.MEASURE);
		filter.addAction(Constant.READ_MEASURE_DATA);
		filter.addAction(Constant.READ_LAB_MEASURE_DATA);
//		filter.addAction(Constant.READ_RGB_MEASURE_DATA);
		filter.addAction(Constant.GET_STANDARD_DATA_COUNT);
		filter.addAction(Constant.GET_STANDARD_DATA_FOR_NUM);
		filter.addAction(Constant.DELETE_ALL_STANDARD_DATA);
		filter.addAction(Constant.DELETE_STANDARD_DATA_FOR_NUM);
		filter.addAction(Constant.GET_SAMPLE_COUNT_FOR_STANDARD_NUM);
		filter.addAction(Constant.GET_NUM_SAMPLE_DATA_FOR_NUM_STANDARD);
		filter.addAction(Constant.DELETE_ALL_SAMPLE_FOR_STANDARD_NUM);
		filter.addAction(Constant.DELETE_NUM_SAMPLE_DATA_FOR_NUM_STANDARD);
		filter.addAction(Constant.POST_STANDARD_DATA);
		filter.addAction(Constant.GET_DEVICE_INFO);
		filter.addAction(Constant.GET_DEVICE_POWER_INFO);
		filter.addAction(Constant.GET_DEVICE_ADJUST_STATE);
		filter.addAction(Constant.SET_DEVICE_DISPLAY_PARAM);
		filter.addAction(Constant.SET_TOLERANCE);
		filter.addAction(Constant.SET_BLUETOOTH);
		filter.addAction(Constant.SET_POWER_MANAGEMENT_TIME);
		filter.addAction(Constant.SET_DEVICE_TIME);
		filter.addAction(Constant.SET_SAVE_MODE);
		filter.addAction(Constant.SET_BLUETOOTH_NAME);
		filter.addAction(Constant.ON_FAIL);
		registerReceiver(mPlaybackStatus, filter);
		
	}
	
	private class PlaybackStatus extends BroadcastReceiver
	{
		
		/**
		 * 接收 {@link com.pbn.beers.ble.BluetoothManager} 的廣播訊息
		 */
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if(action != null) {
				Log.i("deep", action);
			}
			Serializable data = intent.getSerializableExtra("data");
			byte state = intent.getByteExtra("state", (byte) 0x00);
			short standardNum = intent.getShortExtra("standard_num", (short) 0);
			short sampleNum = intent.getShortExtra("sample_num", (short) 0);
			short standardCount = intent.getShortExtra("standard_count", (short) 0);
			short sampleCount = intent.getShortExtra("sample_count", (short) 0);
			switch(Objects.requireNonNull(action)) {
				case Constant.BLACK_ADJUST:
					onBlackAdjust((AdjustBean) data);
					break;
				case Constant.WHITE_ADJUST:
					onWhiteAdjust((AdjustBean) data);
					break;
//				case Constant.MEASURE:
//					onMeasure((MeasureBean) data);
//					break;
//				case Constant.READ_MEASURE_DATA:
//					onReadMeasureData((ReadMeasureDataBean) data);
//					break;
//				case Constant.READ_LAB_MEASURE_DATA:
//					onReadLabMeasureData((ReadLabMeasureDataBean) data);
//					break;
//				case Constant.READ_RGB_MEASURE_DATA:
//					onReadRgbMeasureData((ReadRgbMeasureDataBean) data);
//					break;
				case Constant.GET_STANDARD_DATA_COUNT:
					onGetStandardCount(standardCount);
					break;
				case Constant.GET_STANDARD_DATA_FOR_NUM:
					onGetStandardDataForNumber((StandardSampleDataBean.StandardDataBean) data);
					break;
				case Constant.DELETE_ALL_STANDARD_DATA:
					onDeleteAllStandardData(state);
					break;
				case Constant.DELETE_STANDARD_DATA_FOR_NUM:
					onDeleteStandardDataForNumber(standardNum, state);
					break;
				case Constant.GET_SAMPLE_COUNT_FOR_STANDARD_NUM:
					onGetSampleCountForStandardNumber(standardNum, state, sampleCount);
					break;
				case Constant.GET_NUM_SAMPLE_DATA_FOR_NUM_STANDARD:
					onGetNumSampleDataForNumStandard(standardNum, sampleNum, state,
							(StandardSampleDataBean.SampleDataBean) data);
					break;
				case Constant.DELETE_ALL_SAMPLE_FOR_STANDARD_NUM:
					onDeleteAllSampleForStandardNum(standardNum, state);
					break;
				case Constant.DELETE_NUM_SAMPLE_DATA_FOR_NUM_STANDARD:
					onDeleteNumSampleDataForNumStandard(standardNum, sampleNum, state);
					break;
				case Constant.POST_STANDARD_DATA:
					onPostStandardData(state);
					break;
				case Constant.GET_DEVICE_POWER_INFO:
					onGetDevicePowerInfo((DeviceInfoBean.PowerInfo) data);
					break;
				case Constant.GET_DEVICE_ADJUST_STATE:
					onGetDeviceAdjustState((DeviceInfoBean.DeviceAdjustState) data);
					break;
				case Constant.SET_DEVICE_DISPLAY_PARAM:
					onSetDeviceDisplayParam(state);
					break;
				case Constant.SET_TOLERANCE:
					onSetTolerance(state);
					break;
				case Constant.SET_BLUETOOTH:
					onSetBluetooth(state);
					break;
				case Constant.SET_BLUETOOTH_NAME:
					onSetBluetoothName(state);
					break;
				case Constant.SET_POWER_MANAGEMENT_TIME:
					onSetPowerManagementTime(state);
					break;
				case Constant.SET_DEVICE_TIME:
					onSetDeviceTime(state);
					break;
				case Constant.SET_SAVE_MODE:
					onSetSaveMode(state);
					break;
				case Constant.ON_FAIL:
					String failType = intent.getStringExtra(Constant.ON_FAIL);
					onFail(failType);
					break;
			}
		}
	}
	
	protected void onGetDeviceInfo(DeviceInfoStruct deviceInfoStruct) {	}
	
	/**
	 * 接收光譜儀白校準後的訊息
	 * @param bean bean
	 */
	protected void onWhiteAdjust(AdjustBean bean) {	}
	
	/**
	 * 接收光譜儀黑校準後的訊息
	 * @param bean bean
	 */
	protected void onBlackAdjust(AdjustBean bean) {	}
	
	protected void onMeasure(MeasureBean bean) { }
	
	protected void onReadMeasureData(ReadMeasureDataBean bean) { }
	
	protected void onReadLabMeasureData(ReadLabMeasureDataBean bean) {
	
	}
	
	protected void onReadRgbMeasureData(ReadRgbMeasureDataBean bean) {
	
	}
	
	protected void onGetStandardCount(short count) {
	
	}
	
	protected void onGetStandardDataForNumber(StandardSampleDataBean.StandardDataBean standardDataBean) {
	
	}
	
	protected void onDeleteAllStandardData(byte state) {
	
	}
	
	protected void onDeleteStandardDataForNumber(short standardNum, byte state) {
	
	}
	
	protected void onGetSampleCountForStandardNumber(short standardNum, byte state, short sampleCount) {
	
	}
	
	protected void onGetNumSampleDataForNumStandard(short standardNum, short sampleNum,
			byte state, StandardSampleDataBean.SampleDataBean sampleDataBean) {
		
	}
	
	protected void onDeleteAllSampleForStandardNum(short standardNum, byte state) {
	
	}
	
	protected void onDeleteNumSampleDataForNumStandard(short standardNum, short sampleNum, byte state) {
	
	}
	
	protected void onPostStandardData(byte state) {
	
	}
	
	protected void onGetDevicePowerInfo(DeviceInfoBean.PowerInfo powerInfo) {
	
	}
	
	protected void onGetDeviceAdjustState(DeviceInfoBean.DeviceAdjustState deviceAdjustState) {
	
	}
	
	protected void onSetDeviceDisplayParam(byte state) {
	
	}
	
	protected void onSetTolerance(byte state) {
	
	}
	
	protected void onSetBluetooth(byte state) {
	
	}
	
	protected void onSetPowerManagementTime(byte state) {
	
	}
	
	protected void onSetDeviceTime(byte state) {
	
	}
	
	protected void onSetSaveMode(byte state) {
	
	}
	
	protected void onSetBluetoothName(byte state) {
	
	}
	
	protected void onFail(String failType) {
	
	}
	
}
