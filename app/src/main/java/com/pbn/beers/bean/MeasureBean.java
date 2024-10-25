package com.pbn.beers.bean;

import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * 設定譜儀量參數
 */
public class MeasureBean implements Serializable
{
	/**
	 * 测量模式 0-SCI 1-SCE 2-SCI+SCE
	 */
	private byte measureMode;
	
	/**
	 * 测量状态代碼
	 * 0 - measurement success (0-测量成功)
	 * 1 - measurement failure (1-测量失败)
	 * 2 - measurement failure, calibration status mismatch (2-测量失败，校准状态不匹配)
	 * 3 - measurement failure, hardware error (3-测量失败，硬件错误)
	 */
	private byte measureState;
	
	/**
	 * 开始波长 360代表360nm 400代表400nm 360 stands for 360nm and 400 stands for 400nm
	 */
	private short startWavelengths;
	
	/**
	 * 波长间隔 10代表10nm 10 stands for 10nm
	 */
	private byte intervalWavelengths;
	
	/**
	 * 波长个数 31代表31个波长 31 represents 31 wavelengths
	 */
	private byte wavelengthsCount;
	
	/**
	 * 返回测量模式 Return to measurement mode
	 *
	 * @return 0-SCI 1-SCE 2-SCI+SCE
	 */
	public int getMeasureMode() {
		return measureMode;
	}
	
	/**
	 * 设置测量模式 Set measurement mode
	 *
	 * @param measureMode 0-SCI 1-SCE 2-SCI+SCE
	 */
	public void setMeasureMode(byte measureMode) {
		this.measureMode = measureMode;
	}
	
	/**
	 * 返回测量状态 Return to measurement status
	 *
	 * @return 测量状态 0-测量成功 1-测量失败 2-测量失败，校准状态不匹配 3-测量失败，硬件错误 0 - measurement success 1 - measurement failure 2 - measurement failure, calibration status mismatch 3 - measurement failure, hardware error
	 */
	public int getMeasureState() {
		return measureState;
	}
	
	public boolean getMeasureSuccesses() {
		return measureState == 0x00;
	}
	
	/**
	 * 设置测量状态 Set measurement status
	 *
	 * @param measureState 测量状态 0-测量成功 1-测量失败 2-测量失败，校准状态不匹配 3-测量失败，硬件错误 Measurement status 0 - measurement success 1 - measurement failure 2 - measurement failure, calibration status mismatch 3 - measurement failure, hardware error
	 */
	public void setMeasureState(byte measureState) {
		this.measureState = measureState;
	}
	
	/**
	 * 返回开始波长 Return to start wavelength
	 *
	 * @return 返回开始波长 360代表360nm 400代表400nm 360 stands for 360nm and 400 stands for 400nm
	 */
	public short getStartWavelengths() {
		return startWavelengths;
	}
	
	/**
	 * 设置开始波长 Set start wavelength
	 *
	 * @param startWavelengths 360代表360nm 400代表400nm 360代表360nm 400代表400nm
	 */
	public void setStartWavelengths(short startWavelengths) {
		this.startWavelengths = startWavelengths;
	}
	
	/**
	 * 返回波长间隔 Return wavelength interval
	 *
	 * @return 返回波长间隔  10代表10nm  10代表10nm
	 */
	public byte getIntervalWavelengths() {
		return intervalWavelengths;
	}
	
	/**
	 * 设置波长间隔 Set wavelength interval
	 *
	 * @param intervalWavelengths 10代表10nm 10 stands for 10nm
	 */
	public void setIntervalWavelengths(byte intervalWavelengths) {
		this.intervalWavelengths = intervalWavelengths;
	}
	
	/**
	 * 返回波长个数 Number of returned wavelengths
	 *
	 * @return 返回波长个数 31代表31个波长 The number of returned wavelengths 31 represents 31 wavelengths
	 */
	public byte getWavelengthsCount() {
		return wavelengthsCount;
	}
	
	/**
	 * 设置返回波长个数 Set the number of return wavelengths
	 *
	 * @param wavelengthsCount 31代表31个波长 31 represents 31 wavelengths
	 */
	public void setWavelengthsCount(byte wavelengthsCount) {
		this.wavelengthsCount = wavelengthsCount;
	}
	
	@NonNull
	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();
		/*builder.append("测量模式：")// measureMode
				.append(judgeMeasureMode(measureMode))
				.append("\n")
				.append("测量状态：") // measureState
				.append(judgeMeasureState(measureState))
				.append("\n")
				.append("开始波长：")//  startWavelengths
				.append(startWavelengths)
				.append("\n")
				.append("波长间隔：")// intervalWavelengths
				.append(intervalWavelengths)
				.append("\n")
				.append("波长个数x151：")// wavelengthsCount
				.append(wavelengthsCount)
				.append("\n");
*/
		
		return builder.toString();
	}
	
	private String judgeMeasureMode(byte b) {
		String str;
		if(b == 0x00) {
			str = "SCI";
		}
		else if(b == 0x01) {
			str = "SCE";
		}
		else if(b == 0x02) {
			str = "SCI+SCE";
		}
		else {
			str = "解析错误";// Parsing error
		}
		return str;
	}
	
	private String judgeMeasureState(byte b) {
		String str;
		if(b == 0x00) {
			str = "测量成功";// Measurement successful
		}
		else if(b == 0x01) {
			str = "测量失败";// Measurement failure
		}
		else if(b == 0x02) {
			str = "测量失败，校准状态不匹配";//  Measurement failed, calibration status mismatch
		}
		else if(b == 0x03) {
			str = "测量失败，硬件错误";// Measurement failure, hardware error
		}
		else {
			str = "解析错误";// Parsing error
		}
		return str;
	}
	
}
