package com.pbn.beers.bean;

import com.pbn.beers.utils.Constant;

/**
 * 顯示光譜儀設定參數
 */
public class DisplayParam
{
	private final int measureMode;
	private final int firstLightSource;
	private final int firstAngle;
	private final int secondLightSource;
	private final int secondAngle;
	private final int colorSpace; //颜色空间(ColorSpace)
	private final int colorDiff; //色差公式(Colour-difference formula)
	
	public DisplayParam() {
		this.measureMode = Constant.TYPE_MEASURE_MODE_SCI;
		this.firstLightSource = Constant.TYPE_LIGHT_SOURCE_D50;
//		this.firstLightSource = Constant.TYPE_LIGHT_SOURCE_D65;
		
		this.firstAngle = Constant.TYPE_ANGLE_2;
		this.secondLightSource = Constant.TYPE_LIGHT_SOURCE_D50;
//		this.secondLightSource = Constant.TYPE_LIGHT_SOURCE_D65;
		this.secondAngle = Constant.TYPE_ANGLE_2;
		this.colorSpace = Constant.TYPE_COLOR_SPACE_LAB_CIE;
		this.colorDiff = Constant.TYPE_COLOR_DIFFERENCE_DE_00;
	}
	
	public int getMeasureMode() {
		return measureMode;
	}
	
	public int getFirstLightSource() {
		return firstLightSource;
	}
	
	public int getFirstAngle() {
		return firstAngle;
	}
	
	public int getColorSpace() {
		return colorSpace;
	}
	
	public int getColorDiff() {
		return colorDiff;
	}
}
