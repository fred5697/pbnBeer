package com.pbn.beers.ble;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleNotifyCallback;
import com.clj.fastble.callback.BleWriteCallback;
import com.clj.fastble.exception.BleException;
import com.clj.fastble.utils.HexUtil;
import com.pbn.beers.bean.AdjustBean;
import com.pbn.beers.bean.DeviceInfoBean;
import com.pbn.beers.bean.DeviceInfoStruct;
import com.pbn.beers.bean.MeasureBean;
import com.pbn.beers.bean.ReadLabMeasureDataBean;
import com.pbn.beers.bean.ReadMeasureDataBean;
import com.pbn.beers.bean.ReadRgbMeasureDataBean;
import com.pbn.beers.bean.StandardSampleDataBean;
import com.pbn.beers.encode.DataParse;
import com.pbn.beers.encode.MachineCmd;
import com.pbn.beers.utils.ByteUtil;
import com.pbn.beers.utils.Constant;
import com.pbn.beers.utils.TimeUtil;

import org.apache.commons.lang3.ArrayUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

class BluetoothManager extends IBluetoothManager
{
	private final String TAG = BluetoothManager.class.getName();
	private Context mContext = null;

	private final String serviceUUid = "0000FFE0-0000-1000-8000-00805F9B34FB";
	private final String writeChaUUid = "0000FFE1-0000-1000-8000-00805F9B34FB";

	private Disposable disposable = null; //rxJava lib

	/**
	 * deviceData总量(Total device data)
	 */
	private short count = 0;

	/**
	 * 表示data数据索引(Data index)
	 */
	private short index = 0;

	private String order = null;
	private byte[] realByte = null;
	private final Handler myHandle;

	public BluetoothManager() {
		this.myHandle = new Handler();
	}

	@Override public void init(Context context) {
		this.mContext = context;
	}

