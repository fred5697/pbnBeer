package com.pbn.beers.encode;

import com.pbn.beers.bean.DisplayParam;
import com.pbn.beers.bean.StandardSampleDataBean;
import com.pbn.beers.bean.ToleranceBean;
import com.pbn.beers.utils.ByteUtil;
import com.pbn.beers.utils.Constant;

import java.util.Arrays;

public class MachineCmd
{
	public static byte[] enterOTACmd() {
		byte[] bytes = new byte[ 10 ];
		Arrays.fill(bytes, (byte) 0x00);
		bytes[ 0 ] = (byte) 0xbb;
		bytes[ 1 ] = 0x20;
		return bytes;
	}
	
	/**
	 * 黑校准命令(BlackAdjustCmd)
	 *
	 * @return
	 */
	public static byte[] blackAdjustCmd() {
		byte[] bytes = new byte[ 10 ];
		Arrays.fill(bytes, (byte) 0x00);
		bytes[ 0 ] = (byte) 0xbb;
		bytes[ 1 ] = 0x10;
		bytes[ 2 ] = 0x01;
		bytes[ 8 ] = (byte) 0xff;
		return sumCheck(bytes);
	}
	
	/**
	 * 白校准命令(WhiteAdjustCmd)
	 *
	 * @return
	 */
	public static byte[] whiteAdjustCmd() {
		byte[] bytes = new byte[ 10 ];
		Arrays.fill(bytes, (byte) 0x00);
		bytes[ 0 ] = (byte) 0xbb;
		bytes[ 1 ] = 0x11;
		bytes[ 2 ] = 0x01;
		bytes[ 8 ] = (byte) 0xff;
		return sumCheck(bytes);
	}
	
	public static int roundNumber() {
		return (int) (Math.random() * (Integer.MAX_VALUE));
	}
	
	/**
	 * 测量命令(MeasureCmd)
	 *
	 * @param measureMode
	 * @return
	 */
	public static byte[] measureCmd(int measureMode) {
		byte[] bytes = new byte[ 10 ];
		Arrays.fill(bytes, (byte) 0x00);
		bytes[ 0 ] = (byte) 0xbb;
		bytes[ 1 ] = 0x01;
		bytes[ 2 ] = getMeasureMode(measureMode);
		bytes[ 3 ] = (byte) roundNumber();
		bytes[ 8 ] = (byte) 0xff;
		return sumCheck(bytes);
	}
	
	/**
	 * 读取测量数据命令(ReadMeasureDataCmd)
	 *
	 * @param measureMode
	 * @return
	 */
	public static byte[] readMeasureDataCmd(int measureMode) {
		byte[] bytes = new byte[ 10 ];
		Arrays.fill(bytes, (byte) 0x00);
		bytes[ 0 ] = (byte) 0xbb;
		bytes[ 1 ] = 0x02;
		bytes[ 2 ] = getMeasureMode_New(measureMode);
		
		bytes[ 8 ] = (byte) 0xff;
		
		return sumCheck(bytes);
	}
	
	/**
	 * 读取lab测量数据(ReadLabMeasureDataCmd)
	 *
	 * @param measureMode
	 * @return
	 */
	public static byte[] readLabMeasureDataCmd(int measureMode) {
		byte[] bytes = new byte[ 10 ];
		Arrays.fill(bytes, (byte) 0x00);
		bytes[ 0 ] = (byte) 0xbb;
		bytes[ 1 ] = 0x03;
		bytes[ 2 ] = getMeasureMode(measureMode);
		bytes[ 8 ] = (byte) 0xff;
		
		return sumCheck(bytes);
	}
	
	/**
	 * 读取RGB测量数据(ReadRGBMeasureDataCmd)
	 *
	 * @param measureMode
	 * @return
	 */
	public static byte[] readRGBMeasureDataCmd(int measureMode) {
		byte[] bytes = new byte[ 10 ];
		Arrays.fill(bytes, (byte) 0x00);
		bytes[ 0 ] = (byte) 0xbb;
		bytes[ 1 ] = 0x04;
		bytes[ 2 ] = getMeasureMode(measureMode);
		bytes[ 8 ] = (byte) 0xff;
		
		return sumCheck(bytes);
	}
	
	/**
	 * 获取标样总数命令(GetStandardCountCmd)
	 *
	 * @return
	 */
	public static byte[] getStandardCountCmd() {
		byte[] bytes = new byte[ 10 ];
		Arrays.fill(bytes, (byte) 0x00);
		bytes[ 0 ] = (byte) 0xbb;
		bytes[ 1 ] = 0x16;
		bytes[ 8 ] = (byte) 0xff;
		return sumCheck(bytes);
	}
	
