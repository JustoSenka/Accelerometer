package com.JustInGApps.accelerometer.main;

import com.JustInGApps.accelerometer.R;
import com.JustInGApps.accelerometer.connection.Connection;
import com.JustInGApps.accelerometer.controller.ControllerActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity{

	private Connection conn = DependencyInjector.getConnection();
	private EditText et1, et2;
	private Button connect, disconnect;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initViews();
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	private void initViews() {
		et1 = (EditText)findViewById(R.id.ip);
		et2 = (EditText)findViewById(R.id.port);
		connect = (Button)findViewById(R.id.connect);
		disconnect = (Button)findViewById(R.id.disconnect);
		
		setConnectButtonClickListener();
		setDisconnectButtonClickListener();
	}
	
	
	private void setDisconnectButtonClickListener() {
		disconnect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				disconnectFromServer();
			}
		});
	}
	private void setConnectButtonClickListener() {
		connect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				connectToServer(et1.getText().toString(), et2.getText().toString());
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		disconnectFromServer();
	}

	private void connectToServer(final String ip, final String port) {
		if (isNetworkAvailable()){
			
			connect.setEnabled(false);
			disconnect.setEnabled(true);
			
			int temp = 6066;
			try {
				temp = Integer.parseInt(port);
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
			}
			
			connectToServerAsync(ip, temp);
		}
		else {
			Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();  
		}
	}
	
	private void connectToServerAsync(final String ip, final int port) {
		
		new AsyncTask<Void, Void, Boolean>(){

			@Override
			protected Boolean doInBackground(Void... params) {
				
				conn.connect(ip, port);
				return conn.isConnected();
			}

			@Override
			protected void onPostExecute(Boolean isConnected) {
				if (isConnected) startActivity(new Intent(MainActivity.this, ControllerActivity.class));
				else {
					Toast.makeText(MainActivity.this, "Did not managed to connect to server", Toast.LENGTH_LONG).show();
					disconnectFromServer();
				}
			}
		}.execute();
	}
	
	private void disconnectFromServer() {
		conn.disconect();
		connect.setEnabled(true);
		disconnect.setEnabled(false);
	}

	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
}
