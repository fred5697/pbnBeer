package com.pbn.beers.bean;

import androidx.annotation.NonNull;

import com.pbn.beers.utils.TimeUtil;

import java.io.Serializable;

/**
 * 校準光譜儀
 */
public class AdjustBean implements Serializable
{
	/**
	 * 校准状态： 0-成功 1-失败 2-硬件错误 Calibration status: 0 - success 1 - failure 2 - hardware error
	 */
	private byte adjustState;
	
	/**
	 * Unix时间戳 单位：秒 UNIX timestamp unit: seconds
	 */
	private int time;
	
	/**
	 * 校準前狀態
	 */
	private byte adjustBeforeState;
	
	/**
	 *  设置校准前状态 Set state before calibration
	 * @param adjustBeforeState 0-成功 1-失败 2-硬件错误 0 - success 1 - failure 2 - hardware error
	 */
	public void setAdjustBeforeState(byte adjustBeforeState) {
		this.adjustBeforeState = adjustBeforeState;
	}
	
	
	/**
	 * 设置校准状态 Set calibration status
	 *
	 * @param adjustState 校准状态 0-成功 1-失败 2-硬件错误 Calibration status 0 - success 1 - failure 2 - hardware error
	 */
	public void setAdjustState(byte adjustState) {
		this.adjustState = adjustState;
	}
	
	/**
	 * 返回Unix时间戳 单位：秒 Returns the UNIX timestamp in seconds
	 *
	 * @return 返回Unix时间戳 单位：秒 Returns the UNIX timestamp in seconds
	 */
	public int getTime() {
		return time;
	}
	
	/**
	 * 设置Unix时间戳 Set UNIX timestamp
	 *
	 * @param time Unix时间戳 UNIX timestamp
	 */
	public void setTime(int time) {
		this.time = time;
	}
	
	/**
	 * 返回校准状态 Return to calibration status
	 *
	 * @return 返回校准状态 Return to calibration status
	 */
	public byte getAdjustState() {
		return adjustState;
	}
	
	
	@NonNull
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("\n")
				.append("校準狀態：") // Calibration status
				.append(judgeState(adjustState))
				.append("\n")
				.append("時間：")
				.append(TimeUtil.unixTimestamp2Date(time));
		
		return builder.toString();
	}
	
	private String judgeState(byte b) {
		String str;
		if(b == 0x00) {
			str = "成功"; // success
		}
		else if(b == 0x01) {
			str = "失敗"; // fail
		}
		else if(b == 0x02) {
			str = "硬體錯誤"; // Hardware error
		}
		else {
			str = "解析失敗"; // Parsing error
		}
		return str;
	}
}