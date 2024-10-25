package com.pbn.beers.bean;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class ReadLabMeasureDataBean implements Serializable
{
	private int measureMode;
	private int lightSource = 3;
	private int angle;
	private float[] lab;
	
	/**
	 * 返回测量模式 Return to measurement mode
	 *
	 * @return 0-SCI 1-SCE 2-SCI+SCE
	 */
	public int getMeasureMode() {
		return measureMode;
	}
	
	/**
	 * @param measureMode 0-SCI 1-SCE 2-SCI+SCE
	 */
	public void setMeasureMode(int measureMode) {
		this.measureMode = measureMode;
	}
	
	/**
	 * 返回光源 Return to light source
	 *
	 * @return
	 */
	public int getLightSource() {
		return lightSource;
	}
	
	/**
	 * @param lightSource 光源 light source
	 */
	public void setLightSource(int lightSource) {
		this.lightSource = lightSource;
	}
	
	/**
	 * Return angle
	 *
	 * @return 0-2° 1-10°
	 */
	public int getAngle() {
		return angle;
	}
	
	/**
	 * @param angle 0-2° 1-10°
	 */
	public void setAngle(int angle) {
		this.angle = angle;
	}
	
	/**
	 * 返回Lab数组 Return lab array
	 *
	 * @return
	 */
	public float[] getLab() {
		return lab;
	}
	
	/**
	 * @param lab
	 */
	public void setLab(float[] lab) {
		this.lab = lab;
	}
	
	@NonNull
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("测量模式：")// measureMode
				.append(judgeMeasureMode((byte) measureMode))
				.append("\n")
				.append("光源：")// lightSource
				.append(judgeLightSource((byte) lightSource))
				.append("\n")
				.append("角度：")// angle
				.append(judgeAngle((byte) angle))
				.append("\n")
				.append("L*:")
				.append(lab[ 0 ])
				.append("\n")
				.append("a*:")
				.append(lab[ 1 ])
				.append("\n")
				.append("b*:")
				.append(lab[ 2 ])
				.append("\n");
		
		return sb.toString();
	}
	
	/**
	 * 判断测量模式 judgeMeasureMode (光譜儀五千沒有包含這個功能
	 */
	public String judgeMeasureMode(byte measureMode) {
		String str;
		if(measureMode == 0x00) {
			str = "SCI";
		}
		else if(measureMode == 0x01) {
			str = "SCE";
		}
		else {
			str = "解析错误"; // Parsing error
		}
		return str;
	}
	
	/**
	 * judgeAngle
	 *
	 * @return 光源 - 度
	 */
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
	
	/**
	 * judgeLightSource
	 *
	 * @return 光源類型
	 */
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
	
}
