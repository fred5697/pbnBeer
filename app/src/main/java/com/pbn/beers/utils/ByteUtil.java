package com.pbn.beers.utils;

import android.util.Log;

import java.util.List;

public class ByteUtil
{
	/**
	 * 低位在前(Low first)
	 */
	public static final int TYPE_LOW_FIRST = 0x01;
	/**
	 * 高位在前(Height first)
	 */
	public static final int TYPE_HEIGHT_FIRST = 0x02;
	
	/**
	 * 转换short为byte(Short to byte)
	 *
	 * @param b
	 * @param s
	 * @param index
	 */
	public static void putShort(byte b[], short s, int index) {
		b[ index + 1 ] = (byte) (s >> 8);
		b[ index + 0 ] = (byte) (s >> 0);
	}
	
	public static void putShort(byte b[], short s, int index, int type) {
		if(type == TYPE_LOW_FIRST) {
			b[ index + 1 ] = (byte) (s >> 8);
			b[ index + 0 ] = (byte) (s >> 0);
		}
		else {
			b[ index + 0 ] = (byte) (s >> 8);
			b[ index + 1 ] = (byte) (s >> 0);
		}
		
	}
	
	/**
	 * 通过byte数组取到short(Byte to short)
	 *
	 * @param b
	 * @param index
	 * @return
	 */
	public static short getShort(byte[] b, int index) {
		
		return (short) (((b[ index + 1 ] << 8) | b[ index + 0 ] & 0xff));
	}
	
	public static short getShort(byte[] b, int index, int type) {
		if(type == TYPE_HEIGHT_FIRST) {
			return (short) (((b[ index + 0 ] << 8) | b[ index + 1 ] & 0xff));
		}
		else if(type == TYPE_LOW_FIRST) {
			return (short) (((b[ index + 1 ] << 8) | b[ index + 0 ] & 0xff));
		}
		else {
			return (short) (((b[ index + 1 ] << 8) | b[ index + 0 ] & 0xff));
		}
	}
	
	public static void putInt(byte[] bb, int x, int index, int type) {
		if(type == TYPE_LOW_FIRST) {
			bb[ index + 3 ] = (byte) (x >> 24);
			bb[ index + 2 ] = (byte) (x >> 16);
			bb[ index + 1 ] = (byte) (x >> 8);
			bb[ index + 0 ] = (byte) (x >> 0);
		}
		else {
			bb[ index + 0 ] = (byte) (x >> 24);
			bb[ index + 1 ] = (byte) (x >> 16);
			bb[ index + 2 ] = (byte) (x >> 8);
			bb[ index + 3 ] = (byte) (x >> 0);
		}
		
	}
	
	/**
	 * 通过byte数组取到int(Byte to int)
	 *
	 * @param bb
	 * @param index
	 * @return
	 */
	public static int getInt(byte[] bb, int index) {
		return (int) ((((bb[ index + 3 ] & 0xff) << 24)
				               | ((bb[ index + 2 ] & 0xff) << 16)
				               | ((bb[ index + 1 ] & 0xff) << 8) | ((bb[ index ] & 0xff) << 0)));
	}
	
	public static int getInt(byte[] bb, int index, int type) {
		if(type == TYPE_LOW_FIRST) {
			return (int) ((((bb[ index + 3 ] & 0xff) << 24)
					               | ((bb[ index + 2 ] & 0xff) << 16)
					               | ((bb[ index + 1 ] & 0xff) << 8) | ((bb[ index ] & 0xff) << 0)));
		}
		else {
			return (int) ((((bb[ index + 1 ] & 0xff) << 24)
					               | ((bb[ index + 2 ] & 0xff) << 16)
					               | ((bb[ index + 3 ] & 0xff) << 8) | ((bb[ index ] & 0xff) << 0)));
		}
		
	}
	