	@Override public void setNotify() {
		BleManager.getInstance().stopNotify(BleManager.getInstance().getAllConnectedDevice().get(0), serviceUUid, writeChaUUid);
		new Handler().postDelayed(() -> BleManager.getInstance().notify(BleManager.getInstance().getAllConnectedDevice().get(0),
				serviceUUid, writeChaUUid, new BleNotifyCallback()
				{

					/**
					 * 依據光譜儀回傳資料解析後, 將資料送到 broadcast
					 * @param data 光譜儀返回的資訊
					 */
					@SuppressLint("DefaultLocale")
					@Override
					public void onCharacteristicChanged(byte[] data) {
						if(HexUtil.formatHexString(data).startsWith("bb1a")) {
							initStatus();
							if(data[ 2 ] == 0x00) {
								Log.i(TAG, "setDisplayParams===>success");
							}
							else if(data[ 2 ] == 0x01) {
								Log.i(TAG, "setDisplayParams===>fail");
							}
							else {
								Log.i(TAG, "setDisplayParams===>Parsing error");
							}
							Intent intent = new Intent(Constant.SET_DEVICE_DISPLAY_PARAM);
							intent.putExtra("state", data[ 2 ]);
							mContext.sendBroadcast(intent);
						}
						else if(HexUtil.formatHexString(data).startsWith("bb1b")) {
							initStatus();
							if(data[ 2 ] == 0x00) {
								Log.i(TAG, "setTolerance===>success");
							}
							else if(data[ 2 ] == 0x01) {
								Log.i(TAG, "setTolerance===>fail");
							}
							else {
								Log.i(TAG, "setTolerance===>Parsing error");
							}
							Intent intent = new Intent(Constant.SET_TOLERANCE);
							intent.putExtra("state", data[ 2 ]);
							mContext.sendBroadcast(intent);
						}
						else if(HexUtil.formatHexString(data).startsWith("bb14")) {
							initStatus();
							if(data[ 2 ] == 0x00) {
								Log.i(TAG, "syncTime===>success");
							}
							else if(data[ 2 ] == 0x01) {
								Log.i(TAG, "syncTime===>fail");
							}
							else {
								Log.i(TAG, "syncTime===>err");
							}
							Intent intent = new Intent(Constant.SET_TOLERANCE);
							intent.putExtra("state", data[ 2 ]);
							mContext.sendBroadcast(intent);
						}
						else if(HexUtil.formatHexString(data).startsWith("bb10")) {
							initStatus();
							AdjustBean adjustBean = DataParse.parseAdjust(data);
							Log.i(TAG, "blackAdjust===>" + adjustBean.toString());
							Intent intent = new Intent(Constant.BLACK_ADJUST);
							intent.putExtra("data", adjustBean);
							mContext.sendBroadcast(intent);
						}
						else if(HexUtil.formatHexString(data).startsWith("bb11")) {
							initStatus();
							AdjustBean adjustBean = DataParse.parseAdjust(data);
							Log.i(TAG, "whiteAdjust===>" + adjustBean.toString());
							Intent intent = new Intent(Constant.WHITE_ADJUST);
							intent.putExtra("data", adjustBean);
							mContext.sendBroadcast(intent);
						}
						else if(HexUtil.formatHexString(data).startsWith("bb01")) {
							initStatus();
							MeasureBean bean = DataParse.parseMeasure(data);
							Log.i(TAG, "measure===>" + bean.toString());
							Intent intent = new Intent(Constant.MEASURE);
							intent.putExtra("data", bean);
							mContext.sendBroadcast(intent);
						}
						else if(HexUtil.formatHexString(data).startsWith("bb03")) {
							initStatus();
							ReadLabMeasureDataBean bean = DataParse.parseReadLabMeasureData(data);
							Log.i(TAG, "readLabMeasureData===>" + bean.toString());
							Intent intent = new Intent(Constant.READ_LAB_MEASURE_DATA);
							intent.putExtra("data", bean);
							mContext.sendBroadcast(intent);
						}
						else if(HexUtil.formatHexString(data).startsWith("bb04")) {
							initStatus();
							ReadRgbMeasureDataBean bean = DataParse.parseReadRgbMeasureData(data);
							Log.i(TAG, "readGgbMeasureData===>" + bean.toString());
							Intent intent = new Intent(Constant.READ_RGB_MEASURE_DATA);
							intent.putExtra("data", bean);
							mContext.sendBroadcast(intent);
						}
						else if(HexUtil.formatHexString(data).startsWith("bb1d")) {
							initStatus();
							DeviceInfoBean.PowerInfo powerInfo = DataParse.parseDevicePowerInfo(data);
							Log.i(TAG, "readPowerInfo===>" + powerInfo.getElectricQuantity());
							Intent intent = new Intent(Constant.GET_DEVICE_POWER_INFO);
							intent.putExtra("data", powerInfo);
							mContext.sendBroadcast(intent);
						}
						else if(HexUtil.formatHexString(data).startsWith("bb1e")) {
							initStatus();
							int blackTime = ByteUtil.getInt(data, 8, ByteUtil.TYPE_LOW_FIRST);
							int whiteTime = ByteUtil.getInt(data, 3, ByteUtil.TYPE_LOW_FIRST);
							DeviceInfoBean.DeviceAdjustState adjustState = DataParse.parseDeviceAdjustState(data);
							Log.i(TAG, "blackAdjust===>" + TimeUtil.unixTimestamp2Date(blackTime));
							Log.i(TAG, "whiteAdjust===>" + TimeUtil.unixTimestamp2Date(whiteTime));
							Intent intent = new Intent(Constant.GET_DEVICE_ADJUST_STATE);
							intent.putExtra("data", adjustState);
							mContext.sendBroadcast(intent);
						}
						else if(HexUtil.formatHexString(data).startsWith("bb1600")) {
							initStatus();
							count = 0;
							index = 0;
							BleManager.getInstance().setOperateTimeout(2000);
							count = ByteUtil.getShort(data, 3);
							Log.i(TAG, "getStandardCount===>" + count);
							Intent intent = new Intent(Constant.GET_STANDARD_DATA_COUNT);
							intent.putExtra("standard_count", count);
							mContext.sendBroadcast(intent);
						}
						else if(HexUtil.formatHexString(data).startsWith("bb1602")) {
							initStatus();
							if(data[ 5 ] == 0x00) {
								Log.i(TAG, "deleteAllData===>删除成功(delete success)");
							}
							else if(data[ 5 ] == 0x01) {
								Log.i(TAG, "deleteAllData===>删除失败(delete fail)");
							}
							else if(data[ 5 ] == 0x02) {
								Log.i(TAG, "deleteAllData===>删除失败，超过界限(Beyond the limit)");
							}
							else {
								Log.i(TAG, "deleteAllData===>Parsing error");
							}
							index = 0;
							short standardNum = ByteUtil.getShort(data, 3);
							Intent intent = new Intent(Constant.DELETE_STANDARD_DATA_FOR_NUM);
							intent.putExtra("state", data[ 5 ]);
							intent.putExtra("standard_num", standardNum);
						}
						else if(HexUtil.formatHexString(data).startsWith("bb1605")) {
							if(data.length == 20 && HexUtil.formatHexString(data).substring(16, 18).equals("ff")) {
								initStatus();
								BleManager.getInstance().setOperateTimeout(2000);
							}
						}
						else if(HexUtil.formatHexString(data).startsWith("bb160a")) {
							initStatus();
							if(data[ 3 ] == 0x00) {
								Log.i(TAG, "postDeviceData===>下载成功(Download successful)");
							}
							else if(data[ 3 ] == 0x01) {
								Log.i(TAG, "postDeviceData===>下载失败(Download failed)");
							}
							else if(data[ 3 ] == 0x02) {
								Log.i(TAG, "postDeviceData===>下载失败：仪器存储已满(Download failed: instrument storage is full)");
							}
							Intent intent = new Intent(Constant.POST_STANDARD_DATA);
							intent.putExtra("state", data[ 3 ]);
							mContext.sendBroadcast(intent);
						}
						else if(HexUtil.formatHexString(data).startsWith("bb1604")) {
							//删除所有数据回调 Delete all data callbacks
							initStatus();
							if(data[ 3 ] == 0x00) {
								Log.i(TAG, "deleteAllData===>success");
							}
							else if(data[ 3 ] == 0x01) {
								Log.i(TAG, "deleteAllData===>fail");
							}
							else {
								Log.i(TAG, "deleteAllData===>Parsing error");
							}
							Intent intent = new Intent(Constant.DELETE_ALL_STANDARD_DATA);
							intent.putExtra("state", data[ 3 ]);
							mContext.sendBroadcast(intent);
						}
						else {
							switch(order) {
								case Constant.READ_MEASURE_DATA:
									readNewMeasureResult(data);
									break;
								case Constant.GET_DEVICE_INFO:
									if(realByte.length < 200) {
										realByte = ArrayUtils.addAll(realByte, data);
									}
									Log.d(TAG, realByte.length + "");
									if(realByte.length == 200) {
										byte[] end = ArrayUtils.subarray(realByte,
												198, 199);
										if(HexUtil.formatHexString(end).equals("ff")) {
											initStatus();
											DeviceInfoStruct deviceInfoStruct = DataParse.parseDeviceInfo(realByte);
											Intent intent = new Intent(Constant.GET_DEVICE_INFO);
											intent.putExtra("data", deviceInfoStruct);
											mContext.sendBroadcast(intent);
											Log.i(TAG, "getDeviceInfo===>" + deviceInfoStruct.toString());
										}
									}
									break;
								case Constant.GET_STANDARD_DATA_FOR_NUM:
									if(realByte.length < 250) {
										realByte = ArrayUtils.addAll(realByte, data);
									}
									if(realByte.length == 250) {
										byte[] end = ArrayUtils.subarray(realByte, 248, 249);
										HexUtil.formatHexString(realByte);
										if(HexUtil.formatHexString(end).equals("ff")) {
											initStatus();
											StandardSampleDataBean.StandardDataBean standardDataBean = DataParse.parseGetStandardForNum(realByte);
											Log.i(TAG, "getStandardData===>index===>" + index + "===>" + standardDataBean.toString());
											Intent intent = new Intent(Constant.GET_STANDARD_DATA_FOR_NUM);
											intent.putExtra("data", standardDataBean);
											mContext.sendBroadcast(intent);
											if(index < count - 1) {
												index++;
												setOrder(Constant.GET_STANDARD_DATA_FOR_NUM);
											}
										}
										else {
											postOrder();
										}
									}
									break;
							}
						}
					}

					@Override
					public void onNotifySuccess() {
						Log.d(TAG, "onNotifySuccess-" + Thread.currentThread().getId());
					}

					@Override
					public void onNotifyFailure(BleException exception) {
						Log.d(TAG, "onNotifyFailure-" + Thread.currentThread().getId());
					}
				}), 1000);
	}

