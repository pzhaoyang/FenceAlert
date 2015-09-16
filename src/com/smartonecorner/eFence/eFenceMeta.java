package com.smartonecorner.eFence;

import android.app.PendingIntent;

import com.amap.api.maps.model.LatLng;

public class eFenceMeta {
	private String mname;
	private LatLng mpoint;
	private float  mradius;
	private long   mexpir;
	private PendingIntent mpint;
	
	public eFenceMeta(String name, LatLng point, float radius, long expir, PendingIntent pint) {
		this.mname = name;
		this.mpoint = point;
		this.mradius = radius;
		this.mexpir = expir;
		this.mpint = pint;
	}
	
	public String getFenceName(){
		return mname;
	}
	
	public LatLng getFenceCenter(){
		return mpoint;
	}
	
	public float getFenceRadius(){
		return mradius;
	}
	
	public long getFenceExpir(){
		return mexpir;
	}
	
	public PendingIntent getFenceIntent(){
		return mpint;
	}
}