	/**
	 * 获取第N条标样数据命令(Command to get the nth standard sample data)
	 *
	 * @param number 数据的序号(index)
	 * @return
	 */
	public static byte[] getStandardDataForNumberCmd(short number) {
		byte[] bytes = new byte[ 10 ];
		Arrays.fill(bytes, (byte) 0x00);
		bytes[ 0 ] = (byte) 0xbb;
		bytes[ 1 ] = 0x16;
		bytes[ 2 ] = 0x01;
		ByteUtil.putShort(bytes, number, 3, ByteUtil.TYPE_LOW_FIRST);
		bytes[ 8 ] = (byte) 0xff;
		return sumCheck(bytes);
	}
	
	/**
	 * 删除所有标样命令(DeleteAllStandardDataCmd)
	 *
	 * @return
	 */
	public static byte[] deleteAllStandardDataCmd() {
		byte[] bytes = new byte[ 10 ];
		Arrays.fill(bytes, (byte) 0x00);
		bytes[ 0 ] = (byte) 0xbb;
		bytes[ 1 ] = 0x16;
		bytes[ 2 ] = 0x04;
		bytes[ 8 ] = (byte) 0xff;
		return sumCheck(bytes);
	}
	
	/**
	 * 删除第N条标样数据 (Delete the nth standard sample data)
	 *
	 * @param number 数据的序号(index)
	 * @return
	 */
	public static byte[] deleteStandardDataForNumberCmd(short number) {
		byte[] bytes = new byte[ 10 ];
		Arrays.fill(bytes, (byte) 0x00);
		bytes[ 0 ] = (byte) 0xbb;
		bytes[ 1 ] = 0x16;
		bytes[ 2 ] = 0x02;
		ByteUtil.putShort(bytes, number, 3, ByteUtil.TYPE_LOW_FIRST);
		bytes[ 8 ] = (byte) 0xff;
		return sumCheck(bytes);
	}
	
	/**
	 * 获取第N条标样下试样总数
	 *
	 * @param number 数据的序号(index)
	 * @return
	 */
	public static byte[] getSampleCountForStandardNumberCmd(short number) {
		byte[] bytes = new byte[ 10 ];
		Arrays.fill(bytes, (byte) 0x00);
		bytes[ 0 ] = (byte) 0xbb;
		bytes[ 1 ] = 0x16;
		bytes[ 2 ] = 0x05;
		ByteUtil.putShort(bytes, number, 3, ByteUtil.TYPE_LOW_FIRST);
		bytes[ 8 ] = (byte) 0xff;
		return sumCheck(bytes);
	}
	
	/**
	 * 获取第N条标样下第M条试样数据命令(Command to obtain the data of M sample under the nth standard sample)
	 *
	 * @param standardNum 标样的序号(Serial number of standard sample)
	 * @param sampleNum   试样的序号(Serial number of sample)
	 * @return
	 */
	public static byte[] getNumSampleDataForNumStandardCmd(short standardNum, short sampleNum) {
		byte[] bytes = new byte[ 10 ];
		Arrays.fill(bytes, (byte) 0x00);
		bytes[ 0 ] = (byte) 0xbb;
		bytes[ 1 ] = 0x16;
		bytes[ 2 ] = 0x06;
		ByteUtil.putShort(bytes, standardNum, 3, ByteUtil.TYPE_LOW_FIRST);
		ByteUtil.putShort(bytes, sampleNum, 5, ByteUtil.TYPE_LOW_FIRST);
		bytes[ 8 ] = (byte) 0xff;
		return sumCheck(bytes);
		
	}
	
	/**
	 * 删除第N条标样下所有试样命令(Delete all sample orders under N standard sample)
	 *
	 * @param standardNum 标样的序号(Serial number of standard sample)
	 * @return
	 */
	public static byte[] deleteAllSampleForStandardNumCmd(short standardNum) {
		byte[] bytes = new byte[ 10 ];
		Arrays.fill(bytes, (byte) 0x00);
		bytes[ 0 ] = (byte) 0xbb;
		bytes[ 1 ] = 0x16;
		bytes[ 2 ] = 0x07;
		ByteUtil.putShort(bytes, standardNum, 3);
		bytes[ 8 ] = (byte) 0xff;
		return sumCheck(bytes);
	}
	
