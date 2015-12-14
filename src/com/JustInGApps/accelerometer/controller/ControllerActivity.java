package com.JustInGApps.accelerometer.controller;

import com.JustInGApps.accelerometer.R;
import com.JustInGApps.accelerometer.connection.Connection;
import com.JustInGApps.accelerometer.main.DependencyInjector;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class ControllerActivity extends Activity {

	Connection conn = DependencyInjector.getConnection();
	CustomView cv;
	boolean run;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_controller);
		
		initViews();
		
		// Sensor listener
		SensorManager sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(DependencyInjector.getSensorListener(), 
        		sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), 
        		SensorManager.SENSOR_DELAY_NORMAL);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		startWritingThread();
	}

	private void initViews() {
		
		LinearLayout LL = (LinearLayout) findViewById(R.id.LL);
		
		cv = new CustomView(this, null);
		LL.addView(cv);
	}

	@Override
	protected void onPause() {
		super.onPause();
		run = false;
	}
	
	private void startWritingThread() {
		run = true;
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (run){
					if (!conn.isBusy() && conn.isConnected()) {
						conn.send(DependencyInjector.getSensorListener().getScreenOrientation() + cv.getButtonsData());
						tryWait(20);
					}
					tryWait(5);
				}
				ControllerActivity.this.finish();
			}
			
			private void tryWait(int time) {
				try {
					Thread.sleep(time);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

}
