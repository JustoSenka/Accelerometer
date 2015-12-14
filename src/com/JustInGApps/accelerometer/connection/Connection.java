package com.JustInGApps.accelerometer.connection;

import com.JustInGApps.accelerometer.main.Point3D;

public interface Connection {

	public void connect(String ip, int port);
	public void disconect();
	
	public <T> void send(T t);
	public Point3D get();
	
	public boolean isBusy();
	public boolean isConnected();
}