	/**
	 * 删除第N条标样下第M条试样命令(Delete the M sample command under the nth standard sample)
	 *
	 * @param standardNum 标样的序号(Serial number of standard sample)
	 * @param sampleNum   试样的序号(Serial number of sample)
	 * @return
	 */
	public static byte[] deleteNumSampleDataForNumStandardCmd(short standardNum, short sampleNum) {
		byte[] bytes = new byte[ 10 ];
		Arrays.fill(bytes, (byte) 0x00);
		bytes[ 0 ] = (byte) 0xbb;
		bytes[ 1 ] = 0x16;
		bytes[ 2 ] = 0x08;
		ByteUtil.putShort(bytes, standardNum, 3);
		ByteUtil.putShort(bytes, sampleNum, 5);
		bytes[ 8 ] = (byte) 0xff;
		return sumCheck(bytes);
	}
	
	/**
	 * 上传一条标样到仪器命令(Upload a standard sample to the instrument command)
	 *
	 * @param t   标样数据实体类(Standard  data entity class)
	 * @param <T>
	 * @return
	 */
	public static <T extends StandardSampleDataBean.StandardDataBean> byte[] postStandardDataCmd(T t) {
		byte[] bytes = new byte[ 250 ];
		byte[] bytes1 = toStandardByte(t);
		Arrays.fill(bytes, (byte) 0x00);
		bytes[ 0 ] = (byte) 0xbb;
		bytes[ 1 ] = 0x16;
		bytes[ 2 ] = 0x0a;
		System.arraycopy(bytes1, 0, bytes, 5, 217);
		bytes[ 248 ] = (byte) 0xff;
		return sumCheck(bytes);
	}
	
	private static byte[] toStandardByte(StandardSampleDataBean.StandardDataBean bean) {
		byte[] bytes = new byte[ 256 ];
		bytes[ 0 ] = bean.getState();
		bytes[ 1 ] = bean.getLightSource();
		bytes[ 2 ] = bean.getMeasureMode();
		bytes[ 3 ] = bean.getDataMode();
		bytes[ 4 ] = (byte) (bean.getStartWave() / 10);
		bytes[ 5 ] = bean.getWaveLength();
		bytes[ 6 ] = bean.getWaveCount();
		byte[] nameBytes = bean.getName().getBytes();
		
		if(nameBytes.length > 18) {
			System.arraycopy(nameBytes, 0, bytes, 7, 18);
		}
		else {
			System.arraycopy(nameBytes, 0, bytes, 7, nameBytes.length);
		}
		
		ByteUtil.putFloat(bytes, bean.getL(), 28);
		ByteUtil.putFloat(bytes, bean.getA(), 32);
		ByteUtil.putFloat(bytes, bean.getB(), 36);
		for(int i = 0; i < bean.getDataSCI().size(); i++) {
			ByteUtil.putShort(bytes, (short) (bean.getDataSCI().get(i) * 100), 40 + i * 2, ByteUtil.TYPE_LOW_FIRST);
		}
		for(int i = 0; i < bean.getDataSCE().size(); i++) {
			ByteUtil.putShort(bytes, (short) (bean.getDataSCI().get(i) * 100), 126 + i * 2, ByteUtil.TYPE_LOW_FIRST);
		}
		ByteUtil.putInt(bytes, bean.getRecordCount(), 212, ByteUtil.TYPE_LOW_FIRST);
		ByteUtil.putInt(bytes, bean.getTime(), 216, ByteUtil.TYPE_LOW_FIRST);
		return bytes;
	}
	
	/**
	 * 获取仪器信息命令(GetDeviceInfoCmd)
	 *
	 * @return
	 */
	public static byte[] getDeviceInfoCmd() {
		byte[] bytes = new byte[ 10 ];
		Arrays.fill(bytes, (byte) 0x00);
		bytes[ 0 ] = (byte) 0xbb;
		bytes[ 1 ] = 0x12;
		bytes[ 2 ] = 0x01;
		bytes[ 8 ] = (byte) 0xff;
		return sumCheck(bytes);
	}
	
	
	/**
	 * 读取电量信息命令(GetDevicePowerInfoCmd)
	 *
	 * @return
	 */
	public static byte[] getDevicePowerInfoCmd() {
		byte[] bytes = new byte[ 10 ];
		Arrays.fill(bytes, (byte) 0x00);
		bytes[ 0 ] = (byte) 0xbb;
		bytes[ 1 ] = 0x1D;
		bytes[ 8 ] = (byte) 0xff;
		return sumCheck(bytes);
	}
	
	/**
	 * 获取仪器校准状态命令(GetDeviceAdjustStateCmd)
	 *
	 * @return
	 */
	public static byte[] getDeviceAdjustStateCmd() {
		byte[] bytes = new byte[ 10 ];
		Arrays.fill(bytes, (byte) 0x00);
		bytes[ 0 ] = (byte) 0xbb;
		bytes[ 1 ] = 0x1E;
		bytes[ 8 ] = (byte) 0xff;
		return sumCheck(bytes);
	}
	
