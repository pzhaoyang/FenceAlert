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
import android.widget.ImageView;
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
		password = (EditText) findViewById(R.id.et_input_password);
		password.setFocusable(false);
		ImageView Iv_log=(ImageView)findViewById(R.id.iv_log);
		Iv_log.getBackground().setAlpha(127);
		Button btn_num_1 = (Button) findViewById(R.id.w_num_1);
		Button btn_num_2 = (Button) findViewById(R.id.w_num_2);
		Button btn_num_3 = (Button) findViewById(R.id.w_num_3);
		Button btn_num_4 = (Button) findViewById(R.id.w_num_4);
		Button btn_num_5 = (Button) findViewById(R.id.w_num_5);
		Button btn_num_6 = (Button) findViewById(R.id.w_num_6);
		Button btn_num_7 = (Button) findViewById(R.id.w_num_7);
		Button btn_num_8 = (Button) findViewById(R.id.w_num_8);
		Button btn_num_9 = (Button) findViewById(R.id.w_num_9);
		Button btn_num_0 = (Button) findViewById(R.id.w_num_0);
		Button btn_num_del = (Button) findViewById(R.id.w_num_del);
		sp = this.getSharedPreferences("user_info", MODE_PRIVATE); 
        if(sp.getString("PASSWORD", "1234").equals(""))  
        {  
            Toast.makeText(LoginActivity.this,"Login Success", Toast.LENGTH_SHORT).show();    
            Intent intent = new Intent(LoginActivity.this,eFenceActivity.class);  
            LoginActivity.this.startActivity(intent);  
            finish();  
              
        }
        btn_num_1.setOnClickListener(new OnClickListener() {  
	        public void onClick(View v) {  
	            passwordValue = password.getText().toString();  
	            password.setText(password.getText().toString()+"1");
	            RefreshPassword();
	          //  if(passwordValue.equals(sp.getString("PASSWORD", "123456")))  
	         //   {  
	          //      Toast.makeText(LoginActivity.this,"Login Success", Toast.LENGTH_SHORT).show();    
	           //     Intent intent = new Intent(LoginActivity.this,eFenceActivity.class);  
	            //    LoginActivity.this.startActivity(intent);  
	           //     finish();  
	                  
	         //   }else{                    
	          //      Toast.makeText(LoginActivity.this,"Password Error, Please Retry!", Toast.LENGTH_LONG).show();  
	          //  }  
	              
	        }  
	    }); 
        btn_num_2.setOnClickListener(new OnClickListener() {  
	        public void onClick(View v) {  
	            password.setText(password.getText().toString()+"2");	
	            RefreshPassword();
	        }  
	    }); 
        btn_num_3.setOnClickListener(new OnClickListener() {  
	        public void onClick(View v) {  
	            password.setText(password.getText().toString()+"3");	
	            RefreshPassword();
	        }  
	    }); 
        btn_num_4.setOnClickListener(new OnClickListener() {  
	        public void onClick(View v) {  
	            password.setText(password.getText().toString()+"4");	
	            RefreshPassword();
	        }  
	    }); 
        btn_num_5.setOnClickListener(new OnClickListener() {  
	        public void onClick(View v) {  
	            password.setText(password.getText().toString()+"5");	
	            RefreshPassword();
	        }  
	    }); 
        btn_num_6.setOnClickListener(new OnClickListener() {  
	        public void onClick(View v) {  
	            password.setText(password.getText().toString()+"6");
	            RefreshPassword();
	        }  
	    }); 
        btn_num_7.setOnClickListener(new OnClickListener() {  
	        public void onClick(View v) {  
	            password.setText(password.getText().toString()+"7");
	            RefreshPassword();
	        }  
	    }); 
        btn_num_8.setOnClickListener(new OnClickListener() {  
	        public void onClick(View v) {  
	            password.setText(password.getText().toString()+"8");
	            RefreshPassword();
	        }  
	    }); 
        btn_num_9.setOnClickListener(new OnClickListener() {  
	        public void onClick(View v) {  
	            password.setText(password.getText().toString()+"9");
	            RefreshPassword();
	        }  
	    }); 
        btn_num_0.setOnClickListener(new OnClickListener() {  
	        public void onClick(View v) {  
	            password.setText(password.getText().toString()+"0");	
	            RefreshPassword();
	        }  
	    }); 
        btn_num_del.setOnClickListener(new OnClickListener() {  
	        public void onClick(View v) {  
	        	passwordValue = password.getText().toString();  
	        	if(!passwordValue.isEmpty())
	        	{	
		            password.setText(passwordValue.substring(0,passwordValue.length()-1));	
	        	}
	        }  
	    }); 

	}
	private void RefreshPassword(){
		passwordValue = password.getText().toString();  
         if(passwordValue.equals(sp.getString("PASSWORD", "123456")))  
          {  
              Toast.makeText(LoginActivity.this,"Login Success", Toast.LENGTH_SHORT).show();
              
              //setFirstStartupStatus("");
              if( !CheckFirstLogin() ){
	              Intent intent = new Intent(LoginActivity.this,eFenceActivity.class);  
	              LoginActivity.this.startActivity(intent);
              }
              
              finish();
          }
	};
	
	private boolean CheckFirstLogin(){
		SharedPreferences sp = getSharedPreferences("user_info",0);
        if(!("123456789".equals(sp.getString("PHONE_NUMBER", "123456789")))){
        	return false;
        }
        
        startActivity(new Intent(this, PhoneAndSMSSetActivity.class));
        return true;
	}
	
    private void setFirstStartupStatus(String step){
    	SharedPreferences sp = getSharedPreferences("user_info",0);
    	String status;
    	
    	if(step.equals("")){
    		status = sp.getString("FIRST_STARTUP", "");
    	}else {
    		status = step;
    	}
    	if(status.equals("") || status.equals("0")){
    		startActivity(new Intent(this, PhoneAndSMSSetActivity.class));
    	}else if(status.equals("1")){
    		startActivity(new Intent(this, ModificationPassword.class));
    	}else if(status.equals("2")){
    		startActivity(new Intent(this, eFenceActivity.class));
    	}
    	
    	Editor editor = sp.edit();
        editor.putString("FIRST_STARTUP", status);
        editor.commit();
        
    	finish();
    }
}
