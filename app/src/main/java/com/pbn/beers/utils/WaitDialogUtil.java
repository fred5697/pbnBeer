package com.pbn.beers.utils;

import androidx.appcompat.app.AppCompatActivity;

import com.kongzue.dialog.v3.TipDialog;
import com.kongzue.dialog.v3.WaitDialog;

public class WaitDialogUtil
{
	/**
	 * 顯示 waitDialog(需自行關閉)
	 * @param context AppCompatActivity
	 * @param msg msg
	 */
	public static void show(AppCompatActivity context, String msg) {
		WaitDialog.show(context, msg);
	}
	
	/**
	 * 關閉顯示中的 waitDialog
	 */
	public static void dismiss() {
		WaitDialog.dismiss();
	}
	
	/**
	 * 指定的時間後，關閉顯示中的 waitDialog
	 *
	 * @param millisecond millisecond
	 */
	public static void dismiss(int millisecond) {
		WaitDialog.dismiss(millisecond);
	}
	
	/**
	 * 指定的時間後，關閉顯示中的 waitDialog。並可設置監聽
	 * @param context AppCompatActivity
	 * @param msg msg
	 * @param dismissMillisecond dismissMillisecond
	 * @param listener listener
	 */
	public static void show(AppCompatActivity context, String msg, int dismissMillisecond, OnDismissListener listener) {
		TipDialog dialog = WaitDialog.show(context, msg);
		WaitDialog.dismiss(dismissMillisecond);
		
		if(listener != null)
			dialog.setOnDismissListener(listener::onDismiss);
	}
	
	public interface OnDismissListener
	{
		void onDismiss();
	}
}
