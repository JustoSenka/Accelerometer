package com.JustInGApps.accelerometer.controller;

import com.JustInGApps.accelerometer.main.Point3D;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class SensorListener implements SensorEventListener {

	private Point3D screenOrientation = new Point3D();
	
	public Point3D getScreenOrientation(){
		return screenOrientation;
	}
	
	private static final SensorListener Instance = new SensorListener();
	private SensorListener(){}
	public static final SensorListener getInstance(){
		return Instance;
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) { }
	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			int x = (int)(event.values[0] * 10);
			int y = (int)(event.values[1] * 10);
			int z = (int)(event.values[2] * 10);
			
			x = softenData(x);
			y = softenData(y);
			z = softenData(z);
			
			screenOrientation.setCoords(x, y, z);
		}
	}
	
	private int softenData(int i) {
		if (i > 2) i -= 2;
		else if (i < -2) i += 2;
		else i = 0;
		
		return i;
	}
}
