package com.pbn.beers.bean;

import androidx.annotation.NonNull;

import com.pbn.beers.utils.ByteUtil;

import java.io.Serializable;

/**
 * 讀取光譜儀量測數據
 */
public class ReadMeasureDataBean implements Serializable
{
	/**
	 * 0-SCI 1-SCE 2-SCI+SCE
	 */
	private byte measureMode;
	private byte lightSource = 2;
	private byte angle;
	/**
	 * 0-反射率 1-lab
	 */
	private byte dataMode;
	
	/**
	 * 开始波长 startWavelengths
	 */
	private short startWavelengths;
	
	/**
	 * 波长间隔 intervalWavelengths
	 */
	private byte intervalWavelengths;
	
	/**
	 * 波长个数 wavelengthsCount
	 */
	private byte wavelengthsCount;
	private float[] data;
	private float[] labData;
	
	/**
	 * @return 0-SCI 1-SCE 2-SCI+SCE
	 */
	public int getMeasureMode() {
		return measureMode;
	}

	
	/**
	 * @param measureMode 0-SCI 1-SCE 2-SCI+SCE
	 */


	public int getLightSource() {
		return lightSource;
	}





	
	/**
	 * @return 0-reflectivity 1-lab
	 */
	public int getDataMode() {
		return dataMode;
	}
	
	/**
	 * @param dataMode 0-reflectivity 1-lab
	 */
	public void setDataMode(byte dataMode) {
		this.dataMode = dataMode;
	}
	
	/**
	 * @return 360代表360nm 400代表400nm 360 stands for 360nm and 400 stands for 400nm
	 */
	public short getStartWavelengths() {
		return startWavelengths;
	}
	
	/**
	 * @param startWavelengths 开始波长 360代表360nm 400代表400nm 360 stands for 360nm and 400 stands for 400nm
	 */
	public void setStartWavelengths(short startWavelengths) {
		this.startWavelengths = startWavelengths;
	}
	
	/**
	 * @return 10代表10nm 10 stands for 10nm
	 */
	public byte getIntervalWavelengths() {
		return intervalWavelengths;
	}
	
	/**
	 * @param intervalWavelengths 10代表10nm 10 stands for 10nm
	 */
	public void setIntervalWavelengths(byte intervalWavelengths) {
		this.intervalWavelengths = intervalWavelengths;
	}
	
	/**
	 * @return 31 represents 31 wavelengths
	 */
	public byte getWavelengthsCount() {
		return wavelengthsCount;
	}
	
	/**
	 * @param wavelengthsCount 31代表31个波长 31 represents 31 wavelengths
	 */
	public void setWavelengthsCount(byte wavelengthsCount) {
		this.wavelengthsCount = wavelengthsCount;
	}
	
	/**
	 * 返回光谱反射率数组 Returns the spectral reflectance array
	 *
	 * @return
	 */
	public float[] getData() {
		return data;
	}
	
	/**
	 * @param data
	 */
	public void setData(float[] data) {
		this.data = data;
	}



	/**
	 * 返回 Lab数组 Return lab array
	 *
	 * @return
	 */
	public float[] getLabData() {
		return labData;
	}
	
	/**
	 * 设置 Lab数组 Set lab array
	 *
	 * @param labData
	 */
	public void setLabData(float[] labData) {
		this.labData = labData;
	}
	
	
	@NonNull
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("测量模式：")// measureMode
				.append(judgeMeasureMode(measureMode))
				.append("\n")
				.append("数据模式：")//  dataMode
				.append(judgeDataMode(dataMode))
				.append("\n")
				.append("光源：")// lightSource
				.append(judgeLightSource(lightSource))
				.append("\n")
				.append("角度：")// angle
				.append(judgeAngle(angle))
				.append("\n")
				.append("开始波长：")// startWavelengths
				.append(startWavelengths)
				.append("\n")
				.append("波长间隔：")// intervalWavelengths
				.append(intervalWavelengths)
				.append("\n")
				.append("波长个数x2：")// wavelengthsCount
				.append(wavelengthsCount)
				.append("\n")
				.append("反射率：")// reflectivity
				.append(ByteUtil.printArrays(data));
		System.out.println("data values : " + sb);

		if(labData != null) {
			sb.append("\n")
					.append("L*:")
					.append(labData[ 0 ])
					.append("\n")
					.append("a*:")
					.append(labData[ 1 ])
					.append("\n")
					.append("b*:")
					.append(labData[ 2 ]);
		}
		
		return sb.toString();
	}
	
	private String judgeMeasureMode(byte b) {
		String str;
		if(b == 0x00) {
			str = "SCI";
		}
		else if(b == 0x01) {
			str = "SCE";
		}
		else if(b == 0x10) {
			str = "SCI";
		}
		else if(b == 0x11) {
			str = "SCE";
		}
		else {
			str = "解析错误";// Parsing error
		}
		return str;
	}
	
	private String judgeDataMode(byte b) {
		String str;
		if(b == 0x00) {
			str = "反射率";// reflectivity
		}
		else if(b == 0x01) {
			str = "lab";
		}
		else {
			str = "解析错误";// Parsing error
		}
		return str;
	}

	public String judgeLightSource(byte lightSource) {
		String str;

		if(lightSource == 0x00) {
			str = "A";
		}
		else if(lightSource == 0x01) {
			str = "C";
		}
		else if(lightSource == 0x02) {
			str = "D50";
		}
		else if(lightSource == 0x03) {
			str = "D55";
		}
		else if(lightSource == 0x04) {
			str = "D65";
		}
		else if(lightSource == 0x05) {
			str = "D75";
		}
		else if(lightSource == 0x06) {
			str = "F1";
		}
		else if(lightSource == 0x07) {
			str = "F2";
		}
		else if(lightSource == 0x08) {
			str = "F3";
		}
		else if(lightSource == 0x09) {
			str = "F4";
		}
		else if(lightSource == 0x0a) {
			str = "F5";
		}
		else if(lightSource == 0x0b) {
			str = "F6";
		}
		else if(lightSource == 0x0c) {
			str = "F7";
		}
		else if(lightSource == 0x0d) {
			str = "F8";
		}
		else if(lightSource == 0x0e) {
			str = "F9";
		}
		else if(lightSource == 0x0f) {
			str = "F10";
		}
		else if(lightSource == 0x10) {
			str = "F11";
		}
		else if(lightSource == 0x11) {
			str = "F12";
		}
		else if(lightSource == 0x12) {
			str = "CWF";
		}
		else if(lightSource == 0x13) {
			str = "U30";
		}
		else if(lightSource == 0x14) {
			str = "DLF";
		}
		else if(lightSource == 0x15) {
			str = "NBF";
		}
		else if(lightSource == 0x16) {
			str = "TL83";
		}
		else if(lightSource == 0x17) {
			str = "TL84";
		}
		else if(lightSource == 0x18) {
			str = "U35";
		}
		else if(lightSource == 0x19) {
			str = "B";
		}
		else {
			str = "解析错误";// Parsing error
		}
		return str;
	}

	public String judgeAngle(byte angle) {
		String str;
		if(angle == 0x00) {
			str = "2°";
		}
		else if(angle == 0x01) {
			str = "10°";
		}
		else {
			str = "解析错误";// Parsing error
		}
		return str;
	}

}
