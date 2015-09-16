package com.smartonecorner.eFence;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ModificationPassword extends Activity {
	private EditText  oldpassword,newpassword,newagainword; 
	private Button btn_ok; 
	private SharedPreferences sp; 
	private String oldpasswordValue,newpasswordValue,newagainpasswordValue;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modificationpassword);  
		oldpassword = (EditText) findViewById(R.id.et_oldpassword);
		newpassword = (EditText) findViewById(R.id.et_newpassword);
		newagainword = (EditText) findViewById(R.id.et_newagainpassword);
		btn_ok = (Button) findViewById(R.id.save_password_button);
		sp = ModificationPassword.this.getSharedPreferences("user_info",0);
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				oldpasswordValue = oldpassword.getText().toString();
				newpasswordValue = newpassword.getText().toString();
				newagainpasswordValue = newagainword.getText().toString();
				if(!oldpasswordValue.equals(sp.getString("PASSWORD", "123"))){
					Toast.makeText(ModificationPassword.this,"�������������������", Toast.LENGTH_LONG).show(); 
				}
				else if(!newpasswordValue.equals(newagainpasswordValue)){
					Toast.makeText(ModificationPassword.this,"������������벻һ��������������", Toast.LENGTH_LONG).show();
				}
				else{
                    Editor editor = sp.edit();
                    editor.putString("PASSWORD", newpasswordValue);
                    editor.commit();
                    Toast.makeText(ModificationPassword.this,"�����޸ĳɹ�", Toast.LENGTH_LONG).show();
                    finish();
				}
			}
			
		});
	}

}
