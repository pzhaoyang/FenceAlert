package com.smartonecorner.eFence;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private EditText  password; 
	private Button btn_login; 
	private SharedPreferences sp; 
	private String passwordValue;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		password = (EditText) findViewById(R.id.et_mima);
		btn_login = (Button) findViewById(R.id.btn_login);
		sp = this.getSharedPreferences("user_info", MODE_PRIVATE); 
        if(sp.getString("PASSWORD", "123456").equals(""))  
        {  
            Toast.makeText(LoginActivity.this,"Login Success", Toast.LENGTH_SHORT).show();    
            Intent intent = new Intent(LoginActivity.this,eFenceActivity.class);  
            LoginActivity.this.startActivity(intent);  
            finish();  
              
        }
        btn_login.setOnClickListener(new OnClickListener() {  
	        public void onClick(View v) {  
	            passwordValue = password.getText().toString();  
	              
	            if(passwordValue.equals(sp.getString("PASSWORD", "123456")))  
	            {  
	                Toast.makeText(LoginActivity.this,"Login Success", Toast.LENGTH_SHORT).show();    
	                Intent intent = new Intent(LoginActivity.this,eFenceActivity.class);  
	                LoginActivity.this.startActivity(intent);  
	                finish();  
	                  
	            }else{                    
	                Toast.makeText(LoginActivity.this,"Password Error, Please Retry!", Toast.LENGTH_LONG).show();  
	            }  
	              
	        }  
	    }); 

	}
}