	@Override public boolean isConnected() {
		return BleManager.getInstance().isConnected(connectDevice);
	}


	/**
	 * 200位获取测量结果方法 Method of obtaining measurement results with 200 bits
	 *
	 * @param data
	 */
	private void readNewMeasureResult(byte[] data) {
		if(realByte.length < 200) {
			realByte = ArrayUtils.addAll(realByte, data);
		}
		//来自仪器，默认展示test数据 From the instrument, the default display test data
		if(realByte.length == 200) {
			byte[] end = ArrayUtils.subarray(realByte, 198, 199);
			if(HexUtil.formatHexString(realByte).startsWith("bb02") && HexUtil.formatHexString(end).equals("ff")) {
				initStatus();
				ReadMeasureDataBean dataBean = DataParse.parseReadMeasureData(realByte);
				Intent intent = new Intent(Constant.READ_MEASURE_DATA);
				intent.putExtra("data", dataBean);
				mContext.sendBroadcast(intent);
				Log.i(TAG, "readMeasureData===>" + dataBean.toString());
			}
		}

	}

	/**
	 * 連接設備後初始化
	 */
	private void initStatus() {
		if(disposable != null && !disposable.isDisposed()) {
			disposable.dispose();
		}
	}

	/**
	 * 透過 RxJava 來傳送資料給光譜儀
	 *
	 * @param order 請問要對光譜儀做什麼呢?
	 */
	@Override public void setOrder(String order) {

		this.order = order;
		myHandle.postDelayed(() -> { //todo: 這層 Handle 不知道是要做什麼? 透過 rxJava 應該不需要去新開 Thread
			if(isConnected()) {

				//透過非同步傳送指令給光譜儀(retry 已經移除，因原機制不管有無失敗都會重複呼叫)
				Observable.intervalRange(0, 1, 0, 0, TimeUnit.SECONDS)
						.subscribeOn(Schedulers.io())
						.subscribe(new Observer<Long>()
						{
							@Override
							public void onSubscribe(Disposable d) {
								disposable = d;
							}

							@Override
							public void onNext(Long aLong) {
								postOrder();
							}

							@Override
							public void onError(Throwable e) {
								//todo: 如果要 retry 的話要坐在這, 要考慮 setNotify failed 的情況
								Log.d(TAG, e.getMessage());
								Intent intent = new Intent(Constant.ON_FAIL);
								intent.putExtra(Constant.ON_FAIL, order);
								mContext.sendBroadcast(intent);
							}

							@Override
							public void onComplete() {
								disposable.dispose();
							}
						});

			}
			else {
				Log.i(TAG, "Bluetooth spectrometer disconnect");
				super.connect_init = false;
				super.adjust_success = false;
			}
		}, 100);
	}

