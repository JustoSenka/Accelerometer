package com.JustInGApps.accelerometer.connection;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.AsyncTask;

import com.JustInGApps.accelerometer.main.Point3D;

public class SocketConnection implements Connection {

	private String ip;
	private int port;
	private volatile boolean busy = false;
	
	private Socket client = null;
	private BufferedWriter out = null;
	
	private static final SocketConnection Instance = new SocketConnection();
	private SocketConnection(){ }
	public static SocketConnection getInstance(){
		return Instance;
	}

	@Override
	public void connect(String ip, int port) {
		this.ip = ip;
		this.port = port;
		
		try {
			client = new Socket(ip, port);
			out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void disconect() {
		if (out != null){
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (client != null) {
			try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public <T> void send(final T t) {
		
		new AsyncTask<Void, Void, Void>(){
			@Override
			protected void onPreExecute() {
				busy = true;
			}
			
			@Override
			protected Void doInBackground(Void... args) {
				
				try {
					out.write(t.toString());
					out.newLine();
					out.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
			
			@Override
			protected void onPostExecute(Void result) {
				busy = false;
			}
		}.execute();
	}

	@Override
	public Point3D get() {

		return null;
	}

	@Override
	public boolean isBusy() {
		return busy;
	}
	
	@Override
	public boolean isConnected() {
		return client != null;
	}
}
