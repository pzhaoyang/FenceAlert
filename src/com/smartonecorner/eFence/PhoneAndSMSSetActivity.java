package com.smartonecorner.eFence;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PhoneAndSMSSetActivity extends Activity {  
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phoneandsmsset);
        service = PhoneAndSMSSetActivity.this.getSharedPreferences("user_info",0);
        phone_number =service.getString("PHONE_NUMBER", ""); 
        sms_content =service.getString("SMS_CONTEN", "使用者离开监控区域");
        phone_number_editText = (EditText) findViewById(R.id.phone_number_editText);
        phone_number_editText.setText(phone_number);
        sms_content_editText = (EditText) findViewById(R.id.sms_content_editText);
        sms_content_editText.setText(sms_content);
        save_sms_button = (Button) findViewById(R.id.save_save_button);
        save_sms_button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
	            phone_number = phone_number_editText.getText().toString().trim();
	            sms_content = sms_content_editText.getText().toString().trim();
	            if(phone_number.equals("")) {
	                Toast.makeText(PhoneAndSMSSetActivity.this, "手机号码不能为空", Toast.LENGTH_LONG).show();
	            }
	            else if(sms_content.equals("")){
	                Toast.makeText(PhoneAndSMSSetActivity.this, "短信内容不能为空", Toast.LENGTH_LONG).show();
	            }
	            else {
	               /* SmsManager smsManager = SmsManager.getDefault();
	                if(sms_content.length() > 70) {
	                    List<String> contents = smsManager.divideMessage(sms_content);
	                    for(String sms : contents) {
	                        smsManager.sendTextMessage(phone_number, null, sms, null, null);
	                    }
	                } else {
	                 smsManager.sendTextMessage(phone_number, null, sms_content, null, null);
	                }*/
	            	
	                Editor editor = service.edit();
	                editor.putString("PHONE_NUMBER", phone_number);
	                editor.putString("SMS_CONTEN", sms_content);
	                editor.commit();
	                Toast.makeText(PhoneAndSMSSetActivity.this,"保存成功", Toast.LENGTH_SHORT).show();
	               // startActivity(new Intent(PhoneAndSMSSetActivity.this, eFenceActivity.class));
//	                setFirstStartupStatus("0");
	                finish();
	            }
	        

			}});

    }
    
    private EditText phone_number_editText;
    private EditText sms_content_editText;
    private Button save_sms_button;
    private SharedPreferences   service;
    private String phone_number;
    private String sms_content ;
    }
