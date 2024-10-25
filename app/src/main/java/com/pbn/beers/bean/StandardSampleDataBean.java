package com.pbn.beers.bean;

import androidx.annotation.NonNull;

import com.pbn.beers.utils.ByteUtil;
import com.pbn.beers.utils.TimeUtil;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class StandardSampleDataBean implements Serializable
{
	public static class SampleDataBean implements Serializable
	{
		private short standardNum;
		private short sampleNum;
		private byte state; // 0-null 1-used 2-deleted 3-protect
		private byte lightSource;// lightSource
		private byte angle;// angle
		private byte measureMode;// measureMode
		private byte dataMode;// dataMode
		private short startWave;// 300-300nm 400-400nm
		private byte waveLength;// Wavelength interval 10-10nm
		private byte waveCount;//31 represents 31 wavelengths
		private String name;// name
		private float L;
		private float a;
		private float b;
		private int time;
		private List<Float> dataSCI;// SCI spectral data
		private List<Float> dataSCE;// SCE spectral data
		
		public short getStandardNum() {
			return standardNum;
		}
		
		public void setStandardNum(short standardNum) {
			this.standardNum = standardNum;
		}
		
		
		public short getSampleNum() {
			return sampleNum;
		}
		
		
		public void setSampleNum(short sampleNum) {
			this.sampleNum = sampleNum;
		}
		
		/**
		 * @return 0-null 1-used 2-deleted 3-protect
		 */
		public int getState() {
			return state;
		}
		
		/**
		 * @param state 0-null 1-used 2-deleted 3-protect
		 */
		public void setState(byte state) {
			this.state = state;
		}
		
		
		public int getLightSource() {
			return lightSource;
		}
		
		
		public void setLightSource(byte lightSource) {
			this.lightSource = lightSource;
		}
		
		
		public byte getAngle() {
			return angle;
		}
		
		
		public void setAngle(byte angle) {
			this.angle = angle;
		}
		
		
		public int getMeasureMode() {
			return measureMode;
		}
		
		
		public void setMeasureMode(byte measureMode) {
			this.measureMode = measureMode;
		}
		
		public byte getDataMode() {
			return dataMode;
		}
		
		
		public void setDataMode(byte dataMode) {
			this.dataMode = dataMode;
		}
		
		public int getStartWave() {
			return startWave;
		}
		
		
		public void setStartWave(short startWave) {
			this.startWave = startWave;
		}
		
		
		public int getWaveLength() {
			return waveLength;
		}
		
		
		public void setWaveLength(byte waveLength) {
			this.waveLength = waveLength;
		}
		
		
		public int getWaveCount() {
			return waveCount;
		}
		
		
		public void setWaveCount(byte waveCount) {
			this.waveCount = waveCount;
		}
		
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public float getL() {
			return L;
		}
		
		
		public void setL(float L) {
			this.L = L;
		}
		
		
		public float getA() {
			return a;
		}
		
		
		public void setA(float a) {
			this.a = a;
		}
		
		
		public float getB() {
			return b;
		}
		
		
		public void setB(float b) {
			this.b = b;
		}
		
		
		public int getTime() {
			return time;
		}
		
		
		public void setTime(int time) {
			this.time = time;
		}
		
		public List<Float> getDataSCI() {
			return dataSCI;
		}
		
		
		public void setDataSCI(List<Float> dataSCI) {
			this.dataSCI = dataSCI;
		}
		
		
		public List<Float> getDataSCE() {
			return dataSCE;
		}
		
		
		public void setDataSCE(List<Float> dataSCE) {
			this.dataSCE = dataSCE;
		}
		
		@NonNull
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("序号：")// Serial number
					.append(standardNum)
					.append("\n")
					.append("data：")
					.append(judgeDataUseful(state))
					.append("\n")
					.append("角度：")// angle
					.append(judgeAngle(angle))
					.append("\n")
					.append("光源：")// lightSource
					.append(judgeLightSource(lightSource))
					.append("\n")
					.append("数据模式：")//dataMode
					.append(judgeDataMode(dataMode))
					.append("\n")
					.append("测量模式：")//measureMode
					.append(judgeMeasureMode(measureMode))
					.append("\n")
					.append("开始波长：")//Starting wavelength
					.append(startWave)
					.append("\n")
					.append("波长间隔：")// Wavelength interval
					.append(waveLength)
					.append("\n")
					.append("波长个数：")//Number of wavelengths
					.append(waveCount)
					.append("\n")
					.append("名称：")//name
					.append(name)
					.append("\n")
					.append("L：")
					.append(L)
					.append("\n")
					.append("a：")
					.append(a)
					.append("\n")
					.append("b：")
					.append(b)
					.append("\n")
					.append("SCI：")
					.append(ByteUtil.printFloatArrays(dataSCI))
					.append("\n")
					.append("SCE：")
					.append(ByteUtil.printFloatArrays(dataSCE))
					.append("\n")
					.append("时间：")
					.append(TimeUtil.unixTimestamp2Date(time));

			return sb.toString();
		}
		
		/**
		 * 判断数据模式 judgeDataMode
		 *
		 * @param dataMode
		 * @return
		 */
		public String judgeDataMode(byte dataMode) {
			String str;
			if(dataMode == 0x00) {
				str = "反射率";// reflectivity
			}
			else if(dataMode == 0x01) {
				str = "lab";
			}
			else {
				str = "解析错误";// Parsing error
			}
			return str;
		}
		
		/**
		 * 判断测量模式 judgeMeasureMode
		 *
		 * @param measureMode
		 * @return
		 */
		public String judgeMeasureMode(byte measureMode) {
			String str;
			if(measureMode == 0x00) {
				str = "SCI";
			}
			else if(measureMode == 0x01) {
				str = "SCE";
			}
			else if(measureMode == 0x02) {
				str = "SCI+SCE";
			}
			else {
				str = "解析错误";// Parsing error
			}
			return str;
		}
		
		
		/**
		 * 判断数据有效性 Judging data validity
		 *
		 * @param state
		 * @return
		 */
		public String judgeDataUseful(byte state) {
			String str;
			byte b = ByteUtil.getLow4Bit(state);
			if(b == 0x00) {
				str = "null";
			}
			else if(b == 0x01) {
				str = "used";
			}
			else if(b == 0x02) {
				str = "deleted";
			}
			else if(b == 0x03) {
				str = "protect";
			}
			else {
				str = "解析错误";// Parsing error
			}
			return str;
		}
		
		/**
		 * 判断角度 judgeAngle
		 *
		 * @param state
		 * @return
		 */
		public String judgeAngle(byte state) {
			String str;
			byte b = ByteUtil.get8Bit(state);
			if(b == 0x00) {
				str = "2°";
			}
			else if(b == 0x01) {
				str = "10°";
			}
			else {
				str = "解析错误";// Parsing error
			}
			return str;
		}
		
		/**
		 * 判断光源 judgeLightSource
		 *
		 * @param bits
		 * @return
		 */
		public String judgeLightSource(byte bits) {
			String str;
			byte b = ByteUtil.getBit(bits, 7);
			if(b == 0x00) {
				str = "A";
			}
			else if(b == 0x01) {
				str = "C";
			}
			else if(b == 0x02) {
				str = "D50";
			}
			else if(b == 0x03) {
				str = "D55";
			}
			else if(b == 0x04) {
				str = "D65";
			}
			else if(b == 0x05) {
				str = "D75";
			}
			else if(b == 0x06) {
				str = "F1";
			}
			else if(b == 0x07) {
				str = "F2";
			}
			else if(b == 0x08) {
				str = "F3";
			}
			else if(b == 0x09) {
				str = "F4";
			}
			else if(b == 0x0a) {
				str = "F5";
			}
			else if(b == 0x0b) {
				str = "F6";
			}
			else if(b == 0x0c) {
				str = "F7";
			}
			else if(b == 0x0d) {
				str = "F8";
			}
			else if(b == 0x0e) {
				str = "F9";
			}
			else if(b == 0x0f) {
				str = "F10";
			}
			else if(b == 0x10) {
				str = "F11";
			}
			else if(b == 0x11) {
				str = "F12";
			}
			else if(b == 0x12) {
				str = "CWF";
			}
			else if(b == 0x13) {
				str = "U30";
			}
			else if(b == 0x14) {
				str = "DLF";
			}
			else if(b == 0x15) {
				str = "NBF";
			}
			else if(b == 0x16) {
				str = "TL83";
			}
			else if(b == 0x17) {
				str = "TL84";
			}
			else if(b == 0x18) {
				str = "U35";
			}
			else if(b == 0x19) {
				str = "B";
			}
			else {
				str = "解析错误";// Parsing error
			}
			return str;
		}
	}
	
	public static class StandardDataBean implements Serializable
	{
		private short standardNum;// Serial number of standard sample
		private byte state;// 0-null 1-used 2-deleted 3-protect
		private byte lightSource;
		private byte angle;
		private byte measureMode;
		private byte dataMode;
		private short startWave;// Start wavelength 300-300nm 400-400nm
		private byte waveLength;// Wavelength interval 10-10nm
		private byte waveCount;//Number of wavelengths
		private String name;
		private float L;
		private float a;
		private float b;
		private int time;
		private List<Float> dataSCI;// SCI reflectivity
		private List<Float> dataSCE;// SCE reflectivity
		private int recordCount;// sample data count
		
		
		public short getStandardNum() {
			return standardNum;
		}
		
		
		public void setStandardNum(short standardNum) {
			this.standardNum = standardNum;
		}
		
		
		public byte getState() {
			return state;
		}
		
		
		public void setState(byte state) {
			this.state = state;
		}
		
		
		public byte getLightSource() {
			return lightSource;
		}
		
		
		public void setLightSource(byte lightSource) {
			this.lightSource = lightSource;
		}
		
		
		public byte getAngle() {
			return angle;
		}
		
		
		public void setAngle(byte angle) {
			this.angle = angle;
		}
		
		
		public byte getMeasureMode() {
			return measureMode;
		}
		
		
		public void setMeasureMode(byte measureMode) {
			this.measureMode = measureMode;
		}
		
		
		public byte getDataMode() {
			return dataMode;
		}
		
		
		public void setDataMode(byte dataMode) {
			this.dataMode = dataMode;
		}
		
		
		public short getStartWave() {
			return startWave;
		}
		
		
		public void setStartWave(short startWave) {
			this.startWave = startWave;
		}
		
		
		public byte getWaveLength() {
			return waveLength;
		}
		
		
		public void setWaveLength(byte waveLength) {
			this.waveLength = waveLength;
		}
		
		
		public byte getWaveCount() {
			return waveCount;
		}
		
		
		public void setWaveCount(byte waveCount) {
			this.waveCount = waveCount;
		}
		
		
		public String getName() {
			return name;
		}
		
		
		public void setName(String name) {
			this.name = name;
		}
		
		
		public float getL() {
			return L;
		}
		
		
		public void setL(float l) {
			L = l;
		}
		
		
		public float getA() {
			return a;
		}
		
		
		public void setA(float a) {
			this.a = a;
		}
		
		
		public float getB() {
			return b;
		}
		
		
		public void setB(float b) {
			this.b = b;
		}
		
		
		public int getTime() {
			return time;
		}
		
		public void setTime(int time) {
			this.time = time;
		}
		
		public List<Float> getDataSCI() {
			return dataSCI;
		}
		
		
		public void setDataSCI(List<Float> dataSCI) {
			this.dataSCI = dataSCI;
		}
		
		
		public List<Float> getDataSCE() {
			return dataSCE;
		}
		
		
		public void setDataSCE(List<Float> dataSCE) {
			this.dataSCE = dataSCE;
		}
		
		
		public int getRecordCount() {
			return recordCount;
		}
		
		
		public void setRecordCount(int recordCount) {
			this.recordCount = recordCount;
		}
		
		public byte[] toStandardByte() {
			byte[] bytes = new byte[ 256 ];
			Arrays.fill(bytes, (byte) 0x00);
			bytes[ 0 ] = state;
			bytes[ 1 ] = lightSource;
			bytes[ 2 ] = measureMode;
			bytes[ 3 ] = dataMode;
			bytes[ 4 ] = (byte) (startWave / 10);
			bytes[ 5 ] = waveLength;
			bytes[ 6 ] = waveCount;
			byte[] nameBytes = name.getBytes();
			
			
			if(nameBytes.length > 18) {
				
				System.arraycopy(bytes, 7, nameBytes, 0, 18);
			}
			else {
				System.arraycopy(bytes, 7, nameBytes, 0, nameBytes.length);
			}
			
			
			ByteUtil.putFloat(bytes, L, 25);
			ByteUtil.putFloat(bytes, a, 29);
			ByteUtil.putFloat(bytes, b, 33);
			for(int i = 0; i < dataSCI.size(); i++) {
				ByteUtil.putShort(bytes, (short) (dataSCI.get(i) * 100), 37 + i * 2, ByteUtil.TYPE_LOW_FIRST);
			}
			for(int i = 0; i < dataSCE.size(); i++) {
				ByteUtil.putShort(bytes, (short) (dataSCE.get(i) * 100), 123 + i * 2, ByteUtil.TYPE_LOW_FIRST);
			}
			ByteUtil.putInt(bytes, recordCount, 209, ByteUtil.TYPE_LOW_FIRST);
			ByteUtil.putInt(bytes, time, 213, ByteUtil.TYPE_LOW_FIRST);
			return bytes;
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("序号：")
					.append(standardNum)
					.append("\n")
					.append("data：")
					.append(judgeDataUseful(state))
					.append("\n")
					.append("角度：")//Angle
					.append(judgeAngle(state))
					.append("\n")
					.append("光源：")//LightSource
					.append(judgeLightSource(lightSource))
					.append("\n")
					.append("数据模式：")//DataMode
					.append(judgeDataMode(dataMode))
					.append("\n")
					.append("测量模式：")//MeasureMode
					.append(judgeMeasureMode(measureMode))
					.append("\n")
					.append("开始波长：")//Starting wavelength
					.append(startWave)
					.append("\n")
					.append("波长间隔：")
					.append(waveLength)//Wavelength interval
					.append("\n")
					.append("波长个数：")//Number of wavelengths
					.append(waveCount)
					.append("\n")
					.append("名称：")
					.append(name)
					.append("\n")
					.append("L：")
					.append(L)
					.append("\n")
					.append("a：")
					.append(a)
					.append("\n")
					.append("b：")
					.append(b)
					.append("\n")
					.append("SCI：")
					.append(ByteUtil.printFloatArrays(dataSCI))
					.append("\n")
					.append("SCE：")
					.append(ByteUtil.printFloatArrays(dataSCE))
					.append("\n")
					.append("该标样下的记录条数：")// The number of records under the standard sample
					.append(recordCount)
					.append("\n")
					.append("时间：")
					.append(TimeUtil.unixTimestamp2Date(time));
			return sb.toString();
			
			
		}
		
		/**
		 * 判断数据模式 judgeDataMode
		 *
		 * @param b
		 * @return
		 */
		public String judgeDataMode(byte b) {
			String str;
			if(b == 0x00) {
				str = "反射率";
			}
			else if(b == 0x01) {
				str = "lab";
			}
			else {
				str = "解析错误";// Parsing error
			}
			return str;
		}
		
		/**
		 * 判断测量模式 judgeMeasureMode
		 *
		 * @param b
		 * @return
		 */
		public String judgeMeasureMode(byte b) {
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
				str = "解析错误"; // Parsing error
			}
			return str;
		}
		
		
		/**
		 * 判断数据有效性 judgeDataUseful
		 *
		 * @param state
		 * @return
		 */
		public String judgeDataUseful(byte state) {
			String str;
			byte b = ByteUtil.getLow4Bit(state);
			if(b == 0x00) {
				str = "null";
			}
			else if(b == 0x01) {
				str = "used";
			}
			else if(b == 0x02) {
				str = "deleted";
			}
			else if(b == 0x03) {
				str = "protect";
			}
			else {
				str = "解析错误";// Parsing error
			}
			return str;
		}
		
		/**
		 * 判断角度 judgeAngle
		 *
		 * @param state
		 * @return
		 */
		public String judgeAngle(byte state) {
			String str;
			byte b = ByteUtil.get8Bit(state);
			if(b == 0x00) {
				str = "2°";
			}
			else if(b == 0x01) {
				str = "10°";
			}
			else {
				str = "解析错误";// Parsing error
			}
			return str;
		}
		
		/**
		 * 判断光源 judgeLightSource
		 *
		 * @param bits
		 * @return
		 */
		public String judgeLightSource(byte bits) {
			String str;
			byte b = ByteUtil.getBit(bits, 7);
			if(b == 0x00) {
				str = "A";
			}
			else if(b == 0x01) {
				str = "C";
			}
			else if(b == 0x02) {
				str = "D50";
			}
			else if(b == 0x03) {
				str = "D55";
			}
			else if(b == 0x04) {
				str = "D65";
			}
			else if(b == 0x05) {
				str = "D75";
			}
			else if(b == 0x06) {
				str = "F1";
			}
			else if(b == 0x07) {
				str = "F2";
			}
			else if(b == 0x08) {
				str = "F3";
			}
			else if(b == 0x09) {
				str = "F4";
			}
			else if(b == 0x0a) {
				str = "F5";
			}
			else if(b == 0x0b) {
				str = "F6";
			}
			else if(b == 0x0c) {
				str = "F7";
			}
			else if(b == 0x0d) {
				str = "F8";
			}
			else if(b == 0x0e) {
				str = "F9";
			}
			else if(b == 0x0f) {
				str = "F10";
			}
			else if(b == 0x10) {
				str = "F11";
			}
			else if(b == 0x11) {
				str = "F12";
			}
			else if(b == 0x12) {
				str = "CWF";
			}
			else if(b == 0x13) {
				str = "U30";
			}
			else if(b == 0x14) {
				str = "DLF";
			}
			else if(b == 0x15) {
				str = "NBF";
			}
			else if(b == 0x16) {
				str = "TL83";
			}
			else if(b == 0x17) {
				str = "TL84";
			}
			else if(b == 0x18) {
				str = "U35";
			}
			else if(b == 0x19) {
				str = "B";
			}
			else {
				str = "解析错误";// Parsing error
			}
			return str;
		}
	}
	
}
