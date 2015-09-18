package com.smartonecorner.eFence;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);  
        //����һ��Adapter,ʹ���Զ����Adapter  
       setListAdapter(new TextImageAdapter(this)); 

	}
	private class TextImageAdapter extends BaseAdapter{                                   
	       private Context mContext;                                                            
	       public TextImageAdapter(Context context) {                                           
	           this.mContext=context;                                                           
	       }                                                                                    
	       /**                                                                                  
	        * Ԫ�صĸ���                                                                             
	        */                                                                                  
	       public int getCount() {                                                              
	           return texts.length;                                                             
	       }                                                                                    
	                                                                                            
	       public Object getItem(int position) {                                                
	           return null;                                                                     
	       }                                                                                    
	                                                                                            
	       public long getItemId(int position) {                                                
	           return 0;                                                                        
	       }                                                                                    
	       //����������ListView��չʾ��һ����Ԫ��View                                                         
	       public View getView(int position, View convertView, ViewGroup parent) {              
	           //�Ż�ListView                                                                     
	           if(convertView==null){                                                           
	               convertView=LayoutInflater.from(mContext).inflate(R.layout.settingitem, null);      
	               ItemViewCache viewCache=new ItemViewCache();                                 
	               viewCache.mTextView=(TextView)convertView.findViewById(R.id.text);           
	               viewCache.mImageView=(ImageView)convertView.findViewById(R.id.image);        
	               convertView.setTag(viewCache);                                               
	           }                                                                                
	           ItemViewCache cache=(ItemViewCache)convertView.getTag();                         
	           //�����ı���ͼƬ��Ȼ�󷵻����View������ListView��Item��չʾ                                          
	           cache.mTextView.setText(texts[position]);                                        
	           cache.mImageView.setImageResource(images[position]);                             
	           return convertView;                                                              
	       }                                                                                    
	   }  

    @Override  
	protected void onListItemClick(ListView l, View v, int position, long id) {  
        Toast.makeText(this, "�㵥����"+texts[position], Toast.LENGTH_SHORT).show();
        if(position==0)
        {
        	Intent intent = new Intent(SettingActivity.this,PhoneAndSMSSetActivity.class);  
        	SettingActivity.this.startActivity(intent);
        }
        if(position==1)
        {
        	Intent intent = new Intent(SettingActivity.this,ModificationPassword.class);  
        	SettingActivity.this.startActivity(intent);
        }
    }
	   //Ԫ�صĻ�����,�����Ż�ListView                                                                    
	   private static class ItemViewCache{                                                      
	       public TextView mTextView;                                                           
	       public ImageView mImageView;                                                         
	   } 
	   private  String[] texts=new String[]{"Contact Setting","Change Password"};
	   private int[] images=new int[]{R.drawable.ic_sms,R.drawable.ic_set};

}