	/**
	 * 通过byte数组取到long(Byte to long)
	 *
	 * @param bb
	 * @param index
	 * @return
	 */
	public static long getLong(byte[] bb, int index) {
		return ((((long) bb[ index + 7 ] & 0xff) << 56)
				        | (((long) bb[ index + 6 ] & 0xff) << 48)
				        | (((long) bb[ index + 5 ] & 0xff) << 40)
				        | (((long) bb[ index + 4 ] & 0xff) << 32)
				        | (((long) bb[ index + 3 ] & 0xff) << 24)
				        | (((long) bb[ index + 2 ] & 0xff) << 16)
				        | (((long) bb[ index + 1 ] & 0xff) << 8) | (((long) bb[ index + 0 ] & 0xff) << 0));
	}
	
	/**
	 * 字符到字节转换(Char to byte)
	 *
	 * @param ch
	 * @return
	 */
	public static void putChar(byte[] bb, char ch, int index) {
		int temp = (int) ch;
		// byte[] b = new byte[2];
		for(int i = 0; i < 2; i++) {
			bb[ index + i ] = Integer.valueOf(temp & 0xff).byteValue(); // 将最高位保存在最低位
			temp = temp >> 8; // 向右移8位
		}
	}
	
	/**
	 * 字节到字符转换(Byte to char)
	 *
	 * @param b
	 * @return
	 */
	public static char getChar(byte[] b, int index) {
		int s = 0;
		if(b[ index + 1 ] > 0)
			s += b[ index + 1 ];
		else
			s += 256 + b[ index + 0 ];
		s *= 256;
		if(b[ index + 0 ] > 0)
			s += b[ index + 1 ];
		else
			s += 256 + b[ index + 0 ];
		char ch = (char) s;
		return ch;
	}
	
	/**
	 * float转换byte(Float to byte)
	 *
	 * @param bb
	 * @param x
	 * @param index
	 */
	public static void putFloat(byte[] bb, float x, int index) {
		// byte[] b = new byte[4];
		int l = Float.floatToIntBits(x);
		for(int i = 0; i < 4; i++) {
			bb[ index + i ] = new Integer(l).byteValue();
			l = l >> 8;
		}
	}
	
	/**
	 * 通过byte数组取得float(ByteArray to float)
	 *
	 * @param
	 * @param index
	 * @return
	 */
	public static float getFloat(byte[] b, int index) {
		int l;
		l = b[ index + 0 ];
		l &= 0xff;
		l |= ((long) b[ index + 1 ] << 8);
		l &= 0xffff;
		l |= ((long) b[ index + 2 ] << 16);
		l &= 0xffffff;
		l |= ((long) b[ index + 3 ] << 24);
		return Float.intBitsToFloat(l);
	}
	
	public static float getFloat(byte[] b, int index, int type) {
		if(type == TYPE_LOW_FIRST) {
			int l;
			l = b[ index + 0 ];
			l &= 0xff;
			l |= ((long) b[ index + 1 ] << 8);
			l &= 0xffff;
			l |= ((long) b[ index + 2 ] << 16);
			l &= 0xffffff;
			l |= ((long) b[ index + 3 ] << 24);
			return Float.intBitsToFloat(l);
		}
		else {
			
			
			return (0xff & b[ 3 + index ]) | (0xff00 & (b[ 2 + index ] << 8)) | (0xff0000 & (b[ 1 + index ] << 16)) | (0xff000000 & (b[ 0 + index ] << 24));
		}
		
	}
	
	public static byte[] getHandleByte(byte[] b) {
		
		for(int i = 0; i < b.length; i++) {
			if(b[ i ] == 0) {
				byte[] c = new byte[ i ];
				System.arraycopy(b, 0, c, 0, i);
				return c;
				
			}
		}
		
		return b;
	}
	
	public static byte get8Bit(byte b) {
		b = (byte) ((b & 0b10000000) >>> 7);
		return b;
	}
	
