package com.smartonecorner.eFence;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;


public class OpAlertDlg extends AlertDialog.Builder{

		public OpAlertDlg(Context context,String title, String message, AlertButton lb, AlertButton mb, AlertButton rb) {
			super(context);
			this.setTitle(title);
			
			if(message != null){
				this.setMessage(message);
			}
			
			setButton(lb,mb,rb);
		}
		
		public OpAlertDlg(Context context,String title, View view, AlertButton lb, AlertButton mb, AlertButton rb) {
			super(context);
			this.setTitle(title);
			
			if(view != null){
				this.setView(view);
			}
			setButton(lb,mb,rb);
		}
		
		public OpAlertDlg(Context context,String title, AlertButton lb, AlertButton mb, AlertButton rb) {
			super(context);
			this.setTitle(title);
			setButton(lb,mb,rb);
		}
		void setButton(AlertButton lb, AlertButton mb, AlertButton rb){
			if(lb != null){
				this.setNegativeButton(lb.text, lb.listener);
			}
			
			if(mb != null){
				this.setNeutralButton(mb.text, mb.listener);
			}
			
			if(rb != null){
				this.setPositiveButton(rb.text, rb.listener);
			}
		}
	}

class AlertButton{
	CharSequence text;
	android.content.DialogInterface.OnClickListener listener;
}