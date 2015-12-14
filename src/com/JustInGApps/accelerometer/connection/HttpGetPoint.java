package com.JustInGApps.accelerometer.connection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

import com.JustInGApps.accelerometer.main.Point3D;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.os.AsyncTask;
import android.util.Log;

public class HttpGetPoint extends AsyncTask<String, Void, Point3D> {

	@Override
	protected Point3D doInBackground(String... params) {
		// TODO Auto-generated method stub
		
		final String url = params[0];
		Point3D p = new Point3D();

		Log.i("GET RESPONSE", url);
		
		try {
			HttpProtocolParams.setVersion(new BasicHttpParams(), HttpVersion.HTTP_1_1);
			final HttpEntity entity = new DefaultHttpClient().execute(new HttpGet(url)).getEntity();
			if (entity != null) {
				
				String str = EntityUtils.toString(entity);
				
				ObjectMapper m = new ObjectMapper();
				p = m.readValue(str, Point3D.class);
	
				Log.i("GET RESPONSE", str);
			} else {
				Log.i("GET RESPONSE", "Point3D doInBackground entity null");
			}
		} catch (Exception e) {
			Log.i("GET RESPONSE", "Point3D doInBackground exception catch");
		}
		
		return p;
	}

}