	/**
	 * 设置仪器显示参数命令(Set instrument display parameter command)
	 *
	 * @param lightSource     光源(LightSource)
	 * @param angle           角度(Angle)
	 * @param measureMode     测量模式(MeasureMode)
	 * @param colorSpace      颜色空间(ColorSpace)
	 * @param colorDifference 色差公式(Colour-difference formula)
	 * @return
	 */
	public static byte[] setDeviceDisplayParamCmd(int lightSource, int angle,
			int measureMode, int colorSpace,
			int colorDifference) {
		byte[] bytes = new byte[ 10 ];
		Arrays.fill(bytes, (byte) 0x00);
		bytes[ 0 ] = (byte) 0xbb;
		bytes[ 1 ] = 0x1A;
		bytes[ 2 ] = getLightSource(lightSource);
		bytes[ 3 ] = getAngle(angle);
		bytes[ 4 ] = getMeasureMode(measureMode);
		bytes[ 5 ] = getColorSpace(colorSpace);
		bytes[ 6 ] = getColorDifference(colorDifference);
		bytes[ 8 ] = (byte) 0xff;
		return sumCheck(bytes);
	}
	
	/**
	 * 设置仪器显示参数命令(Set instrument display parameter command
	 */
	public static byte[] setDeviceDisplayParamCmd(DisplayParam displayParam) {
		byte[] bytes = new byte[ 10 ];
		Arrays.fill(bytes, (byte) 0x00);
		bytes[ 0 ] = (byte) 0xbb;
		bytes[ 1 ] = 0x1A;
		bytes[ 2 ] = getLightSource(displayParam.getFirstLightSource());
		bytes[ 3 ] = getAngle(displayParam.getFirstAngle());
		bytes[ 4 ] = getMeasureMode(displayParam.getMeasureMode());
		bytes[ 5 ] = getColorSpace(displayParam.getColorSpace());
		bytes[ 6 ] = getColorDifference(displayParam.getColorDiff());
		bytes[ 8 ] = (byte) 0xff;
		return sumCheck(bytes);
	}
	
	/**
	 * 设置仪器显示参数命令 (Set instrument display parameter command)
	 *
	 * @param lightSource       光源(LightSource)
	 * @param angle             角度(Angle)
	 * @param measureMode       测量模式(MeasureMode)
	 * @param colorSpace        颜色空间(ColorSpace)
	 * @param colorDifference   色差公式(Colour-difference formula)
	 * @param secondAngle       第二角度 （用于计算同色异谱） Second angle (used to calculate metamerism)
	 * @param secondLightSource 第二光源（用于计算同色异谱） Second light source (for calculating metamerism)
	 * @return
	 */
	public static byte[] setDeviceDisplayParamCmd(int lightSource, int angle,
			int measureMode, int colorSpace,
			int colorDifference, int secondLightSource, int secondAngle) {
		byte[] bytes = new byte[ 10 ];
		Arrays.fill(bytes, (byte) 0x00);
		bytes[ 0 ] = (byte) 0xbb;
		bytes[ 1 ] = 0x1A;
		bytes[ 2 ] = getLightSource(lightSource);
		bytes[ 3 ] = getAngle(angle);
		bytes[ 4 ] = getMeasureMode(measureMode);
		bytes[ 5 ] = getColorSpace(colorSpace);
		bytes[ 6 ] = getColorDifference(colorDifference);
		bytes[ 7 ] = (byte) (getSecondAngle(secondAngle) + getLightSource(secondLightSource));
		bytes[ 8 ] = (byte) 0xff;
		return sumCheck(bytes);
	}
	