	public static byte getLow4Bit(byte b) {
		b = (byte) (b & (0b00001111));
		return b;
	}
	
	public static byte getHeight4Bit(byte b) {
		b = (byte) (b & (0b11110000));
		return b;
	}
	
	public static byte getBit(byte b, int length) {
		length = 8 - length;
		b = (byte) (b & (0b1111111 >>> length));
		return b;
	}
	
	public static char[] getChars(byte[] bytes, int start, int length) {
		char[] chars = new char[ length ];
		for(int i = 0; i < length; i++) {
			chars[ i ] = (char) bytes[ start + i ];
		}
		return chars;
	}
	
	public static String printArrays(byte[] bytes) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		for(int i = 0; i < bytes.length; i++) {
			builder.append("buf")
					.append("[")
					.append(i)
					.append("]")
					.append(getByteHexString(Integer.toHexString(bytes[ i ])));
			
			if(i != bytes.length - 1) {
				builder.append(", ");
			}
			if((i + 1) % 10 == 0)
				builder.append("\n");
		}
		builder.append("]");
		return builder.toString();
	}
	
	public static String printArrays(float[] bytes) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		for(int i = 0; i < bytes.length; i++) {
			builder.append("buf")
					.append("[")
					.append(i)
					.append("]")
					.append((bytes[ i ]));
			
			if(i != bytes.length - 1) {
				builder.append(", ");
			}
			if((i + 1) % 10 == 0)
				builder.append("\n");
		}
		builder.append("]");
		return builder.toString();
	}
	
	public static String printArrays(List<Short> bytes) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		for(int i = 0; i < bytes.size(); i++) {
			builder.append("buf")
					.append("[")
					.append(i)
					.append("]")
					.append(bytes.get(i));
			if(i != bytes.size() - 1) {
				builder.append(", ");
			}
			if((i + 1) % 10 == 0)
				builder.append("\n");
		}
		builder.append("]");
		return builder.toString();
	}
	
	public static String printFloatArrays(List<Float> bytes) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		for(int i = 0; i < bytes.size(); i++) {
			builder.append("buf")
					.append("[")
					.append(i)
					.append("]")
					.append(bytes.get(i));
			if(i != bytes.size() - 1) {
				builder.append(", ");
			}
			if((i + 1) % 10 == 0)
				builder.append("\n");
		}
		builder.append("]");
		return builder.toString();
	}
	
	public static String printArrays(short[] bytes) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		for(int i = 0; i < bytes.length; i++) {
			builder.append("buf")
					.append("[")
					.append(i)
					.append("]")
					.append((bytes[ i ]));
			if(i != bytes.length - 1) {
				builder.append(", ");
			}
		}
		builder.append("]");
		return builder.toString();
	}
	
	public static boolean judgeSumCheck(byte[] bytes, int checkIndex, byte checkValue) {
		byte value = 0;
		for(int i = 0; i < checkIndex; i++) {
			value = (byte) (value + bytes[ i ]);
		}
		if(value == checkValue) {
			Log.d("deep", "judgeSumCheck===>T");
		}
		else {
			Log.d("deep", "judgeSumCheck===>F");
			Log.d("deep", "judgeSumCheck===>value===>" + Integer.toHexString(value));
			Log.d("deep", "judgeSumCheck===>checkValue===>" + Integer.toHexString(checkValue));
		}
		return value == checkValue;
	}
	
	private static String getByteHexString(String str) {
		String num = "0";
		char[] sourceChars = str.toCharArray();
		char[] chars = new char[ 2 ];
		if(sourceChars.length < 2) {
			chars[ 0 ] = num.charAt(0);
			System.arraycopy(sourceChars, 0, chars, 1, 1);
		}
		else if(sourceChars.length == 2) {
			return str;
		}
		else {
			System.arraycopy(sourceChars, sourceChars.length - 2, chars, 0, 2);
		}
		return new String(chars);
	}
	
}
