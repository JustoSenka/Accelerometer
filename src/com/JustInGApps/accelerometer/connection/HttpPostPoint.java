package com.JustInGApps.accelerometer.connection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpProtocolParams;

import android.os.AsyncTask;
import android.util.Log;

public class HttpPostPoint extends AsyncTask<String, Void, Void> {

	public static final void testExecuteNoAsync(String url, String pointJson){
		try {
			HttpProtocolParams.setVersion(new BasicHttpParams(), HttpVersion.HTTP_1_1);
			HttpPost post = new HttpPost(url);

			post.setEntity(new StringEntity(pointJson, "UTF8"));
			post.setHeader("Content-type", "application/json");
			
			HttpEntity entity = new DefaultHttpClient().execute(post).getEntity();

			if (entity != null) {
				Log.i("POST RESPONSE", "POST SUCCESS: " + pointJson);
			} else {
				Log.i("POST RESPONSE", "HttpPostPoint doInBackground entity null");
			}

		} catch (Exception e) {
			Log.i("POST RESPONSE", "HttpPostPoint doInBackground exception catch");
		}
	}
	
	@Override
	protected Void doInBackground(String... args) {
		// TODO Auto-generated method stub
		
		String pointJson = args[1];
		String url = args[0];
		
		try {
			HttpProtocolParams.setVersion(new BasicHttpParams(), HttpVersion.HTTP_1_1);
			HttpPost post = new HttpPost(url);

			post.setEntity(new StringEntity(pointJson, "UTF8"));
			post.setHeader("Content-type", "application/json");
			
			HttpEntity entity = new DefaultHttpClient().execute(post).getEntity();

			if (entity != null) {
				Log.i("POST RESPONSE", "POST SUCCESS: " + pointJson);
			} else {
				Log.i("POST RESPONSE", "HttpPostPoint doInBackground entity null");
			}

		} catch (Exception e) {
			Log.i("POST RESPONSE", "HttpPostPoint doInBackground exception catch");
		}
		return null;
	}
}