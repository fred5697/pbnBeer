package com.pbn.beers.utils;

public class Constant
{
	
	// 测量模式
	public static final int TYPE_MEASURE_MODE_SCI = 0x00;
	public static final int TYPE_MEASURE_MODE_SCE = 0x01;
	public static final int TYPE_MEASURE_MODE_SCI_NEW = 0x10;
	public static final int TYPE_MEASURE_MODE_SCE_NEW = 0x11;
	public static final int TYPE_MEASURE_MODE_SCI_SCE = 0x02;
	// 光源
	public static final int TYPE_LIGHT_SOURCE_A = 0x03;
	public static final int TYPE_LIGHT_SOURCE_C = 0x04;
	public static final int TYPE_LIGHT_SOURCE_D50 = 0x05;
	public static final int TYPE_LIGHT_SOURCE_D55 = 0x06;
	public static final int TYPE_LIGHT_SOURCE_D65 = 0x07;
	public static final int TYPE_LIGHT_SOURCE_D75 = 0x08;
	public static final int TYPE_LIGHT_SOURCE_F1 = 0x09;
	public static final int TYPE_LIGHT_SOURCE_F2 = 0x0A;
	public static final int TYPE_LIGHT_SOURCE_F3 = 0x0B;
	public static final int TYPE_LIGHT_SOURCE_F4 = 0x0C;
	public static final int TYPE_LIGHT_SOURCE_F5 = 0x0D;
	public static final int TYPE_LIGHT_SOURCE_F6 = 0x0E;
	public static final int TYPE_LIGHT_SOURCE_F7 = 0x0F;
	public static final int TYPE_LIGHT_SOURCE_F8 = 0x10;
	public static final int TYPE_LIGHT_SOURCE_F9 = 0x11;
	public static final int TYPE_LIGHT_SOURCE_F10 = 0x12;
	public static final int TYPE_LIGHT_SOURCE_F11 = 0x13;
	public static final int TYPE_LIGHT_SOURCE_F12 = 0x14;
	public static final int TYPE_LIGHT_SOURCE_CWF = 0x15;
	public static final int TYPE_LIGHT_SOURCE_U30 = 0x16;
	public static final int TYPE_LIGHT_SOURCE_DLF = 0x17;
	public static final int TYPE_LIGHT_SOURCE_NBF = 0x18;
	public static final int TYPE_LIGHT_SOURCE_TL83 = 0x19;
	public static final int TYPE_LIGHT_SOURCE_TL84 = 0x1A;
	public static final int TYPE_LIGHT_SOURCE_U35 = 0x1B;
	public static final int TYPE_LIGHT_SOURCE_B = 0x1C;
	// 角度
	public static final int TYPE_ANGLE_2 = 0x1D;// 2°
	public static final int TYPE_ANGLE_10 = 0x1E;// 10°
	// 颜色空间
	public static final int TYPE_COLOR_SPACE_LAB_CIE = 0x1F;// lab(CIE)
	public static final int TYPE_COLOR_SPACE_LCH_CIE = 0x20;// lch(CIE)
	public static final int TYPE_COLOR_SPACE_LAB_HUNTER = 0x21;// lab(Hunter)
	public static final int TYPE_COLOR_SPACE_LUV = 0x22;// luv
	public static final int TYPE_COLOR_SPACE_XYZ = 0x23;// XYZ
	public static final int TYPE_COLOR_SPACE_YXY = 0x24;// Yxy
	public static final int TYPE_COLOR_SPACE_RGB = 0x25;// RGB
	public static final int TYPE_COLOR_SPACE_TSYP = 0x26;// 同色异谱
	public static final int TYPE_COLOR_SPACE_REFLECTIVITY = 0x27;// 反射率
	public static final int TYPE_COLOR_SPACE_COVERAGE_RATE = 0x28;// 遮盖率
	public static final int TYPE_COLOR_SPACE_DENSITY_A = 0x29;// DENSITY(A)
	public static final int TYPE_COLOR_SPACE_DENSITY_T = 0x3A;// DENSITY(T)
	public static final int TYPE_COLOR_SPACE_DENSITY_E = 0x3B;// DENSITY(E)
	public static final int TYPE_COLOR_SPACE_DENSITY_M = 0x3C;// DENSITY(M)
	public static final int TYPE_COLOR_SPACE_MUNSELL = 0x3D;// Munsell
	public static final int TYPE_COLOR_SPACE_TINT = 0x3E;// Tint
	public static final int TYPE_COLOR_SPACE_WHITENESS = 0x3F;// 白度
	public static final int TYPE_COLOR_SPACE_YELLOWNESS = 0x40;// 黄度
	public static final int TYPE_COLOR_SPACE_BLACKNESS = 0x41;// 黑度
	public static final int TYPE_COLOR_SPACE_COLORING_POWER = 0x42;// 着色力
	public static final int TYPE_COLOR_SPACE_COLOR_FASTNESS = 0x43;// 色牢度
	//色差公式
	public static final int TYPE_COLOR_DIFFERENCE_DE_AB = 0x44;// dE*ab
	public static final int TYPE_COLOR_DIFFERENCE_DE_CH = 0x45;// dE*ch
	public static final int TYPE_COLOR_DIFFERENCE_DE_00 = 0x46;// dE*00
	public static final int TYPE_COLOR_DIFFERENCE_DE_CMC = 0x47;// dE*cmc
	public static final int TYPE_COLOR_DIFFERENCE_DE_94 = 0x48;// dE*94
	public static final int TYPE_COLOR_DIFFERENCE_DE_AB_HUNTER = 0x49;// dE*ab(Hunter)
	public static final int TYPE_COLOR_DIFFERENCE_DE_UV = 0x4A;// dE*uv
	
