package com.JustInGApps.accelerometer.main;

import com.JustInGApps.accelerometer.connection.*;
import com.JustInGApps.accelerometer.controller.SensorListener;

public class DependencyInjector {

	public static Connection getConnection(){
		
		if (true){
			return SocketConnection.getInstance();
		} else {
			return new HttpConnectionImpl();
		}
	}
	
	public static SensorListener getSensorListener(){
		
		return SensorListener.getInstance();
	}
	
}
