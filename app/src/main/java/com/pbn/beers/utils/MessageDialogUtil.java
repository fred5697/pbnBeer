package com.pbn.beers.utils;

import androidx.appcompat.app.AppCompatActivity;

import com.pbn.beers.R;
import com.kongzue.dialog.v3.MessageDialog;

public class MessageDialogUtil
{
	public static void okMsgBox(AppCompatActivity context, String title, String msg, OnDialogButtonClickListener listener) {
		MessageDialog.show(context, title, msg, context.getString(R.string.btn_i_know)).
				setOkButton((baseDialog, v) -> {
					if(listener != null)
						listener.onClick();
					return false;
				});
	}
	
	public static void okMsgBox(AppCompatActivity context, String title, String msg) {
		okMsgBox(context, title, msg, null);
	}
	
	public interface OnDialogButtonClickListener {
		void onClick();
	}
}