	public static final int TYPE_BLUETOOTH_OPEN = 0x4B;
	public static final int TYPE_BLUETOOTH_CLOSE = 0x4C;
	
	public static final int TYPE_AUTO_OFF_TIME_10_MIN = 0x4D;
	public static final int TYPE_AUTO_OFF_TIME_20_MIN = 0x4E;
	public static final int TYPE_BACKLIGHT_TIME_1_SECOND = 0x4F;
	public static final int TYPE_BACKLIGHT_TIME_2_SECOND = 0x50;
	
	public static final int TYPE_SAVE_MODE_AUTO = 0x51;
	public static final int TYPE_SAVE_MODE_MANUAL = 0x52;
	
	public static final int TYPE_DATA_MODE_REFLECTIVITY = 0x53;
	public static final int TYPE_DATA_MODE_LAB = 0x54;
	
	public static final String ENTER_OTA = "ENTER_OTA";
	
	/**
	 * 黑校準
	 */
	public static final String BLACK_ADJUST = "BLACK_ADJUST";
	
	/**
	 * 白校準
	 */
	public static final String WHITE_ADJUST = "WHITE_ADJUST";
	public static final String MEASURE = "MEASURE";
	public static final String READ_MEASURE_DATA = "READ_MEASURE_DATA";
	public static final String READ_LAB_MEASURE_DATA = "READ_LAB_MEASURE_DATA";
	public static final String READ_RGB_MEASURE_DATA = "READ_RGB_MEASURE_DATA";
	public static final String GET_STANDARD_DATA_COUNT = "GET_STANDARD_DATA_COUNT";
	public static final String GET_STANDARD_DATA_FOR_NUM = "GET_STANDARD_DATA_FOR_NUM";
	public static final String DELETE_ALL_STANDARD_DATA = "DELETE_ALL_STANDARD_DATA";
	public static final String DELETE_STANDARD_DATA_FOR_NUM = "DELETE_STANDARD_DATA_FOR_NUM";
	public static final String GET_SAMPLE_COUNT_FOR_STANDARD_NUM = "GET_SAMPLE_COUNT_FOR_STANDARD_NUM";
	public static final String GET_NUM_SAMPLE_DATA_FOR_NUM_STANDARD = "GET_NUM_SAMPLE_DATA_FOR_NUM_STANDARD";
	public static final String DELETE_ALL_SAMPLE_FOR_STANDARD_NUM = "DELETE_ALL_SAMPLE_FOR_STANDARD_NUM";
	public static final String DELETE_NUM_SAMPLE_DATA_FOR_NUM_STANDARD = "DELETE_NUM_SAMPLE_DATA_FOR_NUM_STANDARD";
	public static final String POST_STANDARD_DATA = "POST_STANDARD_DATA";
	public static final String GET_DEVICE_INFO = "GET_DEVICE_INFO";
	public static final String GET_DEVICE_POWER_INFO = "GET_DEVICE_POWER_INFO";
	public static final String GET_DEVICE_ADJUST_STATE = "GET_DEVICE_ADJUST_STAT";
	
	/**
	 * 設定光譜儀的參數
	 */
	public static final String SET_DEVICE_DISPLAY_PARAM = "SET_DEVICE_DISPLAY_PARAM";
	
	/**
	 * 設定光譜儀的容差率(delta 的標準值)
	 */
	public static final String SET_TOLERANCE = "SET_TOLERANCE";
	public static final String SET_BLUETOOTH = "SET_BLETOOTH";
	public static final String SET_POWER_MANAGEMENT_TIME = "SET_POWER_MANAGEMENT_TIME";
	public static final String SET_DEVICE_TIME = "SET_DEVICE_TIME";
	public static final String SET_SAVE_MODE = "SET_SAVE_MODE";
	public static final String SET_BLUETOOTH_NAME = "SET_BLUETOOTH_NAME";
	public static final String ON_FAIL = "ON_FAIL";
	
	
}
