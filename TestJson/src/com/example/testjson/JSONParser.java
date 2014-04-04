package com.example.testjson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONParser 
{
	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";

	// constructor
	public JSONParser() 
	{

	}

	public JSONObject getJSONFromUrl(String url) 
	 {
	 
	     // Making HTTP request
	        try {
	            // defaultHttpClient
	            DefaultHttpClient httpClient = new DefaultHttpClient();
	            
	            HttpPost httpPost = new HttpPost(url);
	            
	            
	            //Add stuff to url request
	           /* 
	            List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
	            pairs.add(new BasicNameValuePair("username", "admin"));
	            pairs.add(new BasicNameValuePair("password", "ehrscape123"));
	            httpPost.setEntity(new UrlEncodedFormEntity(pairs));
	            */
	            HttpResponse httpResponse = httpClient.execute(httpPost);
	            
	            //Log.d("URL dodatek", );
	            
	            HttpEntity httpEntity = httpResponse.getEntity();
	           // Log.d("is:", httpEntity.getContentLength()+"");
	           
	            is = httpEntity.getContent();           
	 
	               
 
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }        
 
		try 
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) 
			{
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
		} 
		catch (Exception e) 
		{
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		try 
		{
			jObj = new JSONObject(json);
		} 
		catch (JSONException e) 
		{
			Log.e("JSON Parser", "Error parsing data " + e.toString());
			jObj = null;
		}
		// return JSON String
		return jObj;
	}
	
	//-----------------------------------------------------------------------------------
	public JSONObject getJSONFromUrl(String url, String sessin) 
	 {
	 
	     // Making HTTP request
	        try {
	            // defaultHttpClient
	            DefaultHttpClient httpClient = new DefaultHttpClient();
	            
	            //HttpGet httpPost = new HttpGet(url);
	            HttpPost httpPost = new HttpPost(url);
	            
	          //Add stuff to url request
	            //List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
	            //pairs.add(new BasicNameValuePair("sessionId", sessin));
	            
	            httpPost.addHeader("sessionId", sessin);
	            Log.d("sessionid", sessin);
	            Log.d("url", url);
	            
	            //pairs.add(new BasicNameValuePair("password", "ehrscape123"));
	            
	            //httpPost.setEntity(new UrlEncodedFormEntity(pairs));
	            
	            
	            HttpResponse httpResponse = httpClient.execute(httpPost);
	            
	            //Log.d("URL dodatek", );
	            
	            HttpEntity httpEntity = httpResponse.getEntity();

	           
	            is = httpEntity.getContent();           

	               

       } catch (UnsupportedEncodingException e) {
           e.printStackTrace();
       } catch (ClientProtocolException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }        

		try 
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			
			while ((line = reader.readLine()) != null) 
			{	
				Log.d("zanka", line);
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
		} 
		catch (Exception e) 
		{
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		try 
		{
			jObj = new JSONObject(json);
		} 
		catch (JSONException e) 
		{
			Log.e("JSON Parser", "Error parsing data " + e.toString());
			jObj = null;
		}
		// return JSON String
		return jObj;
	}
	
	//---------------
	public JSONObject getJSONFromUrl(String url, List<BasicNameValuePair> pairs) {
	 
	     // Making HTTP request
	        try {
	            // defaultHttpClient
	            DefaultHttpClient httpClient = new DefaultHttpClient();
	            HttpGet httpGet = new HttpGet(url);
	            httpGet.addHeader(BasicScheme.authenticate(
	            		 new UsernamePasswordCredentials("admin", "ehrscape123"),
	            		 "UTF-8", false));
	          //Add stuff to url request
	            //List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
	            //pairs.add(new BasicNameValuePair("sessionId", sessin));
	            
	            //pairs.add(new BasicNameValuePair("password", "ehrscape123"));
	            for (BasicNameValuePair i:pairs){
	            	httpGet.addHeader(i.getName(), i.getValue());
	            }
	            /*
	            httpGet.setEntity(new UrlEncodedFormEntity(pairs));
	            */
	            
	            HttpResponse httpResponse = httpClient.execute(httpGet);
	            
	            //Log.d("URL dodatek", );
	            
	            HttpEntity httpEntity = httpResponse.getEntity();

	           
	            is = httpEntity.getContent();           
	 
	               

      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      } catch (ClientProtocolException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      }        

		try 
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) 
			{
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
		} 
		catch (Exception e) 
		{
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		try 
		{
			jObj = new JSONObject(json);
		} 
		catch (JSONException e) 
		{
			Log.e("JSON Parser", "Error parsing data " + e.toString());
			jObj = null;
		}
		// return JSON String
		return jObj;
	}
	
}