	/**
	 * 容差设置命令(Tolerance setting command)
	 *
	 * @param toleranceBean 容差实体类(Tolerance entity class)
	 * @return
	 */
	public static byte[] setToleranceCmd(ToleranceBean toleranceBean) {
		byte[] bytes = new byte[ 100 ];
		Arrays.fill(bytes, (byte) 0x00);
		bytes[ 0 ] = (byte) 0xbb;
		bytes[ 1 ] = 0x1B;
		bytes[ 2 ] = 0x00;
		ByteUtil.putFloat(bytes, toleranceBean.getL(), 6);
		ByteUtil.putFloat(bytes, toleranceBean.getA(), 10);
		ByteUtil.putFloat(bytes, toleranceBean.getB(), 14);
		ByteUtil.putFloat(bytes, toleranceBean.getC(), 18);
		ByteUtil.putFloat(bytes, toleranceBean.getH(), 22);
		ByteUtil.putFloat(bytes, toleranceBean.getdEab(), 26);
		ByteUtil.putFloat(bytes, toleranceBean.getdEch(), 30);
		ByteUtil.putFloat(bytes, toleranceBean.getdEcmc(), 34);
		ByteUtil.putFloat(bytes, toleranceBean.getdE94(), 38);
		ByteUtil.putFloat(bytes, toleranceBean.getDe00(), 42);
		bytes[ 98 ] = (byte) 0xff;
		return sumCheck(bytes);
	}
	
	
	/**
	 * 设置电源管理时间命令(Set power management time command)
	 *
	 * @param autoOffTime   关机时间(Shutdown time)
	 * @param backLightTime 背光时间(backlight time)
	 * @return
	 */
	public static byte[] setPowerManagementTimeCmd(int autoOffTime, int backLightTime) {
		byte[] bytes = new byte[ 10 ];
		Arrays.fill(bytes, (byte) 0x00);
		bytes[ 0 ] = (byte) 0xbb;
		bytes[ 1 ] = 0x17;
		bytes[ 2 ] = getAutoOffTime(autoOffTime);
		bytes[ 3 ] = getBackLightTime(backLightTime);
		bytes[ 8 ] = (byte) 0xff;
		return sumCheck(bytes);
	}
	
	
	/**
	 * 设置时间命令(Set time command)
	 *
	 * @param time 时间(Time)
	 * @return
	 */
	public static byte[] setDeviceTimeCmd(int time) {
		byte[] bytes = new byte[ 10 ];
		Arrays.fill(bytes, (byte) 0x00);
		bytes[ 0 ] = (byte) 0xbb;
		bytes[ 1 ] = 0x14;
		ByteUtil.putInt(bytes, time, 2, ByteUtil.TYPE_LOW_FIRST);
		bytes[ 8 ] = (byte) 0xff;
		return sumCheck(bytes);
	}
	
	/**
	 * 设置保存模式命令(Set save mode command)
	 *
	 * @param saveMode 保存模式(SaveMode)
	 * @return
	 */
	public static byte[] setSaveModeCmd(int saveMode) {
		byte[] bytes = new byte[ 10 ];
		Arrays.fill(bytes, (byte) 0x00);
		bytes[ 0 ] = (byte) 0xbb;
		bytes[ 1 ] = 0x1C;
		bytes[ 2 ] = getSaveMode(saveMode);
		bytes[ 8 ] = (byte) 0xff;
		return sumCheck(bytes);
	}
	
	public static byte[] enterDfuMode() {
		byte[] bytes = new byte[ 10 ];
		Arrays.fill(bytes, (byte) 0x00);
		bytes[ 0 ] = (byte) 0xbb;
		bytes[ 1 ] = 0x20;
		bytes[ 8 ] = (byte) 0xff;
		return sumCheck(bytes);
	}
	
	/**
	 * 设置蓝牙名称 字符长度不能超过15个字节 (Set the length of Bluetooth name character cannot exceed 15 bytes)
	 *
	 * @param bluetoothName 蓝牙名称(BluetoothName)
	 * @return
	 */
	public static byte[] setBluetoothName(String bluetoothName) {
		byte[] bytes = new byte[ 20 ];
		Arrays.fill(bytes, (byte) 0x00);
		bytes[ 0 ] = (byte) 0xbb;
		bytes[ 1 ] = 0x1F;
		if(bluetoothName.getBytes().length < 15) {
			System.arraycopy(bluetoothName.getBytes(), 0, bytes, 3, bluetoothName.getBytes().length);
		}
		else {
			System.arraycopy(bluetoothName.getBytes(), 0, bytes, 3, 15);
		}
		bytes[ 18 ] = (byte) 0xff;
		return sumCheck(bytes);
	}
	
	/**
	 * 判断测量模式(Judge the measurement mode)
	 *
	 * @param measureMode
	 * @return
	 */
	private static byte getMeasureMode(int measureMode) {
		if(measureMode == Constant.TYPE_MEASURE_MODE_SCI) {
			return 0x00;
		}
		else if(measureMode == Constant.TYPE_MEASURE_MODE_SCE) {
			return 0x01;
		}
		else if(measureMode == Constant.TYPE_MEASURE_MODE_SCI_SCE) {
			return 0x02;
		}
		else {
			return 0x00;
		}
	}
	
