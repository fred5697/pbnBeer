package com.pbn.beers.ui.memberCenter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pbn.beers.R;
import com.pbn.beers.bean.BluetoothBean;
import com.pbn.beers.ble.IBluetoothManager;
import com.pbn.beers.databinding.ActivityBleDeviceSearchBinding;
import com.pbn.beers.ui.adapter.BluetoothInfoAdapter;
import com.pbn.beers.utils.Constant;
import com.pbn.beers.utils.WaitDialogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BleDeviceSearch extends AppCompatActivity
{
	private static final int REQUEST_BLUETOOTH_PERMISSIONS = 1001;
	private static final long SCAN_DURATION_MS = 3000L;
	private final Handler mainHandler = new Handler(Looper.getMainLooper());
	private ActivityResultLauncher<Intent> enableBluetoothLauncher;
	private final ScanCallback scanCallback = new ScanCallback() {
		@Override
		public void onScanResult(int callbackType, ScanResult result) {
			handleScanResult(result);
		}

		@Override
		public void onBatchScanResults(List<ScanResult> results) {
			for(ScanResult result : results) {
				handleScanResult(result);
			}
		}

		@Override
		public void onScanFailed(int errorCode) {
			WaitDialogUtil.dismiss();
			mMsg.set("Scan failed: " + errorCode);
			Toast.makeText(BleDeviceSearch.this, "Bluetooth scan failed: " + errorCode, Toast.LENGTH_LONG).show();
		}
	};
	private final Runnable stopScanRunnable = this::stopScan;

	private ActivityBleDeviceSearchBinding binding;
	private final ObservableField<String> mMsg = new ObservableField<>("beforeInit");
	private BluetoothInfoAdapter bluetoothInfoAdapter;
	private final List<BluetoothBean> bluetoothBeanList = new ArrayList<>();
	private BluetoothAdapter bluetoothAdapter;
	private BluetoothLeScanner bluetoothLeScanner;
	private boolean isScanning;
	public String sn = "CM";
	public String ss0 = "";
	public String ssn = "";
	private static final int EXPIRATION_YEAR = 2024;
	private static final int EXPIRATION_MONTH = 11;
	private static final int EXPIRATION_DAY = 30;
	private static final int WARNING_DAYS_BEFORE_EXPIRATION = 30;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		enableBluetoothLauncher = registerForActivityResult(
				new ActivityResultContracts.StartActivityForResult(),
				result -> {
					if(bluetoothAdapter != null && bluetoothAdapter.isEnabled()) {
						initializeBluetoothSearch();
					} else {
						mMsg.set("Bluetooth still disabled");
						Toast.makeText(this, "Bluetooth must be enabled to pair devices.", Toast.LENGTH_LONG).show();
					}
				}
		);
		binding = DataBindingUtil.setContentView(this, R.layout.activity_ble_device_search);
		binding.setActivityMsg(mMsg);
		binding.getActivityMsg().set("onCreate");
		initBluetoothInfoAdapter();
		ensureBluetoothReady();
	}

	@Override
	protected void onDestroy() {
		stopScan();
		super.onDestroy();
	}

	private void ensureBluetoothReady() {
		if(hasRequiredBluetoothPermissions()) {
			initializeBluetoothSearch();
		} else {
			requestBluetoothPermissions();
		}
	}

	private void initializeBluetoothSearch() {
		if(bluetoothLeScanner != null) {
			return;
		}
		BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
		if(bluetoothManager == null) {
			mMsg.set("Bluetooth unavailable");
			Toast.makeText(this, "Bluetooth is unavailable on this device.", Toast.LENGTH_LONG).show();
			return;
		}
		bluetoothAdapter = bluetoothManager.getAdapter();
		if(bluetoothAdapter == null) {
			mMsg.set("Bluetooth unavailable");
			Toast.makeText(this, "Bluetooth is unavailable on this device.", Toast.LENGTH_LONG).show();
			return;
		}
		if(!bluetoothAdapter.isEnabled()) {
			mMsg.set("Please enable Bluetooth");
			enableBluetoothLauncher.launch(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE));
			return;
		}
		bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
		if(bluetoothLeScanner == null) {
			mMsg.set("BLE scanner unavailable");
			Toast.makeText(this, "Bluetooth LE scanner is unavailable.", Toast.LENGTH_LONG).show();
			return;
		}
		search();
		mMsg.set("BluetoothSearch");
	}

	private boolean hasRequiredBluetoothPermissions() {
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
			return ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED
					&& ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED;
		}
		return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
	}

	private void requestBluetoothPermissions() {
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
			requestPermissions(new String[]{Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT}, REQUEST_BLUETOOTH_PERMISSIONS);
		} else {
			requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_BLUETOOTH_PERMISSIONS);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if(requestCode != REQUEST_BLUETOOTH_PERMISSIONS) {
			return;
		}
		boolean allGranted = grantResults.length > 0;
		for(int result : grantResults) {
			if(result != PackageManager.PERMISSION_GRANTED) {
				allGranted = false;
				break;
			}
		}
		if(allGranted) {
			initializeBluetoothSearch();
		} else {
			mMsg.set("Bluetooth permission denied");
			Toast.makeText(this, "Bluetooth permission is required to pair devices.", Toast.LENGTH_LONG).show();
		}
	}

	public void search() {
		if(bluetoothLeScanner == null || isScanning) {
			return;
		}
		bluetoothBeanList.clear();
		bluetoothInfoAdapter.setNewInstance(new ArrayList<>());
		WaitDialogUtil.show(BleDeviceSearch.this, getString(R.string.search_device));
		isScanning = true;
		bluetoothLeScanner.startScan(scanCallback);
		mainHandler.removeCallbacks(stopScanRunnable);
		mainHandler.postDelayed(stopScanRunnable, SCAN_DURATION_MS);
	}

	private void stopScan() {
		mainHandler.removeCallbacks(stopScanRunnable);
		if(bluetoothLeScanner != null && isScanning) {
			bluetoothLeScanner.stopScan(scanCallback);
		}
		isScanning = false;
		WaitDialogUtil.dismiss();
	}

	@SuppressLint("NotifyDataSetChanged")
	private void handleScanResult(ScanResult result) {
		if(result == null || result.getDevice() == null) {
			return;
		}
		String name = result.getDevice().getName();
		String address = result.getDevice().getAddress();
		if(name == null || "NULL".equals(name) || !name.startsWith(sn) || address == null) {
			return;
		}
		for(BluetoothBean item : bluetoothBeanList) {
			if(address.equals(item.getMac())) {
				return;
			}
		}
		BluetoothBean bluetoothBean = new BluetoothBean();
		bluetoothBean.setName(name);
		bluetoothBean.setMac(address);
		bluetoothBean.setRssi(String.valueOf(result.getRssi()));
		bluetoothBean.setPreParse("");
		bluetoothBean.setBleDevice(new BleDevice(result.getDevice()));
		bluetoothBeanList.add(bluetoothBean);
		bluetoothInfoAdapter.setNewInstance(new ArrayList<>(bluetoothBeanList));
		bluetoothInfoAdapter.notifyDataSetChanged();
	}

	private void connectBluetoothDevice(String mac) {
		Log.d("BleDeviceSearch", "FLOW: connectBluetoothDevice mac=" + mac);
		BleManager.getInstance().connect(mac, new BleGattCallback()
		{
			@Override
			public void onStartConnect() {
				Log.d("BleDeviceSearch", "FLOW: onStartConnect");
				WaitDialogUtil.show(BleDeviceSearch.this, getString(R.string.connect));
			}

			@Override
			public void onConnectFail(BleDevice bleDevice, BleException exception) {
				WaitDialogUtil.dismiss();
				Log.e("BleDeviceSearch", "FLOW: Connect failed: " + (exception == null ? "unknown" : exception.getDescription()));
			}

			@Override
			public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {
				Log.d("BleDeviceSearch", "FLOW: Connect success status=" + status);
				WaitDialogUtil.dismiss();
				initDevice(bleDevice);
				Toast.makeText(BleDeviceSearch.this, getString(R.string.connect_success), Toast.LENGTH_SHORT).show();
				mainHandler.postDelayed(() -> {
					startActivity(new Intent(BleDeviceSearch.this, BleDeviceAdjust.class));
					finish();
				}, 250);
			}

			@Override
			public void onDisConnected(boolean isActiveDisConnected, BleDevice device, BluetoothGatt gatt, int status) {
				Log.i("BleDeviceSearch", "FLOW: onDisConnected status=" + status + " active=" + isActiveDisConnected);
			}
		});
	}

	private void initBluetoothInfoAdapter() {
		bluetoothInfoAdapter = new BluetoothInfoAdapter(R.layout.item_bluetooth_info);
		binding.rvBluetoothInfo.setLayoutManager(new LinearLayoutManager(this));
		binding.rvBluetoothInfo.setAdapter(bluetoothInfoAdapter);
		bluetoothInfoAdapter.setOnItemClickListener((adapter, view, position) -> {
			stopScan();
			BluetoothBean bluetoothBean = bluetoothBeanList.get(position);
			BleDevice bleDevice = bluetoothBean.getBleDevice();
			if (bleDevice == null) {
				return;
			}

			WaitDialogUtil.show(this, "Connecting...");
			BleManager.getInstance().connect(bleDevice, new BleGattCallback() {
				@Override
				public void onStartConnect() {
				}

				@Override
				public void onConnectFail(BleDevice bleDevice, BleException exception) {
					WaitDialogUtil.dismiss();
					mMsg.set("Connect Fail");
				}

				@Override
				public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {
					// Register the callback BEFORE initDevice() calls setNotify(),
					// so we are notified when the BLE notification listener is ready.
					IBluetoothManager.getInstance().setNotifyCallback(new IBluetoothManager.NotifyCallback() {
						@Override
						public void onNotifySuccess() {
							// fired from BLE thread – post to main thread
							mainHandler.post(() -> {
								WaitDialogUtil.dismiss();
								startActivity(new Intent(BleDeviceSearch.this, BleDeviceAdjust.class));
								// Finish BleDeviceSearch so the back stack is
								// MainActivity → BleDeviceAdjust. When BleDeviceAdjust
								// finishes after calibration it returns straight to
								// MemberCenterFragment without passing through here again.
								finish();
							});
						}
						@Override
						public void onNotifyFailure(String reason) {
							mainHandler.post(() -> {
								WaitDialogUtil.dismiss();
								Toast.makeText(BleDeviceSearch.this, "BLE通知設置失敗: " + reason, Toast.LENGTH_LONG).show();
							});
						}
					});
					// initDevice sets connectDevice, calls setNotify(), sets connect_init=true
					mainHandler.post(() -> initDevice(bleDevice));
				}


				@Override
				public void onDisConnected(boolean isActiveDisConnected, BleDevice device, BluetoothGatt gatt, int status) {
					WaitDialogUtil.dismiss();
					mMsg.set("Disconnected");
				}
			});
		});
	}

	private void initDevice(BleDevice bleDevice) {
		IBluetoothManager.getInstance().connectDevice = bleDevice;
		IBluetoothManager.getInstance().setNotify();
		IBluetoothManager.getInstance().connect_init = true;
	}

	private boolean isAppExpired() {
		Date currentDate = new Date();
		Calendar expirationCalendar = Calendar.getInstance();
		expirationCalendar.set(EXPIRATION_YEAR, EXPIRATION_MONTH, EXPIRATION_DAY, 0, 0, 0);
		return currentDate.after(expirationCalendar.getTime());
	}

	private boolean isWarningPeriod() {
		Date currentDate = new Date();
		Calendar expirationCalendar = Calendar.getInstance();
		expirationCalendar.set(EXPIRATION_YEAR, EXPIRATION_MONTH, EXPIRATION_DAY, 0, 0, 0);
		expirationCalendar.add(Calendar.DAY_OF_YEAR, -WARNING_DAYS_BEFORE_EXPIRATION);
		return currentDate.after(expirationCalendar.getTime()) && !isAppExpired();
	}

	private class GetDataSn extends AsyncTask<Void, Void, String> {
		@Override
		protected String doInBackground(Void... voids) {
			String result = "";
			try {
				URL url = new URL("https://fredkuo.idv.tw/myapi/getSn.php?sn=" + ss0);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				while((line = reader.readLine()) != null) {
					result += line;
				}
				reader.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
			new Gson().fromJson(result, JsonObject.class);
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			try {
				JSONObject jsonObject = new JSONObject(result);
				ssn = jsonObject.getString("ssn");
				if(ssn.equals(ss0)) {
					writeFile("installBit.txt", "1", BleDeviceSearch.this);
					writeFile("Tstamp.txt", String.valueOf(System.currentTimeMillis()), BleDeviceSearch.this);
					writeFile("snString.txt", ss0, BleDeviceSearch.this);
					new regSn().execute();
				} else {
					writeFile("installBit.txt", "0", BleDeviceSearch.this);
				}
			} catch(JSONException e) {
				e.printStackTrace();
			}
		}
	}

	private class deActiveSn extends AsyncTask<Void, Void, String> {
		@Override
		protected String doInBackground(Void... voids) {
			try {
				URL url = new URL("https://fredkuo.idv.tw/myapi/deActiveSn.php?sn=" + ss0);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.getResponseCode();
			} catch(IOException e) {
				e.printStackTrace();
			}
			writeFile("installBit.txt", "0", BleDeviceSearch.this);
			return null;
		}
	}

	public void writeFile(String fileName, String content, Context context) {
		File file = new File(context.getFilesDir(), fileName);
		try(FileOutputStream fos = new FileOutputStream(file, false)) {
			fos.write(content.getBytes());
			fos.flush();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public String readFile(String fileName, Context context) {
		File file = new File(context.getFilesDir(), fileName);
		if(!file.exists()) {
			return "File not found!";
		}
		StringBuilder content = new StringBuilder();
		try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			while((line = reader.readLine()) != null) {
				content.append(line).append("\n");
			}
		} catch(IOException e) {
			e.printStackTrace();
			return "Error reading file: " + e.getMessage();
		}
		return content.toString().trim();
	}

	private class regSn extends AsyncTask<Void, Void, String> {
		@Override
		protected String doInBackground(Void... voids) {
			try {
				URL url = new URL("https://fredkuo.idv.tw/myapi/regSn.php?sn=" + ss0);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.getResponseCode();
			} catch(IOException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
}
