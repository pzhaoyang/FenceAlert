package com.smartonecorner.eFence;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

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

	private MapView mMapView;
	private AMap mAMap;
	private OnLocationChangedListener mListener;
	private LocationManagerProxy mLocationManagerProxy;
	private Marker mGPSMarker;
	private Circle mCircle;
	private PendingIntent mPendingIntent;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_efence);
		Button Setting_button = (Button) findViewById(R.id.setting_button);
		init(savedInstanceState);
		Setting_button.setOnClickListener(new OnClickListener(){

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
	
	private void init(Bundle savedInstanceState){
		mMapView = (MapView) findViewById(R.id.map);
		mMapView.onCreate(savedInstanceState);
		mAMap = mMapView.getMap();
		
		mLocationManagerProxy = LocationManagerProxy.getInstance(this);
		mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork, 2000, 1, this);
		setUpMap();
		//setMarker();
		mAMap.setOnMapClickListener(this);
	}
	@Override
	protected void onPause() {
		mLocationManagerProxy.removeGeoFenceAlert(mPendingIntent);
		mLocationManagerProxy.removeUpdates(this);
		mLocationManagerProxy.destroy();
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

	@Override
	public void onMapClick(LatLng latLng) {

		mLocationManagerProxy.removeGeoFenceAlert(mPendingIntent);
		if (mCircle != null) {
			mCircle.remove();
		}
		mLocationManagerProxy.addGeoFenceAlert(latLng.latitude,
				latLng.longitude, 1000,
				 1000 * 60 * 30
				 , mPendingIntent);
		CircleOptions circleOptions = new CircleOptions();
		circleOptions.center(latLng).radius(1000)
				.fillColor(Color.argb(180, 224, 171, 10))
				.strokeColor(Color.RED);
		mCircle = mAMap.addCircle(circleOptions);

	
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

}
