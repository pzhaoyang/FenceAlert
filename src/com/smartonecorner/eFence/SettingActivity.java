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
        //设置一个Adapter,使用自定义的Adapter  
       setListAdapter(new TextImageAdapter(this)); 

	}
	private class TextImageAdapter extends BaseAdapter{                                   
	       private Context mContext;                                                            
	       public TextImageAdapter(Context context) {                                           
	           this.mContext=context;                                                           
	       }                                                                                    
	       /**                                                                                  
	        * 元素的个数                                                                             
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
	       //用以生成在ListView中展示的一个个元素View                                                         
	       public View getView(int position, View convertView, ViewGroup parent) {              
	           //优化ListView                                                                     
	           if(convertView==null){                                                           
	               convertView=LayoutInflater.from(mContext).inflate(R.layout.settingitem, null);      
	               ItemViewCache viewCache=new ItemViewCache();                                 
	               viewCache.mTextView=(TextView)convertView.findViewById(R.id.text);           
	               viewCache.mImageView=(ImageView)convertView.findViewById(R.id.image);        
	               convertView.setTag(viewCache);                                               
	           }                                                                                
	           ItemViewCache cache=(ItemViewCache)convertView.getTag();                         
	           //设置文本和图片，然后返回这个View，用于ListView的Item的展示                                          
	           cache.mTextView.setText(texts[position]);                                        
	           cache.mImageView.setImageResource(images[position]);                             
	           return convertView;                                                              
	       }                                                                                    
	   }  

    @Override  
	protected void onListItemClick(ListView l, View v, int position, long id) {  
        Toast.makeText(this, "你单击了"+texts[position], Toast.LENGTH_SHORT).show();
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
	   //元素的缓冲类,用于优化ListView                                                                    
	   private static class ItemViewCache{                                                      
	       public TextView mTextView;                                                           
	       public ImageView mImageView;                                                         
	   } 
	   private  String[] texts=new String[]{"Contact Setting","Change Password"};
	   private int[] images=new int[]{R.drawable.ic_sms,R.drawable.ic_set};

}