	/**
	 * 判断测量模式(Judge the measurement mode)
	 *
	 * @param measureMode
	 * @return
	 */
	private static byte getMeasureMode_New(int measureMode) {
		if(measureMode == Constant.TYPE_MEASURE_MODE_SCI || measureMode == Constant.TYPE_MEASURE_MODE_SCI_NEW) {
			return 0x10;
		}
		else if(measureMode == Constant.TYPE_MEASURE_MODE_SCE || measureMode == Constant.TYPE_MEASURE_MODE_SCE_NEW) {
			return 0x11;
		}
		else {
			return 0x00;
		}
	}
	
	private static byte getDataMode(int dataMode) {
		if(dataMode == Constant.TYPE_DATA_MODE_REFLECTIVITY) {
			return 0x00;
		}
		else if(dataMode == Constant.TYPE_DATA_MODE_LAB) {
			return 0x01;
		}
		else {
			return 0x00;
		}
		
	}
	
	/**
	 * 判断光源(Judge the light source)
	 *
	 * @param lightSource
	 * @return
	 */
	private static byte getLightSource(int lightSource) {
		byte b = 0x00;
		switch(lightSource) {
			case Constant.TYPE_LIGHT_SOURCE_A:
				b = 0x00;
				break;
			case Constant.TYPE_LIGHT_SOURCE_C:
				b = 0x01;
				break;
			case Constant.TYPE_LIGHT_SOURCE_D50:
				b = 0x02;
				break;
			case Constant.TYPE_LIGHT_SOURCE_D55:
				b = 0x03;
				break;
			case Constant.TYPE_LIGHT_SOURCE_D65:
				b = 0x04;
				break;
			case Constant.TYPE_LIGHT_SOURCE_D75:
				b = 0x05;
				break;
			case Constant.TYPE_LIGHT_SOURCE_F1:
				b = 0x06;
				break;
			case Constant.TYPE_LIGHT_SOURCE_F2:
				b = 0x07;
				break;
			case Constant.TYPE_LIGHT_SOURCE_F3:
				b = 0x08;
				break;
			case Constant.TYPE_LIGHT_SOURCE_F4:
				b = 0x09;
				break;
			case Constant.TYPE_LIGHT_SOURCE_F5:
				b = 0x0A;
				break;
			case Constant.TYPE_LIGHT_SOURCE_F6:
				b = 0x0B;
				break;
			case Constant.TYPE_LIGHT_SOURCE_F7:
				b = 0x0C;
				break;
			case Constant.TYPE_LIGHT_SOURCE_F8:
				b = 0x0D;
				break;
			case Constant.TYPE_LIGHT_SOURCE_F9:
				b = 0x0E;
				break;
			case Constant.TYPE_LIGHT_SOURCE_F10:
				b = 0x0F;
				break;
			case Constant.TYPE_LIGHT_SOURCE_F11:
				b = 0x10;
				break;
			case Constant.TYPE_LIGHT_SOURCE_F12:
				b = 0x11;
				break;
			case Constant.TYPE_LIGHT_SOURCE_CWF:
				b = 0x12;
				break;
			case Constant.TYPE_LIGHT_SOURCE_U30:
				b = 0x13;
				break;
			case Constant.TYPE_LIGHT_SOURCE_DLF:
				b = 0x14;
				break;
			case Constant.TYPE_LIGHT_SOURCE_NBF:
				b = 0x15;
				break;
			case Constant.TYPE_LIGHT_SOURCE_TL83:
				b = 0x16;
				break;
			case Constant.TYPE_LIGHT_SOURCE_TL84:
				b = 0x17;
				break;
			case Constant.TYPE_LIGHT_SOURCE_U35:
				b = 0x18;
				break;
			case Constant.TYPE_LIGHT_SOURCE_B:
				b = 0x19;
				break;
			
		}
		return b;
	}
	
	/**
	 * 判断角度(Judgment angle)
	 *
	 * @param angle
	 * @return
	 */
	private static byte getAngle(int angle) {
		if(angle == Constant.TYPE_ANGLE_2) {
			return 0x00;
		}
		else if(angle == Constant.TYPE_ANGLE_10) {
			return 0x01;
		}
		else {
			return 0x00;
		}
	}
	
	private static byte getSecondAngle(int angle) {
		if(angle == Constant.TYPE_ANGLE_2) {
			return 0x00;
		}
		else if(angle == Constant.TYPE_ANGLE_10) {
			return (byte) 0x80;
		}
		else {
			return 0x00;
		}
	}
	