	private final BleWriteCallback bleWriteCallback = new BleWriteCallback()
	{
		@Override
		public void onWriteSuccess(int current, int total, byte[] justWrite) {
			Log.d(TAG, "WriteSuccess：" + HexUtil.formatHexString(justWrite));
		}

		@Override
		public void onWriteFailure(BleException exception) {
			Log.e(TAG, "onWriteFailure:" + exception.getDescription());
		}
	};

	/**
	 * 傳送訊息給光譜儀
	 */
	private void postOrder() {

		realByte = new byte[] {};
		// wakeUp
		BleManager.getInstance().write(connectDevice, serviceUUid,
				writeChaUUid, HexUtil.hexStringToBytes("bbf0000000000000ff00"), bleWriteCallback);

		try {
			Thread.sleep(100);
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}

		switch(order) {
			case Constant.ENTER_OTA:
				BleManager.getInstance().write(connectDevice, serviceUUid, writeChaUUid,
						MachineCmd.enterOTACmd(), bleWriteCallback);
				break;
			case Constant.MEASURE: // 测量(measure)
				BleManager.getInstance().write(connectDevice, serviceUUid,
						writeChaUUid, MachineCmd.measureCmd(Constant.TYPE_MEASURE_MODE_SCI), bleWriteCallback);
				break;
			case Constant.READ_MEASURE_DATA: // 读取测量结果(Read the measurement results)
				BleManager.getInstance().write(connectDevice, serviceUUid,
						writeChaUUid, MachineCmd.readMeasureDataCmd(Constant.TYPE_MEASURE_MODE_SCI_NEW), bleWriteCallback);
				break;
			case Constant.READ_LAB_MEASURE_DATA: // 读取Lab测量结果(GetLabMeasureResult)
				BleManager.getInstance().write(connectDevice, serviceUUid,
						writeChaUUid, MachineCmd.readLabMeasureDataCmd(Constant.TYPE_MEASURE_MODE_SCI), bleWriteCallback);
				break;
			case Constant.READ_RGB_MEASURE_DATA: // 读取RGB测量结果(GetRgbMeasureResult)
				BleManager.getInstance().write(connectDevice, serviceUUid,
						writeChaUUid, MachineCmd.readRGBMeasureDataCmd(Constant.TYPE_MEASURE_MODE_SCI), bleWriteCallback);
				break;
			case Constant.GET_STANDARD_DATA_COUNT: // 获取标样数据数量(GetStandardDataCount)
				BleManager.getInstance().write(connectDevice, serviceUUid,
						writeChaUUid, HexUtil.hexStringToBytes("bb16000000000000ff00"), bleWriteCallback);
				break;
			case Constant.GET_STANDARD_DATA_FOR_NUM: // 获取某一条标样数据(GetStandardDataIndex)
				byte[] b = new byte[ 2 ];
				ByteUtil.putShort(b, index, 0);
				BleManager.getInstance().write(connectDevice, serviceUUid, writeChaUUid,
						HexUtil.hexStringToBytes("bb1601" + HexUtil.formatHexString(b) + "000000ff00"), bleWriteCallback);
				break;
			case Constant.POST_STANDARD_DATA: // 下载数据到仪器(DownloadDataToDevice)
				if(standardDataBean != null) {
					BleManager.getInstance().write(connectDevice, serviceUUid,
							writeChaUUid, MachineCmd.postStandardDataCmd(standardDataBean), true, bleWriteCallback);
				}
				break;
			case Constant.SET_DEVICE_TIME: // 同步时间(SyncTime)
				int systemTime = (int) (System.currentTimeMillis() / 1000);
				BleManager.getInstance().write(connectDevice, serviceUUid,
						writeChaUUid, MachineCmd.setDeviceTimeCmd(systemTime), bleWriteCallback);
				break;
			case Constant.BLACK_ADJUST: // 黑校准(BlackAdjust)
				BleManager.getInstance().write(connectDevice, serviceUUid,
						writeChaUUid, MachineCmd.blackAdjustCmd(), bleWriteCallback);
				break;
			case Constant.WHITE_ADJUST: // 白校准(WhiteAdjust)
				BleManager.getInstance().write(connectDevice, serviceUUid,
						writeChaUUid, MachineCmd.whiteAdjustCmd(), bleWriteCallback);
				break;
			case Constant.GET_DEVICE_ADJUST_STATE: // 读取校准状态(ReadAdjustState)
				byte[] bytes = new byte[ 10 ];
				bytes[ 0 ] = (byte) 0xbb;
				bytes[ 1 ] = (byte) 0x1e;
				bytes[ 2 ] = (byte) 0x00;
				bytes[ 3 ] = (byte) 0x00;
				bytes[ 4 ] = (byte) 0x00;
				bytes[ 5 ] = (byte) 0x00;
				bytes[ 6 ] = (byte) 0x00;
				bytes[ 7 ] = (byte) 0x00;
				bytes[ 8 ] = (byte) 0xff;
				bytes[ 9 ] = (byte) 0x00;
				BleManager.getInstance().write(connectDevice, serviceUUid,
						writeChaUUid, bytes, bleWriteCallback);
				break;
			case Constant.DELETE_ALL_STANDARD_DATA: // 删除所有数据(DeleteAllData)
				BleManager.getInstance().write(connectDevice, serviceUUid,
						writeChaUUid, MachineCmd.deleteAllStandardDataCmd(), bleWriteCallback);
				break;
			case Constant.DELETE_STANDARD_DATA_FOR_NUM: // 删除某一条标样(Delete a standard sample)
				BleManager.getInstance().write(connectDevice, serviceUUid,
						writeChaUUid, MachineCmd.deleteStandardDataForNumberCmd(index), bleWriteCallback);
				break;
			case Constant.GET_DEVICE_POWER_INFO: // 读取仪器电量(GetPowerInfo)
				BleManager.getInstance().write(connectDevice, serviceUUid,
						writeChaUUid, MachineCmd.getDevicePowerInfoCmd(), bleWriteCallback);
				break;
			case Constant.SET_DEVICE_DISPLAY_PARAM: // 设置仪器显示参数(SetDisplayParam)
				if(displayParam != null) {
					BleManager.getInstance().write(connectDevice, serviceUUid,
							writeChaUUid, MachineCmd.setDeviceDisplayParamCmd(displayParam), bleWriteCallback);
				}
				break;
			case Constant.SET_TOLERANCE: // 设置仪器容差(SetTolerance)
				if(toleranceBean != null) {
					BleManager.getInstance().write(connectDevice, serviceUUid,
							writeChaUUid, MachineCmd.setToleranceCmd(toleranceBean), bleWriteCallback);
				}
				break;
		}
	}

}

