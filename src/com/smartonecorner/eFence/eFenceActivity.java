package com.smartonecorner.eFence;

import java.text.Format.Field;
import java.util.List;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.AMap.OnMapClickListener;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;



public class eFenceActivity extends Activity implements AMapLocationListener,
	OnMapClickListener, LocationSource {

	public static final String EFENCE_BROADCAST_ACTION = "com.smartonecorner.efence.broadcast";
	
	private MapView mMapView;
	private AMap mAMap;
	private OnLocationChangedListener mListener;
	private LocationManagerProxy mLocationManagerProxy;
	private Marker mGPSMarker;
	private Circle mCircle;
	private PendingIntent mPendingIntent;
	

	private float mRadius = 20;
	private long mExpir = 2 * 1000*60;
	
	private Context mContext;
	
	private BroadcastReceiver meFenceReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(EFENCE_BROADCAST_ACTION)) {
				Bundle bundle = intent.getExtras();
				int status = bundle.getInt("status");
				if (status == 0) {
					SendSMS();
					Toast.makeText(getApplicationContext(), "Out Fence",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(), "In Fence",
							Toast.LENGTH_SHORT).show();
				}
				Log.d("AAA","status=" + status);

			}

		}
	};
	protected void SendSMS(){
		SharedPreferences service = eFenceActivity.this.getSharedPreferences("user_info",0);
		String phone_number =service.getString("PHONE_NUMBER", "123456789"); 
		String sms_content =service.getString("SMS_CONTEN", "使用者离开监控区域");
         SmsManager smsManager = SmsManager.getDefault();
        if(sms_content.length() > 70) {
            List<String> contents = smsManager.divideMessage(sms_content);
            for(String sms : contents) {
                smsManager.sendTextMessage(phone_number, null, sms, null, null);
            }
        } else {
         smsManager.sendTextMessage(phone_number, null, sms_content, null, null);
        }	
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_efence);
		mContext = eFenceActivity.this;
		
		forceShowOverflowMenu();
		
		Button setting_button = (Button) findViewById(R.id.setting_button);
		init(savedInstanceState);
		setting_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
	        	Intent intent = new Intent(eFenceActivity.this,SettingActivity.class);  
	        	eFenceActivity.this.startActivity(intent);
			}
		});
	}
	

	private void setUpMap() {
		mAMap.setLocationSource(this);
		mAMap.getUiSettings().setMyLocationButtonEnabled(true);
		mAMap.setMyLocationEnabled(true);
		mAMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
	}
	
	private void setMarker() {
		MarkerOptions markOptions = new MarkerOptions();
		markOptions.icon(
				BitmapDescriptorFactory.fromBitmap(
						BitmapFactory.decodeResource(
								getResources(),R.drawable.location_marker))).anchor(0.5f, 0.5f);
		mGPSMarker = mAMap.addMarker(markOptions);

		
		
	}
	
	private void updateLocation(double latitude, double longtitude) {
		if (mGPSMarker != null) {
			mGPSMarker.setPosition(new LatLng(latitude, longtitude));
		}

	}

	private void RegBC(){
		IntentFilter fliter = new IntentFilter(
				ConnectivityManager.CONNECTIVITY_ACTION);
		fliter.addAction(EFENCE_BROADCAST_ACTION);
		registerReceiver(meFenceReceiver, fliter);
		Intent intent = new Intent(EFENCE_BROADCAST_ACTION);
		mPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0,
				intent, 0);
	}
	
	private void init(Bundle savedInstanceState){
		mMapView = (MapView) findViewById(R.id.map);
		mMapView.onCreate(savedInstanceState);
		mAMap = mMapView.getMap();
		RegBC();

		
		mLocationManagerProxy = LocationManagerProxy.getInstance(this);
		mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork, 2000, 1, this);
		setUpMap();
		//setMarker();
		mAMap.setOnMapClickListener(this);
	}
	@Override
	protected void onPause() {
		/*When background the software is still running*/
//		mLocationManagerProxy.removeGeoFenceAlert(mPendingIntent);
//		mLocationManagerProxy.removeUpdates(this);
//		mLocationManagerProxy.destroy();
		super.onPause();
		mMapView.onPause();
		deactivate();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mMapView.onResume();
	}

	protected void onStop() {
		super.onStop();
	}

	protected void onDestroy() {
		super.onDestroy();

		mMapView.onDestroy();

	}
	
	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}

	private void setFence(LatLng  latLng, float radius, long Expr){
		mLocationManagerProxy.addGeoFenceAlert(
				latLng.latitude,
				latLng.longitude, 
				mRadius,
				mExpir, 
				mPendingIntent);
		CircleOptions circleOptions = new CircleOptions();
		circleOptions.center(latLng).radius((double)mRadius)
				.fillColor(Color.argb(180, 224, 171, 10))
				.strokeColor(Color.RED);
		mCircle = mAMap.addCircle(circleOptions);
		
	}
	
	AlertButton setAlertBt(CharSequence text, OnClickListener l ){
		AlertButton ab = new AlertButton();
		ab.text = text;
		ab.listener = l;
		return ab;
	}
	
	@Override
	public void onMapClick(final LatLng latLng) {

		mLocationManagerProxy.removeGeoFenceAlert(mPendingIntent);
		if (mCircle != null) {
			mCircle.remove();
		}
		LayoutInflater inflater = LayoutInflater.from(this);
		View view = inflater.inflate(R.layout.dialog_radius, null);
		final EditText et3 = (EditText) view.findViewById(R.id.radiusvalue);
		
	    et3.setText("20");
	    et3.requestFocus();
	    et3.selectAll();
		new OpAlertDlg(mContext,"Set Radius",view,
	      setAlertBt(mContext.getString(android.R.string.cancel), null),
	      null,
	      setAlertBt(mContext.getString(android.R.string.ok), new OnClickListener(){
	
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				String valuestr = et3.getText().toString().trim();
				mRadius = Float.parseFloat(valuestr);
				//Toast.makeText(mContext, ""+mRadius, Toast.LENGTH_SHORT).show();
				Log.d("AAA","mRadius = " + mRadius);
				setFence(latLng, mRadius, mExpir);
			}
	      })).create().show();
	
	}

	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		if (mListener != null && amapLocation != null) {
			if (amapLocation != null
					&& amapLocation.getAMapException().getErrorCode() == 0) {
				mListener.onLocationChanged(amapLocation);
			} else {
				Log.e("AAA","Location ERR:" + amapLocation.getAMapException().getErrorCode());
			}
		}

		if (amapLocation.getAMapException().getErrorCode() == 0) {
			updateLocation(amapLocation.getLatitude(), amapLocation.getLongitude());
		}
		
	}


	@Override
	public void activate(OnLocationChangedListener listener) {
		mListener = listener;
		if (mLocationManagerProxy == null) {
			mLocationManagerProxy = LocationManagerProxy.getInstance(this);
			mLocationManagerProxy.requestLocationData(
					LocationProviderProxy.AMapNetwork, 60 * 1000, 10, this);
		}
	}


	@Override
	public void deactivate() {
		mListener = null;
		if (mLocationManagerProxy != null) {
			mLocationManagerProxy.removeUpdates(this);
			mLocationManagerProxy.destroy();
		}
		mLocationManagerProxy = null;
	}

	@Override  
	  public boolean onCreateOptionsMenu(Menu menu) {  
	    MenuInflater inflater = getMenuInflater();  
	    inflater.inflate(R.menu.action_menu, menu);  
	    return true;  
	  }
	
	 public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {
	        case R.id.action_settings:
	        	Intent intent = new Intent(eFenceActivity.this,SettingActivity.class);  
	        	eFenceActivity.this.startActivity(intent);
	            break;
	        }
	        return super.onOptionsItemSelected(item);
	    }
	 
	    private void forceShowOverflowMenu() {  
	        try {  
	            ViewConfiguration config = ViewConfiguration.get(this);  
	            java.lang.reflect.Field menuKeyField = ViewConfiguration.class  
	                    .getDeclaredField("sHasPermanentMenuKey");  
	            if (menuKeyField != null) {  
	                menuKeyField.setAccessible(true);  
	                menuKeyField.setBoolean(config, false);  
	            }  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	    }  
}