	/**
	 * 判断颜色空间(Judge color space)
	 *
	 * @param colorSpace
	 * @return
	 */
	private static byte getColorSpace(int colorSpace) {
		byte b = 0x00;
		switch(colorSpace) {
			case Constant.TYPE_COLOR_SPACE_LAB_CIE:
				b = 0x00;
				break;
			case Constant.TYPE_COLOR_SPACE_LCH_CIE:
				b = 0x01;
				break;
			case Constant.TYPE_COLOR_SPACE_LAB_HUNTER:
				b = 0x02;
				break;
			case Constant.TYPE_COLOR_SPACE_LUV:
				b = 0x03;
				break;
			case Constant.TYPE_COLOR_SPACE_XYZ:
				b = 0x04;
				break;
			case Constant.TYPE_COLOR_SPACE_YXY:
				b = 0x05;
				break;
			case Constant.TYPE_COLOR_SPACE_RGB:
				b = 0x06;
				break;
			case Constant.TYPE_COLOR_SPACE_TSYP:
				b = 0x07;
				break;
			case Constant.TYPE_COLOR_SPACE_REFLECTIVITY:
				b = 0x08;
				break;
			case Constant.TYPE_COLOR_SPACE_COVERAGE_RATE:
				b = 0x09;
				break;
			case Constant.TYPE_COLOR_SPACE_DENSITY_A:
				b = 0x0A;
				break;
			case Constant.TYPE_COLOR_SPACE_DENSITY_T:
				b = 0x0B;
				break;
			case Constant.TYPE_COLOR_SPACE_DENSITY_E:
				b = 0x0C;
				break;
			case Constant.TYPE_COLOR_SPACE_DENSITY_M:
				b = 0x0D;
				break;
			case Constant.TYPE_COLOR_SPACE_MUNSELL:
				b = 0x0E;
				break;
			case Constant.TYPE_COLOR_SPACE_TINT:
				b = 0x0F;
				break;
			case Constant.TYPE_COLOR_SPACE_WHITENESS:
				b = 0x10;
				break;
			case Constant.TYPE_COLOR_SPACE_YELLOWNESS:
				b = 0x11;
				break;
			case Constant.TYPE_COLOR_SPACE_BLACKNESS:
				b = 0x12;
				break;
			case Constant.TYPE_COLOR_SPACE_COLORING_POWER:
				b = 0x13;
				break;
			case Constant.TYPE_COLOR_SPACE_COLOR_FASTNESS:
				b = 0x14;
				break;
		}
		return b;
	}
	
	/**
	 * 判断色差公式(Color difference formula)
	 *
	 * @param colorDifference
	 * @return
	 */
	private static byte getColorDifference(int colorDifference) {
		byte b = 0x00;
		switch(colorDifference) {
			case Constant.TYPE_COLOR_DIFFERENCE_DE_AB:
				b = 0x00;
				break;
			case Constant.TYPE_COLOR_DIFFERENCE_DE_CH:
				b = 0x01;
				break;
			case Constant.TYPE_COLOR_DIFFERENCE_DE_00:
				b = 0x02;
				break;
			case Constant.TYPE_COLOR_DIFFERENCE_DE_CMC:
				b = 0x03;
				break;
			case Constant.TYPE_COLOR_DIFFERENCE_DE_94:
				b = 0x04;
				break;
			case Constant.TYPE_COLOR_DIFFERENCE_DE_AB_HUNTER:
				b = 0x05;
				break;
			case Constant.TYPE_COLOR_DIFFERENCE_DE_UV:
				b = 0x06;
				break;
			
		}
		return b;
	}
	
	/**
	 * 判断蓝牙开关(Judge Bluetooth openOrClose)
	 *
	 * @param openOrClose
	 * @return
	 */
	private static byte getBluetooth(int openOrClose) {
		byte b = 0x00;
		switch(openOrClose) {
			case Constant.TYPE_BLUETOOTH_OPEN:
				b = 0x00;
				break;
			case Constant.TYPE_BLUETOOTH_CLOSE:
				b = 0x01;
				break;
		}
		return b;
	}
	
	/**
	 * 判断自动关机时间(Judge automatic shutdown time)
	 *
	 * @param autoOffTime
	 * @return
	 */
	private static byte getAutoOffTime(int autoOffTime) {
		if(autoOffTime == Constant.TYPE_AUTO_OFF_TIME_10_MIN) {
			return 0x01;
		}
		else if(autoOffTime == Constant.TYPE_AUTO_OFF_TIME_20_MIN) {
			return 0x02;
		}
		return 0x01;
	}
	
	/**
	 * 判断背光时间(Judge backlight time)
	 *
	 * @param backLightTime
	 * @return
	 */
	private static byte getBackLightTime(int backLightTime) {
		if(backLightTime == Constant.TYPE_BACKLIGHT_TIME_1_SECOND) {
			return 0x01;
		}
		else if(backLightTime == Constant.TYPE_BACKLIGHT_TIME_2_SECOND) {
			return 0x02;
		}
		return 0x01;
	}
	
