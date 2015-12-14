package com.JustInGApps.accelerometer.connection;

import android.util.Log;

import com.JustInGApps.accelerometer.main.Point3D;

public class HttpConnectionImpl implements Connection {

	private String ip;
	private int port;
	
	@Override
	public <T> void send(T t) {
		new HttpPostPoint() {
			@Override
			protected void onPreExecute() {
				//posting = true;
			}

			@Override
			protected void onPostExecute(Void result) {
				//posting = false;
				//if (run) postPointToServer(ip, screenOrientation);
			}
		}.execute("http://" + ip + ":" + port, t.toString());
	}

	@Override
	public Point3D get() {
		new HttpGetPoint() {
			@Override
			protected void onPostExecute(Point3D result) {
				super.onPostExecute(result);

				Log.i("tag", result.toString());
			}
		}.execute("http://" + ip + ":" + port);
		return null;
	}

	
	@Override
	public void connect(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	@Override
	public boolean isBusy() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void disconect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isConnected() {
		// TODO Auto-generated method stub
		return false;
	}

}
