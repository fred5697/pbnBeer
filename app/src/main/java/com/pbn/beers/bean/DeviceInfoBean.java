package com.pbn.beers.bean;

import androidx.annotation.NonNull;

import com.pbn.beers.utils.TimeUtil;

import java.io.Serializable;

public class DeviceInfoBean implements Serializable
{
	private DeviceInfoStruct deviceInfoStruct;
	private PowerInfo powerInfo;
	private DeviceAdjustState deviceAdjustState;
	
	public DeviceInfoStruct getDeviceInfoStruct() {
		return deviceInfoStruct;
	}
	
	public void setDeviceInfoStruct(DeviceInfoStruct deviceInfoStruct) {
		this.deviceInfoStruct = deviceInfoStruct;
	}
	
	public PowerInfo getPowerInfo() {
		return powerInfo;
	}
	
	public void setPowerInfo(PowerInfo powerInfo) {
		this.powerInfo = powerInfo;
	}
	
	public DeviceAdjustState getDeviceAdjustState() {
		return deviceAdjustState;
	}
	
	public void setDeviceAdjustState(DeviceAdjustState deviceAdjustState) {
		this.deviceAdjustState = deviceAdjustState;
	}
	
	public static class PowerInfo implements Serializable
	{
		private byte electricQuantity;
		
		public byte getElectricQuantity() {
			return electricQuantity;
		}
		
		public void setElectricQuantity(byte electricQuantity) {
			this.electricQuantity = electricQuantity;
		}
	}
	
	public static class DeviceAdjustState implements Serializable
	{
		private byte whiteAdjustState;
		private byte blackAdjustState;
		private int whiteADjustTime;
		private int blackAdjustTime;
		
		public byte getWhiteAdjustState() {
			return whiteAdjustState;
		}
		
		public void setWhiteAdjustState(byte whiteAdjustState) {
			this.whiteAdjustState = whiteAdjustState;
		}
		
		public byte getBlackAdjustState() {
			return blackAdjustState;
		}
		
		public void setBlackAdjustState(byte blackAdjustState) {
			this.blackAdjustState = blackAdjustState;
		}
		
		public int getWhiteADjustTime() {
			return whiteADjustTime;
		}
		
		public void setWhiteADjustTime(int whiteADjustTime) {
			this.whiteADjustTime = whiteADjustTime;
		}
		
		public int getBlackAdjustTime() {
			return blackAdjustTime;
		}
		
		public void setBlackAdjustTime(int blackAdjustTime) {
			this.blackAdjustTime = blackAdjustTime;
		}
		
		@NonNull
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("白校准状态：") // White calibration status
					.append(judgeAdjustState(whiteAdjustState))
					.append("\n")
					.append("时间：") // time
					.append(TimeUtil.unixTimestamp2Date(whiteADjustTime))
					.append("\n")
					.append("黑校准状态：") // blackAdjustState
					.append(judgeAdjustState(blackAdjustState))
					.append("\n")
					.append("时间：") // time
					.append(TimeUtil.unixTimestamp2Date(blackAdjustTime));
			return builder.toString();
		}
		
		private String judgeAdjustState(byte b) {
			if(b == 0x00) {
				return "开机后没校准过";// It was not calibrated after startup
			}
			else if(b == 0x01) {
				return "开机后校准过";// Calibrated after startup
			}
			else {
				return "解析错误";// Parsing error
			}
		}
	}
	
}