	/**
	 * 判断保存模式(Determine the save mode)
	 *
	 * @param saveMode
	 * @return
	 */
	private static byte getSaveMode(int saveMode) {
		if(saveMode == Constant.TYPE_SAVE_MODE_AUTO) {
			return 0x00;
		}
		else if(saveMode == Constant.TYPE_SAVE_MODE_MANUAL) {
			return 0x01;
		}
		return 0x01;
	}
	
	/**
	 * 和校验(SumCheck)
	 *
	 * @param bytes
	 * @return
	 */
	private static byte[] sumCheck(byte[] bytes) {
		for(int i = 0; i < bytes.length - 1; i++) {
			bytes[ bytes.length - 1 ] += bytes[ i ];
		}
		return bytes;
	}
	
	public static String judgeCmdType(byte[] bytes) {
		if(bytes[ 0 ] == (byte) 0xbb) {
			if(bytes[ 1 ] == 0x10) {
				return Constant.BLACK_ADJUST;
			}
			else if(bytes[ 1 ] == 0x11) {
				return Constant.WHITE_ADJUST;
			}
			else if(bytes[ 1 ] == 0x01) {
				return Constant.MEASURE;
			}
			else if(bytes[ 1 ] == 0x02) {
				if(bytes[ 2 ] == 0x01 || bytes[ 2 ] == 0x00) {
					return Constant.READ_MEASURE_DATA;
				}
				else if(bytes[ 2 ] == 0x10 || bytes[ 2 ] == 0x11) {
					return Constant.READ_MEASURE_DATA;
				}
			}
			else if(bytes[ 1 ] == 0x03) {
				return Constant.READ_LAB_MEASURE_DATA;
			}
			else if(bytes[ 1 ] == 0x04) {
				return Constant.READ_RGB_MEASURE_DATA;
			}
			else if(bytes[ 1 ] == 0x12) {
				if(bytes[ 2 ] == 0x00) {
					return Constant.GET_DEVICE_INFO;
				}
				else if(bytes[ 2 ] == 0x01) {
					return Constant.GET_DEVICE_INFO;
				}
			}
			else if(bytes[ 1 ] == 0x16) {
				if(bytes[ 2 ] == 0x00) {
					return Constant.GET_STANDARD_DATA_COUNT;
				}
				else if(bytes[ 2 ] == 0x01) {
					return Constant.GET_STANDARD_DATA_FOR_NUM;
				}
				else if(bytes[ 2 ] == 0x04) {
					return Constant.DELETE_ALL_STANDARD_DATA;
				}
				else if(bytes[ 2 ] == 0x02) {
					return Constant.DELETE_STANDARD_DATA_FOR_NUM;
				}
				else if(bytes[ 2 ] == 0x05) {
					return Constant.GET_SAMPLE_COUNT_FOR_STANDARD_NUM;
				}
				else if(bytes[ 2 ] == 0x06) {
					return Constant.GET_NUM_SAMPLE_DATA_FOR_NUM_STANDARD;
				}
				else if(bytes[ 2 ] == 0x07) {
					return Constant.DELETE_ALL_SAMPLE_FOR_STANDARD_NUM;
				}
				else if(bytes[ 2 ] == 0x08) {
					return Constant.DELETE_NUM_SAMPLE_DATA_FOR_NUM_STANDARD;
				}
				else if(bytes[ 2 ] == 0x0a) {
					return Constant.POST_STANDARD_DATA;
				}
			}
			else if(bytes[ 1 ] == 0x17) {
				return Constant.SET_POWER_MANAGEMENT_TIME;
			}
			else if(bytes[ 1 ] == 0x18) {
				return Constant.SET_BLUETOOTH;
			}
			else if(bytes[ 1 ] == 0x1A) {
				return Constant.SET_DEVICE_DISPLAY_PARAM;
			}
			else if(bytes[ 1 ] == 0x14) {
				return Constant.SET_DEVICE_TIME;
			}
			else if(bytes[ 1 ] == 0x1B) {
				return Constant.SET_TOLERANCE;
			}
			else if(bytes[ 1 ] == 0x1C) {
				return Constant.SET_SAVE_MODE;
			}
			else if(bytes[ 1 ] == 0x1D) {
				return Constant.GET_DEVICE_POWER_INFO;
			}
			else if(bytes[ 1 ] == 0x1E) {
				return Constant.GET_DEVICE_ADJUST_STATE;
			}
			else if(bytes[ 1 ] == 0x1F) {
				return Constant.SET_BLUETOOTH_NAME;
			}
		}
		return "judge fail";
	}
	
}
